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

import java.util.List;

import evergarden.Document;
import evergarden.Letter;

public class DocumentOnePage<D extends Document> extends AbstractDocumentPage<List<D>> {

    public DocumentOnePage(String path, Letter letter, List<D> contents) {
        super(path, letter, contents);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareContents() {
        for (Document content : contents) {
            write(content);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void declareSubNavigation() {
    }
}