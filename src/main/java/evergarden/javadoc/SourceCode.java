/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden.javadoc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.DocSourcePositions;
import com.sun.source.util.TreePath;

import kiss.I;
import psychopath.Directory;
import psychopath.File;

public class SourceCode {

    private static final ThreadLocal<JavaParser> threadLocalParser = ThreadLocal.withInitial(() -> {
        return new JavaParser(new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21));
    });

    /**
     * Get the source code of the specified class.
     */
    public static String read(String fqcn, String memberDescriptor, boolean bodyOnly) {
        try {
            for (Directory sample : Util.Samples.get()) {
                List<String> split = List.of(fqcn.split("\\."));
                int max = split.size();
                int current = max;
                while (0 < current) {
                    File file = sample.file(split.subList(0, current--).stream().collect(Collectors.joining("/", "", ".java")));
                    if (file.isPresent()) {
                        Deque<String> members = new ArrayDeque();
                        if (current + 1 != max) split.subList(current + 1, max).forEach(members::add);
                        if (memberDescriptor != null) members.add(memberDescriptor);

                        Node node = threadLocalParser.get()
                                .parse(new FileInputStream(file.asJavaFile()))
                                .getResult()
                                .orElseThrow()
                                .findRootNode()
                                .removeComment();

                        // remove package declaration
                        node.findAll(PackageDeclaration.class).forEach(PackageDeclaration::remove);

                        // remove unnecessary annotations
                        for (MethodDeclaration method : node.findAll(MethodDeclaration.class)) {
                            String[] removables = {"Override", "SuppressWarnings"};
                            for (String removable : removables) {
                                method.getAnnotationByName(removable).ifPresent(AnnotationExpr::remove);
                            }
                        }

                        root: while (!members.isEmpty()) {
                            memberDescriptor = members.pollFirst();

                            for (MethodDeclaration method : node.findAll(MethodDeclaration.class)) {
                                if (method.getSignature().asString().equals(memberDescriptor)) {
                                    return readCode(file, method, bodyOnly);
                                }
                            }

                            for (ClassOrInterfaceDeclaration type : node.findAll(ClassOrInterfaceDeclaration.class)) {
                                if (type.getNameAsString().equals(memberDescriptor)) {
                                    if (members.isEmpty()) {
                                        return readCode(file, type, bodyOnly);
                                    } else {
                                        node = type;
                                        continue root;
                                    }
                                }
                            }

                            for (RecordDeclaration type : node.findAll(RecordDeclaration.class)) {
                                if (type.getNameAsString().equals(memberDescriptor)) {
                                    if (members.isEmpty()) {
                                        String code = readCode(file, type, bodyOnly);

                                        // special optimization for empty record
                                        code = code.replaceAll("\\s*\\{\\s+\\}\\s*$", "");

                                        return code;
                                    } else {
                                        node = type;
                                        continue root;
                                    }
                                }
                            }

                            for (FieldDeclaration field : node.findAll(FieldDeclaration.class)) {
                                for (VariableDeclarator variable : field.findAll(VariableDeclarator.class)) {
                                    if (variable.getNameAsString().equals(memberDescriptor)) {
                                        return readCode(file, field, false);
                                    }
                                }
                            }
                        }
                        return node.toString();
                    }
                }
            }
        } catch (Exception e) {
            throw I.quiet(e);
        }
        return "";
    }

    /**
     * Get the source code from source file.
     * 
     * @param file
     * @param node
     * @return
     */
    static String readCode(File file, Node node, boolean bodyOnly) {
        int start = node.getBegin().get().line;
        int end = node.getEnd().get().line;
        if (node instanceof FieldDeclaration) start--;
        if (bodyOnly) {
            start += 2;
            end--;
        }
        String code = file.lines().skip(start - 1).take(end - start + 1).scan(Collectors.joining("\r\n")).to().exact();
        return stripHeaderWhitespace(code);
    }

    /**
     * Get the source code of the specified {@link Element}.
     * 
     * @param doc
     * @return
     */
    public static String read(DocumentInfo doc) {
        try {
            DocSourcePositions positions = Util.DocUtils.get().getSourcePositions();

            TreePath path = Util.DocUtils.get().getPath(doc.e);
            CompilationUnitTree cut = path.getCompilationUnit();

            int start = (int) positions.getStartPosition(cut, path.getLeaf());
            int end = (int) positions.getEndPosition(cut, path.getLeaf());
            return stripHeaderWhitespace(cut.getSourceFile().getCharContent(true).subSequence(start, end).toString());
        } catch (IOException error) {
            throw I.quiet(error);
        }
    }

    /**
     * Strip whitespace prettily for the formatted source code.
     * 
     * @param text
     * @return
     */
    public static String stripHeaderWhitespace(String text) {
        List<String> lines = I.list(text.split("\\r\\n|\\r|\\n"));

        if (lines.size() == 1) {
            return text.trim();
        }

        // remove the empty line from head
        ListIterator<String> iter = lines.listIterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.isEmpty()) {
                iter.remove();
            } else {
                break;
            }
        }

        // remove the empty line from tail
        iter = lines.listIterator(lines.size());
        while (iter.hasPrevious()) {
            String line = iter.previous();
            if (line.isEmpty()) {
                iter.remove();
            } else {
                break;
            }
        }

        // remove package declaration and annotations (@Override)
        iter = lines.listIterator();
        while (iter.hasNext()) {
            String line = iter.next().trim();
            if (line.equals("@Override") || line.startsWith("@SuppressWarnings") || line.startsWith("@Test")) {
                iter.remove();
            } else if (line.startsWith("package ")) {
                iter.remove();
            }
        }

        // strip the common width indent
        int indent = computeIndentSize(lines);
        return lines.stream().map(line -> stripIndent(line, indent)).collect(Collectors.joining("\r\n"));
    }

    /**
     * Compute the minimum size of indent.
     * 
     * @param lines
     * @return
     */
    static int computeIndentSize(List<String> lines) {
        return lines.stream().mapToInt(line -> {
            for (int i = 0; i < line.length(); i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    return i;
                }
            }
            return -1;
        }).filter(size -> 0 <= size).min().orElse(0);
    }

    /**
     * Remove the indent.
     * 
     * @param line
     * @param indentSize
     * @return
     */
    private static String stripIndent(String line, int indentSize) {
        if (line.length() < indentSize) {
            return line;
        }

        for (int i = 0; i < indentSize; i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return line;
            }
        }
        return line.substring(indentSize);
    }

}