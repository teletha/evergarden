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

import evergarden.AutoMemoriesDollModel;
import evergarden.Document;

public class ActivityPage extends DocumentPage {

    /**
     * @param depth
     * @param model
     * @param content
     */
    public ActivityPage(int depth, AutoMemoriesDollModel model, Document content) {
        super(depth, model, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareSubNavigation() {
        $("ul", foŕ(contents.children(), section -> {
            $("li", () -> {
                $("a", href("doc/changelog.html#" + section.id()), text(section.title()));
            });
        }));
    }
}