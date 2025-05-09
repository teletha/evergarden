/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden.web;

import java.util.function.Consumer;

import kiss.XML;
import stylist.Style;

/**
 * Domain Specific Language for HTML.
 */
public abstract class HTML extends lycoris.HTML {

    protected final Consumer<XML> svg(String type) {
        return parent -> {
            $("svg", clazz(type), () -> {
                $("use", attr("href", "main.svg#" + type));
            });
        };
    }

    protected final Consumer<XML> togglable(Style style) {
        return attr("onClick", "this.classList.toggle('" + style.className()[0] + "')");
    }
}