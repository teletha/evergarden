/*
 * Copyright (C) 2025 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package evergarden.javadoc;

import java.util.List;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import kiss.I;
import kiss.XML;

public class Markdown {

    private static final Parser markParser = Parser.builder().extensions(List.of(TablesExtension.create())).build();

    private static final HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();

    public static XML parse(String text) {
        return I.xml(htmlRenderer.render(markParser.parse(text)));
    }
}
