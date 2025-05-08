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

import static stylist.value.Numeric.*;

import evergarden.Document;
import evergarden.Letter;
import evergarden.design.EvergardenDSL;
import stylist.Style;

public class DocumentPage<D extends Document> extends AbstractDocumentPage<D> {

    public DocumentPage(String path, Letter letter, D contents) {
        super(path, letter, contents);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        writeDocument(contents);

        $("div", css.navi, () -> {
            contents.prev().to(doc -> {
                $("a", css.box, css.prev, href("doc/" + doc.id() + ".html"), text(doc.title()));
            });
            contents.next().to(doc -> {
                $("a", css.box, css.next, href("doc/" + doc.id() + ".html"), text(doc.title()));
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareAside() {
        writeContribution(contents);

        $("p", CSS.sidebox, text("View in "), () -> {
            $("a", href("doc/one.html#" + contents.children().getFirst().id()), text("onepager"));
        });
    }

    interface css extends EvergardenDSL {

        Style box = () -> {
            border.radius(Theme.radius);
            background.color(Theme.surface);
            padding.vertical(1, rem).horizontal(1.5, rem);
            text.decoration.none().verticalAlign.middle();

            $.hover(() -> {
                background.color(Theme.accent.opacify(-0.7));
            });
        };

        Style prev = () -> {
            $.before(() -> {
                font.family(Theme.icon);
                content.text("\\e5e0");
                padding.right(1, rem);
            });
        };

        Style next = () -> {
            text.align.right();

            $.after(() -> {
                font.family(Theme.icon);
                content.text("\\e5e1");
                padding.left(1, rem);
            });
        };

        Style navi = () -> {
            display.grid().area(prev, next).column(num(1, fr), num(1, fr)).columnGap(2, rem);
        };
    }
}