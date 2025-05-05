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

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import evergarden.page.Page;
import evergarden.web.HTML;
import kiss.I;
import kiss.XML;
import psychopath.Directory;
import psychopath.File;
import psychopath.Locator;
import stylist.StyleDSL;
import stylist.StyleDeclarable;
import stylist.Stylist;

/**
 * A builder class responsible for generating static site assets such as HTML, CSS, JS, and JSONP.
 * <p>
 * This builder manages the root output directory, ensures proper cleanup of previous builds
 * (excluding protected files), and provides methods for writing various file types to the
 * filesystem.
 */
public class SiteBuilder {

    /** The root directory. */
    private final Directory root;

    /** The initial protectable file pattern. */
    private List<String> protectable = I.list("!**@.*");

    /**
     * Hide constructor
     */
    private SiteBuilder(Directory root) {
        this.root = Objects.requireNonNull(root);

        // delete all existing files
        root.create().delete(protectable.toArray(String[]::new));

        // There is a time lag until the OS releases the handle of the deleted file, so wait a
        // little. AccessDeniedException may occur when going straight.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw I.quiet(e);
        }
    }

    /**
     * Specify a pattern for files that you do not want to delete during initialization.
     * 
     * @param patterns
     * @return
     */
    public final SiteBuilder guard(String... patterns) {
        for (String pattern : patterns) {
            if (pattern != null && pattern.length() != 0) {
                protectable.add("!" + pattern);
            }
        }
        return this;
    }

    private static final String[] characterType = {"figcaption", "figure", "a", "abbr", "b", "bdi", "bdo", "cite", "code", "data", "dfn",
            "em", "i", "kbd", "mark", "q", "rb", "rp", "rt", "rtc", "s", "samp", "span", "strong", "sub", "sup", "time", "u", "var", "del",
            "ins", "&script", "&nav", "&article", "&aside", "&dl", "&div", "&i", "&td"};

    /**
     * Build HTML file.
     */
    public final void buildHTML(Page page) {
        buildHTML(page.path, page);
    }

    /**
     * Build HTML file.
     */
    public final void buildHTML(String path, HTML html) {
        html.declare();

        root.file(path).write(output -> {
            output.append("<!DOCTYPE html>\r\n");

            for (XML node : html.root) {
                node.to(output, "\t", characterType);
            }
        });
    }

    /**
     * Build CSS file and return the path of the generated file.
     * 
     * @return A path to the generated file.
     */
    public final String buildCSS(String path) {
        String formatted = Stylist.pretty().importNormalizeStyle().format();

        File file = root.file(path);
        file.write(output -> output.append(formatted));
        return root.relativize(file).path();
    }

    /**
     * Build CSS file and return the path of the generated file.
     * 
     * @param styles A style definition class to write.
     * @return A path to the generated file.
     */
    public final String buildCSS(String path, Class<? extends StyleDSL> styles) {
        String formatted = Stylist.pretty().importNormalizeStyle().styles(styles).format();

        File file = root.file(path);
        file.write(output -> output.append(formatted));
        return root.relativize(file).path();
    }

    /**
     * Build CSS file and return the path of the generated file.
     * 
     * @param styles A style definition class to write.
     * @return A path to the generated file.
     */
    public final String buildCSS(String path, StyleDeclarable styles) {
        String formatted = Stylist.pretty().importNormalizeStyle().styles(styles).format();

        File file = root.file(path);
        file.write(output -> output.append(formatted));
        return root.relativize(file).path();
    }

    /**
     * Build JS file and return the path of the generated file.
     * 
     * @return A path to the generated file.
     */
    public final String build(String path, InputStream input) {
        File file = root.file(path);
        file.writeFrom(input);

        return root.relativize(file).path();
    }

    /**
     * Build JS file and return the path of the generated file.
     * 
     * @return A path to the generated file.
     */
    public final String build(String path, InputStream input, List<String> additions) {
        File file = root.file(path);
        file.writeFrom(input);
        for (String add : additions) {
            file.textAtTail(add);
        }
        return root.relativize(file).path();
    }

    /**
     * Build JSON file with padding.
     */
    public final String buildJSONP(String path, Object object) {
        File file = root.file(path);
        file.write(output -> {
            output.append("const " + file.base() + " = ");
            I.write(object, output);
        });
        return root.relativize(file).path();
    }

    /**
     * Configure root directory.
     * 
     * @param pathToRootDirectory
     * @return
     */
    public static SiteBuilder root(String pathToRootDirectory) {
        return root(Locator.directory(pathToRootDirectory));
    }

    /**
     * Configure root directory.
     * 
     * @param pathToRootDirectory
     * @return
     */
    public static SiteBuilder root(Path pathToRootDirectory) {
        return root(Locator.directory(pathToRootDirectory));
    }

    /**
     * Configure root directory.
     * 
     * @param rootDirectory
     * @return
     */
    public static SiteBuilder root(Directory rootDirectory) {
        return new SiteBuilder(rootDirectory);
    }
}