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

import java.util.List;

import evergarden.Document;
import evergarden.Letter;
import evergarden.Letter.Doc;
import evergarden.design.EvergardenDSL;
import evergarden.javadoc.ClassInfo;
import kiss.I;
import kiss.XML;
import stylist.Style;
import stylist.value.Numeric;

public class LandingPage extends Page<List<ClassInfo>> {

    /**
     * @param path
     * @param letter
     */
    public LandingPage(String path, Letter letter, List<ClassInfo> docs) {
        super(path, letter, docs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        $("div", css.root, () -> {
            $("h1", css.title, () -> $("code", text(letter.title())));
            $("p", css.description, text(letter.description()));

            $("div", css.box, () -> {
                $("a", href(letter.doc().map(Doc::path).or("")), css.button, text("Getting started"));
                $("a", href(""), css.button, text("Download " + letter.title()));
            });

            I.signal(contents)
                    .as(Document.class)
                    .recurseMap(x -> x.flatIterable(Document::children))
                    .take(doc -> doc.title().toLowerCase().equals("futures"))
                    .first()
                    .flatIterable(doc -> doc.contents().find("h4"))
                    .buffer(2)
                    .to(list -> {
                        $("section", css.overflowbox, () -> {
                            for (XML x : list) {
                                $("div", css.fuature, () -> {
                                    $("h3", css.featureTitle, text(x.text()));
                                    $(x.nextUntil("h4").clone());
                                });
                            }
                        });
                    });

            letter.authority().to(host -> {
                $("section", css.overflowbox, () -> {
                    $("a", css.card, href(host.location()), () -> {
                        $("div", css.name, () -> {
                            $("img", css.icon, src(host.icon()));
                            $("span", text(host.owner() + "  /  " + host.name()));
                        });
                        $("div", text(host.description()));
                        $("div", () -> {
                            $(svg("star"));
                            $("span", css.value, text(host.countStar()));
                            $(svg("fork"));
                            $("span", css.value, text(host.countFork()));
                            $(svg("license"));
                            $("span", css.value, text(host.license()));
                            $(svg("language"));
                            $("span", css.value, text(host.language()));
                        });
                    });
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

        Style root = () -> {
            display.width(70, percent);
            margin.auto();
        };

        Style title = () -> {
            font.size(4.5, rem).family(Theme.title);
            text.align.center();
            margin.top(3, rem);
        };

        Style description = () -> {
            font.size(1.3, rem);
            margin.vertical(3, rem);
            text.align.center().wrap.balance();
        };

        Style box = () -> {
            margin.bottom(2, rem);
            text.align.center();
        };

        Style overflowbox = () -> {
            display.minBlock(300, px).grid().column(Numeric.num(1, fr), Numeric.num(1, fr)).gap(6, rem);
            padding.vertical(4, rem);
            margin.horizontal(-12, dvw);

            $.nthChild("odd", () -> {
                background.viewportInline(Theme.surface.lighten(-10).opacify(-0.7));
            });

            $.nthChild("even", () -> {
                background.viewportInline(Theme.surface.lighten(10).opacify(-0.7));
            });
        };

        Style featureTitle = () -> {
            font.weight.normal();
            margin.bottom(1, em);
        };

        Style fuature = () -> {

            $.select("p", () -> {
                text.wrap.balance();
            });
        };

        Style button = () -> {
            display.inlineBlock().width(200, px);
            border.radius(Theme.radius).color(Theme.border).solid().width(2, px);
            padding.horizontal(1.5, rem).vertical(1, rem);
            font.size(1.2, rem);
            text.align.center().decoration.none();

            $.hover(() -> {
                border.color(Theme.link);
            });

            $.firstChild(() -> {
                margin.right(1, rem);
            });
        };

        Style card = () -> {
            display.block();
            border.radius(Theme.radius).width(1, px).solid().color(Theme.surface.lighten(Theme.front, 6));
            padding.horizontal(1.6, em).vertical(1.2, em);
            background.color(Theme.surface.lighten(Theme.front, 3));
            text.decoration.none();

            $.transit().easeIn().duration(0.15, s).when().hover(() -> {
                background.color(Theme.accent.opacify(-0.7));
            });

            $.select("svg", () -> {
                display.size(16, px);
                fill.current();
                transform.translateY(2, px);
            });
        };

        Style icon = () -> {
            display.size(2.3, rem);
            border.radius(50, percent);
            text.verticalAlign.middle();
            margin.right(1, rem);
        };

        Style name = () -> {
            font.size(1.3, em);
        };

        Style value = () -> {
            margin.right(2, rem).left(0.5, rem);
        };
    }
}
