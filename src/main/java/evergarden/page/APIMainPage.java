/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden.page;

import java.util.List;

import evergarden.Letter;
import evergarden.VioletEvergarden;
import evergarden.design.EvergardenDSL;
import evergarden.design.Styles;
import evergarden.javadoc.ClassInfo;

public class APIMainPage extends Page<VioletEvergarden> {

    public APIMainPage(String path, Letter letter, VioletEvergarden doll) {
        super(path, letter, doll);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        $("div", Styles.Section, () -> {
            $("section", Styles.JavadocComment, () -> {
                $("h2", text(letter.title() + " API Overview"));
                $("p", text("This page provides a concise overview of the available APIs, including their purpose, key functionalities, and usage patterns. It serves as a starting point for developers to understand how to integrate and interact with the system effectively."));

                writeTable("Interface");
                writeTable("Functional", "Functional Interface");
                writeTable("Class");
                writeTable("Enum");
                writeTable("Annotation");
                writeTable("Record");
            });
        });
    }

    private void writeTable(String type) {
        writeTable(type, type);
    }

    private void writeTable(String type, String title) {
        List<ClassInfo> list = letter.types.stream().filter(c -> c.type.equals(type)).toList();
        if (!list.isEmpty()) {
            $("section", Styles.JavadocComment, () -> {
                $("h4", text("🛠️ " + title));
                $("table", () -> {
                    for (ClassInfo info : list) {
                        $("tr", () -> {
                            $("td", () -> $("code", () -> $("a", href("api/" + info.id() + ".html"), text(info.name))));
                            $("td", text(info.contents() == null ? "no description" : info.contents().children().first().text()));
                        });
                    }
                });
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareSubNavigation() {
    }

    /**
     * Style definition.
     */
    interface css extends EvergardenDSL {
    }
}