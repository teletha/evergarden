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

import java.util.ArrayList;
import java.util.List;

import evergarden.javadoc.ClassInfo;

/**
 * A scanned snapshot of the project's structure and documentation.
 * <p>
 * This repository holds modules, packages, types, and documentation entries extracted from source
 * code. It acts as a memory of the scanned landscape, ready to be processed into final
 * documentation.
 * <p>
 * Modules represent JPMS modules declared via {@code module-info.java}.
 * Packages and types are collected as encountered, preserving their original discovery order.
 * Documentation nodes are managed as a tree of {@link Document} implementations.
 */
public class Index {

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
    public List<Doc> docs = new ArrayList();

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
    public static record Doc(String title, String path, List<Doc> subs) {

        public Doc(String title, String path) {
            this(title, path, new ArrayList());
        }
    }
}