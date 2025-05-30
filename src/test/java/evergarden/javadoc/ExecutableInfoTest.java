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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import evergarden.EvergardenTestSupport;
import evergarden.javadoc.ExecutableInfo;

public class ExecutableInfoTest extends EvergardenTestSupport {

    @Test
    public void parameter0() {
        ExecutableInfo info = currentMethod();
        assert info.name.equals("parameter0");
        assert info.numberOfParameters() == 0;
    }

    @ParameterizedTest
    @ArgumentsSource(NullProvider.class)
    public void parameter1(String value) {
        assert checkParamName(currentMethod(), "value");
    }

    @ParameterizedTest
    @ArgumentsSource(NullProvider.class)
    public void parameter2(String value, String text) {
        assert checkParamName(currentMethod(), "value", "text");
    }

    @ParameterizedTest
    @ArgumentsSource(NullProvider.class)
    public void parameter3(String value, String text, Object context) {
        assert checkParamName(currentMethod(), "value", "text", "context");
    }

    /**
     * Shortcut method.
     * 
     * @param info
     * @param expected
     * @return
     */
    private boolean checkParamName(ExecutableInfo info, String... expected) {
        for (int i = 0; i < expected.length; i++) {
            assert info.createParameterName(i).text().equals(expected[i]);
        }
        return true;
    }
}