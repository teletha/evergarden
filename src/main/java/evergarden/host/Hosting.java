/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden.host;

import java.net.URI;
import java.time.LocalDate;

import evergarden.Document;
import evergarden.Region;
import kiss.Variable;

/**
 * Represents an abstraction for repository hosting platforms (e.g., GitHub).
 * <p>
 * This class provides a unified interface to compute platform-specific URLs and
 * retrieve metadata such as changelogs and publish dates. Concrete subclasses
 * should implement service-specific behavior.
 * </p>
 */
public interface Hosting {

    /**
     * Returns the name of hosting service.
     * 
     * @return
     */
    String service();

    String name();

    String owner();

    /**
     * Returns the URL of the repository's main page.
     * 
     * @return The repository URL.
     */
    String location();

    /**
     * Returns the URL of the community discussion page.
     * 
     * @return The community page URL.
     */
    String locateCommunity();

    /**
     * Returns the URL of the changelog page.
     * 
     * @return The changelog page URL.
     */
    String locateChangeLog();

    String locateReadme();

    /**
     * Returns the URL of the issue tracker page.
     * 
     * @return The issue tracker page URL.
     */
    String locateIssues();

    /**
     * Returns the URL of the issue tracker page.
     * 
     * @return The issue tracker page URL.
     */
    String locateNewIssue(String title, String label, String body);

    /**
     * Returns the URL to read a specific code or documentation.
     * 
     * @param region The region to locate the read for.
     * @return The reader page URL.
     */
    String locateReader(Region region);

    /**
     * Returns the URL to edit a specific region of code or documentation.
     * 
     * @param region The region to locate the editor for.
     * @return The editor page URL.
     */
    String locateEditor(Region region);

    /**
     * Builds a {@link Document} representing the parsed changelog content.
     * 
     * @param text The raw changelog text.
     * @return A {@link Document} instance representing the changelog.
     */
    Document getChangeLog(String text);

    /**
     * Retrieves the published date of the latest version.
     * 
     * @return The latest published date as a string.
     */
    LocalDate getLatestPublishedDate();

    String language();

    int countFork();

    int countStar();

    int countWatch();

    int countIssue();

    String icon();

    String id();

    String description();

    String license();

    /**
     * Infers and constructs a {@link Hosting} instance from the given URI string.
     * 
     * @param uri The URI as a string.
     * @return A {@link Hosting} instance, or {@code null} if unsupported.
     */
    static Variable<Hosting> of(String uri) {
        return uri == null ? Variable.empty() : of(URI.create(uri));
    }

    /**
     * Infers and constructs a {@link Hosting} instance from the given {@link URI}.
     * 
     * @param uri The {@link URI}.
     * @return A {@link Hosting} instance, or {@code null} if unsupported.
     */
    static Variable<Hosting> of(URI uri) {
        if (uri == null) {
            return null;
        }

        switch (uri.getHost()) {
        case "github.com":
            return Variable.of(new Github(uri));

        default:
            return Variable.empty();
        }
    }
}