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

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import antibug.CleanRoom;
import evergarden.javadoc.ClassInfo;
import evergarden.javadoc.MethodInfo;
import kiss.I;
import kiss.Variable;
import kiss.XML;

public class EvergardenTestSupport {

    private static final CleanRoom room = new CleanRoom(true);

    private static final Letter doc = Violet.with.address(room.locateDirectory("normal"))
            .title("test")
            .sources("src/test/java")
            .mute()
            .write();

    private static final Letter docEx = Violet.with.address(room.locateDirectory("external"))
            .title("test")
            .sources("src/test/java")
            .mute()
            .useExternalJDKDoc()
            .write();

    protected final MethodInfo currentMethod() {
        StackFrame frame = caller();

        return findByClassName(doc, frame.getClassName()).exact()
                .findByMethodSignature(frame.getMethodName(), frame.getMethodType().parameterArray())
                .exact();
    }

    protected final MethodInfo currentMethodEx() {
        StackFrame frame = caller();

        return findByClassName(docEx, frame.getClassName()).exact()
                .findByMethodSignature(frame.getMethodName(), frame.getMethodType().parameterArray())
                .exact();
    }

    protected final MethodInfo method(String name) {
        StackFrame frame = caller();

        return findByClassName(doc, frame.getClassName()).exact().findByMethodSignature(name).exact();
    }

    /**
     * Retrieve the caller info.
     * 
     * @return
     */
    private final StackFrame caller() {
        return StackWalker.getInstance(Set.of(Option.RETAIN_CLASS_REFERENCE), 2).walk(s -> s.skip(2).limit(1).findFirst().get());
    }

    /**
     * Test xml equality.
     * 
     * @param actual
     * @param expected
     * @return
     */
    protected final boolean sameXML(Variable<XML> actual, String expected) {
        return sameXML(actual.exact(), expected);
    }

    /**
     * Test xml equality.
     * 
     * @param actual
     * @param expected
     * @return
     */
    protected final boolean sameXML(XML actual, String expected) {
        return sameXML(actual, I.xml(expected.replace('\'', '"')));
    }

    /**
     * Test xml equality.
     * 
     * @param actual
     * @param expected
     * @return
     */
    protected final boolean sameXML(XML actual, XML expected) {
        XML root = I.xml("<r/>").append(actual).children();

        return sameXML(root, root.to(), expected, expected.to());
    }

    /**
     * Test xml equality.
     * 
     * @param actual
     * @param expected
     * @return
     */
    private boolean sameXML(XML actualXML, Node actual, XML expectedXML, Node expected) {
        // base
        assert actual != null;
        assert expected != null;
        assert Objects.equals(actual.getLocalName(), expected.getLocalName()) : error(actualXML, expectedXML);
        assert actual.getNodeType() == expected.getNodeType() : error(actualXML, expectedXML);
        if (actual.getNodeType() == Node.TEXT_NODE) {
            assert actual.getTextContent().trim().equals(expected.getTextContent().trim()) : error(actualXML, expectedXML);
        }

        // attributes
        NamedNodeMap actualAttrs = actual.getAttributes();
        NamedNodeMap expectedAttrs = expected.getAttributes();
        if (actualAttrs != null && expectedAttrs != null) {
            assert actualAttrs.getLength() == expectedAttrs.getLength() : error(actualXML, expectedXML);

            for (int i = 0; i < actualAttrs.getLength(); i++) {
                Attr attr = (Attr) actualAttrs.item(i);
                Attr pair = (Attr) expectedAttrs.getNamedItem(attr.getName());
                assert pair != null : "Expected element has no attribute [" + attr.getName() + "] in " + expectedXML;
                assert attr.getName().equals(pair.getName()) : error(actualXML, expectedXML);
                assert attr.getValue().equals(pair.getValue()) : error(actualXML, expectedXML);
            }
            for (int i = 0; i < expectedAttrs.getLength(); i++) {
                Attr attr = (Attr) expectedAttrs.item(i);
                Attr pair = (Attr) actualAttrs.getNamedItem(attr.getName());
                assert pair != null : "Actual element has no attribute [" + attr.getName() + "] in " + actualXML;
                assert attr.getName().equals(pair.getName()) : error(actualXML, expectedXML);
                assert attr.getValue().equals(pair.getValue()) : error(actualXML, expectedXML);
            }
        }

        // children
        NodeList actualChildren = actual.getChildNodes();
        NodeList expectedChildren = expected.getChildNodes();

        for (int i = 0; i < actualChildren.getLength(); i++) {
            Node item = actualChildren.item(i);
            if (item.getNodeType() == Node.TEXT_NODE && item.getTextContent().isBlank()) {
                actual.removeChild(item);
            }
        }

        assert actualChildren.getLength() == expectedChildren.getLength() : error(actualXML, expectedXML);
        for (int i = 0; i < actualChildren.getLength(); i++) {
            assert sameXML(actualXML, actualChildren.item(i), expectedXML, expectedChildren.item(i));
        }

        // next
        Node actualNext = actual.getNextSibling();
        Node expectedNext = expected.getNextSibling();

        if (actualNext == null) {
            assert actualNext == expectedNext : error(actualXML, expectedXML);
        } else if (expectedNext != null) {
            assert sameXML(actualXML, actualNext, expectedXML, expectedNext);
        }
        return true;
    }

    /**
     * @param actualXML
     * @param expectedXML
     * @return
     */
    private String error(XML actualXML, XML expectedXML) {
        return "\r\n=============== ACTUAL ===============\r\n" + actualXML + "\r\n\r\n=============== EXPECTED ===============\r\n" + expectedXML + "\r\n";
    }

    /**
     * Find class by its name.
     * 
     * @param className
     */
    final Variable<ClassInfo> findByClassName(Letter letter, String className) {
        for (ClassInfo info : letter.types) {
            if (info.id().equals(className)) {
                return Variable.of(info);
            }
        }
        return Variable.empty();
    }

    /**
     * Provide only null.
     */
    protected static class NullProvider implements ArgumentsProvider {

        /**
         * {@inheritDoc}
         */
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
            return Stream.of(Arguments.of(new Object[] {null, null, null, null, null, null}));
        }
    }
}