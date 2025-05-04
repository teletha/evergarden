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

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.DocSourcePositions;
import com.sun.source.util.DocTrees;
import com.sun.source.util.TreePath;

import jdk.javadoc.doclet.DocletEnvironment;
import kiss.I;

/**
 * Utility class for accessing and operating on elements of the Javadoc model
 * within a custom Doclet implementation.
 */
public final class Tool {

    /** Holds the current {@link DocletEnvironment} per thread. */
    static final InheritableThreadLocal<DocletEnvironment> ENVIRONMENT = new InheritableThreadLocal<>();

    /** Holds the current {@link VioletEvergarden} per thread. */
    static final InheritableThreadLocal<VioletEvergardenModel> DOLL = new InheritableThreadLocal();

    /** Thread-local {@link JavaParser} instance configured for Java 21. */
    private static final ThreadLocal<JavaParser> PARSER = ThreadLocal.withInitial(() -> {
        return new JavaParser(new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21));
    });

    /**
     * Returns the {@link DocTrees} utility from the current environment.
     *
     * @return the DocTrees instance.
     */
    public static DocTrees useDocTrees() {
        return ENVIRONMENT.get().getDocTrees();
    }

    /**
     * Returns the {@link Elements} utility from the current environment.
     *
     * @return the Elements instance.
     */
    public static Elements useElements() {
        return ENVIRONMENT.get().getElementUtils();
    }

    /**
     * Returns the {@link Types} utility from the current environment.
     *
     * @return the Types instance.
     */
    public static Types useTypes() {
        return ENVIRONMENT.get().getTypeUtils();
    }

    /**
     * Returns the current {@link JavaParser} instance.
     *
     * @return the JavaParser instance.
     */
    public static JavaParser useJavaParser() {
        return PARSER.get();
    }

    /**
     * Returns the {@link VioletEvergarden} from the current environment.
     *
     * @return the AutoMemoriesDoll instance.
     */
    public static VioletEvergardenModel useDoll() {
        return DOLL.get();
    }

    /**
     * Finds the top-level {@link TypeElement} that encloses the given element.
     * Skips nested classes and returns the outermost class or interface.
     *
     * @param e The element to analyze.
     * @return The top-level {@link TypeElement}.
     */
    public static TypeElement getTopLevelTypeElement(Element e) {
        Element parent = e.getEnclosingElement();

        while (parent != null && parent.getKind() != ElementKind.PACKAGE) {
            e = parent;
            parent = e.getEnclosingElement();
        }
        return (TypeElement) e;
    }

    /**
     * Determines the start and end line numbers of the Javadoc comment
     * associated with the given element.
     *
     * @param e The element whose Javadoc position is to be determined.
     * @return An array of two integers: [startLine, endLine].
     */
    public static int[] getDocumentLineNumbers(Element e) {
        try {
            DocTrees trees = useDocTrees();
            DocSourcePositions positions = trees.getSourcePositions();

            TreePath path = trees.getPath(e);
            CompilationUnitTree cut = path.getCompilationUnit();

            DocCommentTree tree = trees.getDocCommentTree(e);
            int start = (int) positions.getStartPosition(cut, tree, tree);
            int end = (int) positions.getEndPosition(cut, tree, tree);

            int[] lines = {1, 1};
            CharSequence chars = cut.getSourceFile().getCharContent(true);
            for (int i = 0; i < end; i++) {
                if (i == start) {
                    lines[0] = lines[1];
                }
                if (chars.charAt(i) == '\n') lines[1]++;
            }

            return lines;
        } catch (IOException x) {
            throw I.quiet(x);
        }
    }

    /**
     * Collects all direct and indirect supertypes of the given element's type,
     * separating superclasses and interfaces.
     *
     * @param type The base element.
     * @return An array containing two sets: [superTypes, interfaceTypes].
     */
    public static Set<TypeMirror>[] getAllTypes(Element type) {
        Types types = useTypes();
        Set<TypeMirror> supers = new LinkedHashSet<>();
        Set<TypeMirror> interfaces = new TreeSet<>(Comparator
                .<TypeMirror, String> comparing(t -> ((TypeElement) types.asElement(t)).getSimpleName().toString()));
        collect(type.asType(), supers, interfaces);

        return new Set[] {supers, interfaces};
    }

    /**
     * Recursively collects all supertypes and interfaces of the given type.
     *
     * @param type The base type.
     * @param superTypes A set to accumulate superclass types.
     * @param interfaceTypes A set to accumulate interface types.
     */
    private static void collect(TypeMirror type, Set<TypeMirror> superTypes, Set<TypeMirror> interfaceTypes) {
        for (TypeMirror up : useTypes().directSupertypes(type)) {
            if (up.toString().equals("java.lang.Object")) {
                continue;
            }

            Element e = useTypes().asElement(up);
            if (e.getKind() == ElementKind.INTERFACE) {
                interfaceTypes.add(up);
            } else {
                superTypes.add(up);
            }
            collect(up, superTypes, interfaceTypes);
        }
    }
}