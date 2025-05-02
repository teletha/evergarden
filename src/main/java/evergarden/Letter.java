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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import evergarden.host.Hosting;
import evergarden.javadoc.ClassInfo;
import evergarden.javadoc.Util;
import kiss.Variable;
import psychopath.Directory;

/**
 * Represents the central metadata and configuration for a documentation project.
 * <p>
 * This abstract class serves as a container for various pieces of information gathered
 * during the documentation generation process, such as discovered modules, packages,
 * parsed class information, and the structure of external documentation files.
 * <p>
 * Subclasses must implement the abstract methods to provide project-specific details
 * like the project title, root directory, character encoding, hosting information,
 * and logic for retrieving code samples.
 */
public abstract class Letter {

    /**
     * The list of JPMS (Java Platform Module System) module names declared in the project.
     * This list is populated during the scanning phase.
     */
    public List<String> modules = new ArrayList();

    /**
     * The list of Java package names discovered within the project's source code
     * during scanning.
     */
    public List<String> packages = new ArrayList();

    /**
     * A collection of {@link ClassInfo} objects representing type definitions
     * (classes, interfaces, enums, records) parsed from the project's Java source files.
     */
    public List<ClassInfo> types = new ArrayList();

    /**
     * A tree structure representing external documentation files (e.g., Markdown files)
     * and their logical hierarchy, defined using {@link Doc} nodes. This typically
     * forms the main navigation structure of the generated documentation site.
     */
    public List<Doc> docs = new ArrayList();

    /**
     * Internal map storing sample code snippets associated with method or identifier IDs.
     * <p>
     * The key is typically a unique identifier such as a method signature or tag, and
     * the value is a list of {@link Doodle} objects associated with that identifier.
     * This map is populated during parsing or sample registration.
     */
    private final Map<String, List<Doodle>> doodles = new ConcurrentHashMap();

    /**
     * Gets the root directory of this documentation project.
     *
     * @return the root directory
     */
    public abstract Directory address();

    /**
     * Returns the main title of this documentation project.
     * This title is typically displayed prominently on the generated site.
     *
     * @return a human-readable title string. Should not be null or empty.
     */
    public abstract String title();

    /**
     * Returns a brief description of the project.
     * This description might be used in meta tags or introductory sections.
     *
     * @return the project description string. Can be empty but should not be null.
     */
    public abstract String description();

    /**
     * Returns the character set (encoding) used for reading source files and
     * writing output files within this project.
     *
     * @return the {@link Charset} for the project. Must not be null.
     */
    public abstract Charset charset();

    /**
     * Returns the hosting context or origin authority of the documentation.
     *
     * @return a variable representing hosting information
     */
    public abstract Variable<Hosting> authority();

    /**
     * Returns the root documentation node from the {@link #docs} list, if available.
     * This is often used as the entry point for navigating the external documentation structure.
     *
     * @return a {@link Variable} containing the root {@link Doc}. Empty if {@link #docs} is empty.
     */
    public final Variable<Doc> doc() {
        return docs.isEmpty() ? Variable.empty() : Variable.of(docs.getFirst());
    }

    /**
     * Returns the primary API documentation entry point, typically the first discovered
     * {@link ClassInfo} from the {@link #types} list, if available. This might represent
     * the main class or entry point of the documented library/application.
     *
     * @return a {@link Variable} containing the first {@link ClassInfo}. Empty if {@link #types} is
     *         empty.
     */
    public final Variable<ClassInfo> api() {
        return types.isEmpty() ? Variable.empty() : Variable.of(types.getFirst());
    }

    /**
     * Retrieves sample code blocks associated with a given identifier.
     *
     * @param id the sample identifier
     * @return a list of matching sample information entries
     */
    public final List<Doodle> doodle(String id) {
        return doodles.getOrDefault(id, Collections.EMPTY_LIST);
    }

    /**
     * Registers a new type into the repository, ensuring its package is recorded.
     *
     * @param info the type information to register
     */
    final void register(ClassInfo info) {
        types.add(info);

        if (packages.indexOf(info.packageName) == -1) {
            packages.add(info.packageName);
        }
    }

    /**
     * Registers a {@link Doodle} sample snippet with this project, organized by its ID.
     * If the ID already exists, the snippet is added to the list of existing samples.
     *
     * @param info the doodle to register. Must not be null.
     */
    @SuppressWarnings("unused")
    final void register(Doodle info) {
        doodles.computeIfAbsent(info.id(), x -> new ArrayList()).add(info);
    }

    /**
     * Establishes the subtype relationships among all collected types.
     * <p>
     * For each known type, this method identifies its direct and indirect supertypes
     * and registers the current type as a subtype of them.
     * <p>
     * This operation is optimized to run in linear time relative to the number of types,
     * using a fast lookup table instead of exhaustive comparison.
     */
    final void buildTypeRelationship() {
        Map<Element, ClassInfo> cache = new HashMap<>();
        for (ClassInfo info : types) {
            cache.put(info.e, info);
        }

        for (ClassInfo type : types) {
            for (Set<TypeMirror> uppers : Util.getAllTypes(type.e)) {
                for (TypeMirror upper : uppers) {
                    Element e = Util.TypeUtils.get().asElement(upper);
                    ClassInfo parent = cache.get(e);
                    if (parent != null) {
                        parent.addSub(type);
                    }
                }
            }
        }
    }

    /**
     * Constructs a hierarchical documentation tree from the given list of {@link ClassInfo}
     * documents.
     * <p>
     * This tree is composed of {@link Doc} nodes, where each top-level document becomes a chapter,
     * its children become sections, and so on.
     *
     * @param documents the list of documentation entries to convert into a {@link Doc} structure.
     */
    final void buildDocumentTree(List<ClassInfo> documents) {
        for (ClassInfo info : documents) {
            Doc chapter = new Doc(info.title(), "doc/" + info.id() + ".html");
            docs.add(chapter);

            for (Document child : info.children()) {
                Doc childChapter = new Doc(child.title(), "doc/" + info.id() + ".html#" + child.id());
                chapter.subs().add(childChapter);

                for (Document grandchild : child.children()) {
                    childChapter.subs().add(new Doc(grandchild.title(), "doc/" + info.id() + ".html#" + grandchild.id()));
                }
            }
        }
    }

    /**
     * Represents a logical unit or node within the documentation structure, typically
     * corresponding to a file (e.g., Markdown) or a conceptual section.
     * <p>
     * A documentation node includes a display title, a reference path (which might be a
     * file path, URL segment, or identifier), and potentially a list of nested sub-nodes
     * (children) to represent hierarchical relationships (e.g., chapters and sections).
     *
     * @param title the display title of this documentation node (e.g., for navigation menus).
     * @param path the associated reference path (e.g., relative file path like "guide/intro.md").
     * @param subs the {@link List} of child {@link Doc} nodes, representing nested structure.
     *            An empty list indicates this node has no children.
     */
    public static record Doc(String title, String path, List<Doc> subs) {

        /**
         * Convenience constructor to create a new documentation node (a leaf node)
         * without any sub-nodes. Initializes the sub-node list as an empty list.
         *
         * @param title the title of the node.
         * @param path the path associated with the node.
         */
        public Doc(String title, String path) {
            this(title, path, new ArrayList());
        }
    }
}
