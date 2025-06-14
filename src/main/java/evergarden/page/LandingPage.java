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
import evergarden.design.Styles;
import evergarden.host.Contributor;
import evergarden.host.Hosting;
import evergarden.host.Release;
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
                $("a", href(letter.authority()
                        .map(Hosting::releases)
                        .map(List::getFirst)
                        .map(Release::url)
                        .or("")), css.button, text("Download"));
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
                                $("div", css.feature, () -> {
                                    $("h3", css.heading, text(x.text()));
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
                            $("span", css.project, text(host.owner() + "  /  " + host.name()));
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

                    $("div", () -> {
                        $("h3", css.heading, text("🧑‍🔧 Contributors"));
                        for (Contributor contributor : host.contributors()) {
                            $("a", href(contributor.url()), attr("aria-label", contributor.name()), () -> {
                                $("img", css.icon, src(contributor.icon()));
                            });
                        }
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

        Style heading = () -> {
            font.weight.normal();
            margin.bottom(1, em);
        };

        Style feature = () -> {
            $.select("p", () -> {
                margin.bottom(1, em);
                text.align.justify().wordSpacing(-1, px);
            });
        };

        Style button = Styles.ACTIVATABLE_BOX.with(() -> {
            display.inlineBlock().width(200, px);
            padding.horizontal(1.5, rem).vertical(1, rem);
            font.size(1.2, rem);
            text.align.center().decoration.none();

            $.firstChild(() -> {
                margin.right(1, rem);
            });
        });

        Style card = Styles.ACTIVATABLE_BOX.with(() -> {
            display.grid().row(Numeric.num(4, ch), Numeric.num(1, fr), Numeric.num(2, ch)).rowGap(5, px);
            padding.horizontal(1.6, em).vertical(1.2, em);
            text.decoration.none();

            $.select("svg", () -> {
                display.size(16, px);
                fill.current();
                transform.translateY(2, px);
            });
        });

        Style icon = () -> {
            display.size(2.3, rem);
            border.radius(50, percent);
            text.verticalAlign.middle();
            margin.size(0.2, rem);
            transition.duration(0.15, s).easeIn().whenever();

            $.hover(() -> {
                transform.scale(1.3);
            });
        };

        Style project = () -> {
            margin.left(1, rem);
        };

        Style name = () -> {
            font.size(1.3, em);
        };

        Style value = () -> {
            margin.right(2, rem).left(0.5, rem);
        };
    }
}
