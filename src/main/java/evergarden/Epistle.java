/*
 * Copyright (C) 2025 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package evergarden;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import evergarden.host.Hosting;
import evergarden.javadoc.ClassInfo;
import evergarden.javadoc.SampleInfo;
import kiss.Variable;

public abstract class Epistle {

    /**
     * The list of JPMS module names declared in the project.
     */
    public List<String> modules = new ArrayList();

    /**
     * The list of package names discovered during the scan, ordered by encounter.
     */
    public List<String> packages = new ArrayList();

    /**
     * The collection of type definitions (classes, interfaces, etc.) parsed from source files.
     */
    public List<ClassInfo> types = new ArrayList();

    /**
     * The tree of documentation nodes representing external documents and structured sections.
     */
    public List<Chapter> docs = new ArrayList();

    public abstract String title();

    public abstract String description();

    public abstract Charset charset();

    public abstract Variable<Hosting> authority();

    public abstract Variable<ClassInfo> doc();

    public abstract Variable<ClassInfo> api();

    public abstract List<SampleInfo> sample(String id);

    /**
     * Registers a new type into the repository, ensuring its package is recorded.
     *
     * @param info the type information to register
     */
    void register(ClassInfo info) {
        types.add(info);

        if (packages.indexOf(info.packageName) == -1) {
            packages.add(info.packageName);
        }
    }

    /**
     * Represents a node of documentation data.
     * <p>
     * Each node has a title, a path (typically a file path or URL),
     * and optional child documentation nodes.
     */
    public static record Chapter(String title, String path, List<Chapter> subs) {

        public Chapter(String title, String path) {
            this(title, path, new ArrayList());
        }
    }
}
