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

import evergarden.Document;
import evergarden.Epistle;
import evergarden.design.EvergardenDSL;
import evergarden.design.Styles;
import kiss.I;
import kiss.XML;
import stylist.Style;
import stylist.value.Numeric;

public class DocumentPage extends Page<Document> {

    /**
     * @param epistle
     * @param content
     */
    public DocumentPage(String path, Epistle epistle, Document content) {
        super(path, epistle, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void transform(XML xml) {
        xml.find("h3").attr("icon", "📄");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        try {
            if (contents.hasContents()) {
                $("section", Styles.Section, Styles.JavadocComment, () -> {
                    write(2, contents, CSS.SectionLevel2, true);
                });
            }

            for (Document child : contents.children()) {
                if (child.hasContents()) {
                    $("div", Styles.Section, () -> {
                        $("section", id(child.id()), Styles.JavadocComment, () -> {
                            write(2, child, CSS.SectionLevel2, true);
                        });

                        for (Document foot : child.children()) {
                            if (foot.hasContents()) {
                                $("section", id(foot.id()), Styles.JavadocComment, CSS.foot, () -> {
                                    write(3, foot, CSS.SectionLevel3, false);
                                });
                            }
                        }
                    });
                }
            }
        } catch (Throwable e) {
            throw I.quiet(e);
        }
    }

    private void write(int level, Document document, Style additionalStyle, boolean useIcons) {
        XML doc = document.contents();
        XML heading = doc.find("h,h1,h2,h3").first().remove();

        $("header", Styles.JavadocComment, additionalStyle, () -> {
            $(xml(heading.size() != 0 ? heading : I.xml("h" + level).text(document.title())));
            if (useIcons) {
                $("div", CSS.meta, () -> {
                    $("span", clazz("perp"), CSS.icon, () -> {
                        $(svg("copy"));
                    });

                    $("a", clazz("tweet"), CSS.icon, () -> {
                        $(svg("twitter"));
                    });

                    epistle.authority().to(repo -> {
                        document.region().ifPresent(area -> {
                            String editor = repo.locateEditor(area);
                            if (editor != null) {
                                $("a", href(editor), clazz("edit"), CSS.icon, () -> {
                                    $(svg("edit"));
                                });
                            }
                        });
                    });
                });
            }
        });
        $(xml(doc));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareSubNavigation() {
    }

    interface CSS extends EvergardenDSL {

        Numeric IconSize = Numeric.num(16, px);

        Style SectionLevel2 = () -> {
            position.relative();
        };

        Style SectionLevel3 = () -> {
            position.relative();
        };

        Style meta = () -> {
            position.absolute().top(Numeric.num(50, percent).subtract(IconSize.divide(2)).subtract(3, px)).right(IconSize.divide(2));
        };

        Style icon = () -> {
            display.inlineBlock().width(IconSize).height(IconSize);
            font.lineHeight(1);
            margin.left(IconSize);
            cursor.pointer();
        };

        Style foot = () -> {
            margin.top(2.5, rem).bottom(1, rem);
        };
    }
}