/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import kiss.XML;

/**
 * A fragile vessel for words, carrying a document's identity, title, and memory.
 * 
 * Each implementation shapes how a document reveals its name, content, and hidden chapters.
 */
public interface Document {

    /**
     * Retrieves the name whispered to this document — its unique identity.
     *
     * @return the identifier string, never {@code null}
     */
    String id();

    /**
     * Retrieves the title bestowed upon this document.
     *
     * @return the title string, never {@code null}
     */
    String title();

    /**
     * Returns the place where this document once took root — a file path, a URL,
     * or perhaps somewhere less tangible.
     *
     * @return an {@link Optional} containing the document's {@link Region},
     *         or an empty {@link Optional} if no origin is recorded
     */
    default Optional<Region> region() {
        return Optional.empty();
    }

    /**
     * Weaves the contents of this document into a structured form,
     * ready to be read or remembered.
     *
     * @return an {@link XML} structure representing the document's inner world
     */
    XML contents();

    /**
     * Determines whether this document holds any contents within.
     *
     * @return {@code true} if something lies within, {@code false} if it remains silent
     */
    default boolean hasContents() {
        return contents() != null;
    }

    /**
     * Lists the hidden pages nested beneath this document.
     * 
     * Returns an empty list if no sub-documents are found.
     *
     * @return a list of child {@link Document} instances
     */
    default List<Document> children() {
        return Collections.EMPTY_LIST;
    }
}
