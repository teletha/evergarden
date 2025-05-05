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
import evergarden.Letter;
import evergarden.design.EvergardenDSL;
import kiss.XML;
import stylist.Style;

public class ActivityPage extends DocumentPage<Document> {

    public ActivityPage(String path, Letter letter, Document content) {
        super(path, letter, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareSubNavigation() {
        $("div", APIPage.css.outline, foŕ(contents.getFirst().children(), sec -> {
            $("div", css.member, () -> {
                $("a", href("doc/changelog.html#" + sec.id()), text(sec.title()));
            });
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void transform(XML xml) {
        xml.find("h3:contains(Features)").attr("icon", "🚀");
        xml.find("h3:contains(Bug Fixes)").attr("icon", "🛠️");
        xml.find("h3:contains(Assets)").attr("icon", "💾");
        xml.find("h3:contains(⚠ BREAKING CHANGES)").attr("icon", "☢️").text("Breaking Changes");
        xml.find("section").removeClass(DocumentPage.CSS.foot.className()).addClass(css.foot.className());
    }

    private interface css extends EvergardenDSL {

        Style member = () -> {
            padding.vertical(0.2, em).left(0.4, em);

            $.has("a.now", () -> {
                font.color(Theme.accent).weight.bold();
            });
        };

        Style foot = () -> {
            padding.top(0.6, rem).bottom(1.2, rem);
        };
    }
}