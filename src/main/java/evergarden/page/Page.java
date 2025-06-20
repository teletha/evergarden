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

import java.time.LocalDate;

import evergarden.Letter;
import evergarden.design.EvergardenDSL;
import evergarden.design.Styles;
import evergarden.host.Hosting;
import evergarden.web.HTML;
import kiss.XML;
import stylist.Query;
import stylist.Style;
import stylist.Stylist;
import stylist.property.Background.BackgroundImage;
import stylist.property.helper.Content;
import stylist.property.helper.Items;
import stylist.value.Color;
import stylist.value.Font;
import stylist.value.Numeric;

public abstract class Page<T> extends HTML {

    public final String path;

    protected final Letter letter;

    protected final T contents;

    private final String base;

    /**
     * @param letter
     * @param content
     */
    protected Page(String path, Letter letter, T content) {
        this.path = path;
        this.letter = letter;
        this.contents = content;
        this.base = "../".repeat((int) path.chars().filter(c -> c == '/').count());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void declare() {
        $("html", attr("lang", "en"), () -> {
            $("head", () -> {
                $("meta", charset(letter.encoding().displayName()));
                $("meta", name("viewport"), content("width=device-width, initial-scale=1"));
                $("meta", name("description"), content("Explains how to use " + letter.title() + " and its API. " + letter.description()));
                $("link", rel("preconnect"), href("https://cdn.jsdelivr.net"));
                $("link", rel("preconnect"), href("https://fonts.googleapis.com"));
                $("link", rel("preconnect"), href("https://fonts.gstatic.com"), attr("crossorigin"));
                for (Font font : Font.fromGoogle()) {
                    stylesheetAsync(font.uri);
                }
                $("title", text(letter.title() + " API"));
                $("base", href(base));
                module("mimic.js");
                stylesheet(Stylist.NormalizeCSS);
                stylesheet("main.css");
            });
            $("body", css.body, () -> {
                // =============================
                // Top Navigation
                // =============================
                $("header", css.header, attr("date", letter.authority().map(Hosting::getLatestPublishedDate).or(LocalDate.now())), () -> {
                    $("h1", css.title, () -> {
                        $("a", href("index.html"), code(letter.title()));
                    });
                    $("nav", css.links, () -> {
                        letter.doc().to(doc -> link("Manual", doc.path(), "text"));
                        letter.api().to(api -> link("API", "api/main.html", "package"));
                        letter.authority().to(repo -> {
                            link("Activity", "doc/changelog.html", "activity");
                            link("Community", repo.locateCommunity(), "user");
                            link("Repository", repo.location(), "github");
                        });
                    });
                    $("div", css.controls, () -> {
                        $("a", id("theme"), attr("aria-label", "Change color scheme"), () -> {
                            $(svg("sun"));
                            $(svg("moon"));
                        });
                    });
                });

                // =============================
                // Navigation
                // =============================
                $("nav", css.nav);

                // =============================
                // Main Contents
                // =============================
                $("article", id("Article"), css.article, () -> {
                    if (contents != null) {
                        declareContents();
                    }
                });

                // =============================
                // Aside
                // =============================
                $("aside", css.aside, () -> {
                    if (contents != null) {
                        declareAside();
                    }
                });

                $("footer", css.footer, () -> {
                    declareFooter();
                });
            });

            script("root.js");
            module("main.js");
        });

        root.forEach(this::transform);
    }

    /**
     * Transform HTML
     * 
     * @param xml
     */
    protected void transform(XML xml) {
    }

    private void link(String id, String path, String icon) {
        if (path.startsWith("http")) {
            $("a", href(path), svg(icon), id(id), attr("target", "_blank"));
        } else {
            $("a", href(path), svg(icon), id(id));
        }

    }

    protected abstract void declareContents();

    protected abstract void declareAside();

    protected void declareFooter() {
        $("p", text("Powered by "), () -> {
            $("a", href("https://github.com/teletha/evergarden"), attr("target", "_"), text("Evergarden"));
        });
    }

    private interface css extends EvergardenDSL {

        Numeric HEADER_HEIGHT = Numeric.num(80, px);

        Numeric GAP = Numeric.num(2, ch);

        Numeric BODY_TOP = HEADER_HEIGHT.plus(GAP);

        Numeric BODY_HEIGHT = Numeric.num(100, dvh).subtract(BODY_TOP);

        Query BASE = Query.all().width(0, 800, px);

        Query MIDDLE = Query.all().width(800, 1200, px);

        Query LARGE = Query.all().width(1200, px);

        Style nav = () -> {
            display.width(100, percent);
            position.sticky().top(BODY_TOP);

            $.when(BASE, () -> {
                margin.top(1, rem);
                padding.vertical(1, rem).horizontal(2, rem);
                background.color(Theme.back.opacify(0.9)).image(Theme.backImage);
            });

            $.child(() -> {
                display.grid().align(Content.Start).rowGap(0.5, rem).flowRow();
                font.color(Theme.front.lighten(Theme.back, -15)).letterSpacing(-0.5, px).lineHeight(1.6);

                $.attr("data-hide", "true", () -> {
                    display.none();
                });

                $.lastChild(() -> {
                    display.height(BODY_HEIGHT);
                    scroll.thin().color(Theme.front.opacify(-0.7), Color.Transparent);
                    overflow.y.auto();
                    EvergardenDSL.scrollable();
                });
            });

            $.select(".doc", () -> {
                $.select("li", () -> {
                    padding.vertical(0.1, rem);
                });

                $.when(Small, () -> {
                    margin.bottom(0.2, rem);
                });

                $.select("a").hover(() -> {
                    font.color(Theme.accent);
                });
            });

            $.select(".sub", () -> {
                display.height(0, px);
                font.size(0.9, em).color(Theme.front.lighten(Theme.back, 7));
                overflow.y.hidden();
                transition.duration(0.3, s).whenever();

                $.when(Small, () -> {
                    display.none();
                });

                $.select("a", () -> {
                    padding.left(0.8, rem).vertical(0.3, rem);
                    border.left.solid().width(2, px).color(Color.hsl(0, 0, 65));
                    $.with(".foot", () -> {
                        padding.left(1.6, rem);
                    });

                    $.with(".now", () -> {
                        border.left.color(Theme.accent);
                        background.color(Theme.accent.opacify(-0.9));
                    });

                    $.firstMatch(".now", () -> {
                        border.top.radius(Theme.radius);
                    });

                    $.lastMatch(".now", () -> {
                        border.right.radius(Theme.radius);
                    });

                });
            });

            $.select("a", () -> {
                display.block();
                text.decoration.none().whiteSpace.pre();
                padding.bottom(0.1, rem);
            });
        };

        Style controls = () -> {

            $.select("#theme", () -> {
                display.width(20, px).height(20, px);
                margin.right(1, em);
            });

            $.select("svg", Styles.SVGButtonEffect.with(() -> {
                display.size(20, px);
                stroke.current().width(2.1, px);
                fill.none();
            }));

            $.when(EvergardenDSL.Small, () -> {
                display.none();
            });
        };

        Style theme = Style.named(".light .moon, .dark .sun", () -> {
            display.none();
        });

        Style title = () -> {
            font.size(2.5, rem).family(Theme.title).weight.normal().color(Theme.primary);

            $.when(BASE, () -> {
                padding.inlineStart(2, rem);
            });

            $.select("a", () -> {
                text.decoration.none();
            });
        };

        Style links = () -> {
            Numeric iconMargin = Numeric.num(4, px);
            Numeric iconSize = Numeric.num(24, px);

            display.width(60, percent).grid().flowColumn().gap(iconMargin).column(x -> x.repeatAutoFit(iconSize, 1, fr));

            $.child(() -> {
                font.size(12, px).color(Theme.front);
                display.inlineFlex().alignItems.center().direction.column();
                text.decoration.none().whiteSpace.nowrap();
                transition.duration(0.2, s).whenever();

                $.after(() -> {
                    content.attr("id");

                    $.when(BASE, () -> {
                        font.size.smaller();
                    });
                });

                $.select("svg", () -> {
                    display.size(iconSize);
                    stroke.color(Theme.front.lighten(Theme.back, -15)).width(1.2, px);
                    fill.none();
                    transition.duration(0.2, s).whenever();
                });

                $.hover(() -> {
                    text.decoration.none();
                    font.color(Theme.link);

                    $.select("svg", () -> {
                        stroke.color(Theme.link).width(2, px);
                        transform.translateY(-4, px);
                    });
                });
            });
        };

        Style header = () -> {
            background.color(Color.Inherit).image(BackgroundImage.inherit()).repeat();
            position.sticky().top(0, rem);
            padding.top(22, px);
            border.bottom.color(Theme.primary).width(1, px).solid();
            display.width(100, percent)
                    .zIndex(10)
                    .grid()
                    .align(Items.Center)
                    .justify(Items.Center)
                    .column(x -> x.autoMax(1, fr).autoMax(91, ch).autoMax(1, fr))
                    .area(title, links, controls);

            $.after(() -> {
                position.absolute().top(5, px).right(1, rem);
                content.text("Updated\\00A0").attr("date");
                font.size(0.8, rem).color(Theme.front).family(Theme.base).letterSpacing(-0.5, px);
                text.whiteSpace.pre();
            });
        };

        Style article = () -> {
            display.opacity(1).width(100, percent);
            font.letterSpacing(-0.025, rem);
            transition.duration(0.15, s).ease().whenever();

            $.with(".fadeout", () -> {
                display.opacity(0);
            });
        };

        Style aside = () -> {
            display.width(100, percent);
            position.sticky().top(BODY_TOP);

            $.when(BASE, MIDDLE, () -> {
                display.none();
            });
        };

        Style footer = () -> {
            font.size(0.8, rem);
            text.align.center();
            padding.vertical(1, rem);
        };

        Style body = () -> {
            background.color(Theme.back).image(Theme.backImage).repeat();
            font.size(Theme.font).family(Theme.base).color(Theme.front.lighten(Theme.back, 5)).lineHeight(Theme.line);
            margin.horizontal(Numeric.max(20, px, 5, dvw));

            $.when(BASE, () -> {
                display.grid().area(header).area(article).area(footer).area(nav);
                margin.size(0, px);
            });

            $.when(MIDDLE, () -> {
                display.grid()
                        .gap(GAP)
                        .align(Items.Start)
                        .justify(Items.Center)
                        .column(x -> x.minmax(30, ch, 1, fr).autoMax(91, ch))
                        .row($.num(80, px), $.num(1, fr))
                        .area(header, header)
                        .area(nav, article)
                        .area(null, footer);
            });

            $.when(LARGE, () -> {
                display.grid()
                        .gap(GAP)
                        .align(Items.Start)
                        .justify(Items.Center)
                        .column(x -> x.minmax(30, ch, 1, fr).autoMax(91, ch).autoMax(1, fr))
                        .row($.num(80, px), $.num(1, fr))
                        .area(header, header, header)
                        .area(nav, article, aside)
                        .area(null, footer, null);
            });
        };
    }
}