/*
 * Copyright (C) 2025 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package evergarden.page;

import evergarden.Letter;
import evergarden.design.EvergardenDSL;
import stylist.Style;

public class LandingPage extends Page {

    /**
     * @param path
     * @param letter
     */
    public LandingPage(String path, Letter letter) {
        super(path, letter, new Object());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        letter.authority().to(host -> {
            $("a", css.card, href(host.location()), () -> {
                $("div", () -> {
                    $("img", css.icon, src(host.icon()));
                    $("span", text(host.owner() + "  /  " + host.name()));
                });
                $("div", text(host.description()));
                $("div", () -> {
                    $(svg("star", css.star));
                    $("span", text(host.countStar()));
                    $("span", css.fork, text(host.countFork()));
                    $("span", css.license, text(host.license()));
                });
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareAside() {
    }

    interface css extends EvergardenDSL {

        Style card = () -> {
            display.block();
            border.radius(Theme.radius).width(1, px).solid().color(Theme.surface.lighten(Theme.front, 6));
            padding.left(1.6, em).vertical(1.2, em);
            background.color(Theme.surface.lighten(Theme.front, 3));
            text.decoration.none();
        };

        Style icon = () -> {
            display.size(1.5, rem);
            border.radius(50, percent);
            text.verticalAlign.middle();
            margin.right(1, rem);
        };

        Style star = () -> {
            display.size(24, px);
            fill.color(Theme.front);
        };

        Style fork = () -> {
            margin.right(2, em);

            $.before(() -> {
                font.family(Theme.icon);
                content.text("\\e80d");
                margin.right(0.5, rem);
            });
        };

        Style license = () -> {
            margin.right(2, em);

            $.before(() -> {
                font.family(Theme.icon);
                content.text("\\e90c");
                margin.right(0.5, rem);
            });
        };
    }
}
