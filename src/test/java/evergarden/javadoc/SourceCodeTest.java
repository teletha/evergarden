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

import java.util.List;

import org.junit.jupiter.api.Test;

import evergarden.javadoc.SourceCode;
import kiss.I;

class SourceCodeTest {

    @Test
    void computeIndentSizeNoIndent() {
        assert SourceCode.computeIndentSize(code("""
                test
                """)) == 0;

        assert SourceCode.computeIndentSize(code("""
                {
                    test
                }
                """)) == 0;
    }

    @Test
    void computeIndentSizeWithIndent() {
        assert SourceCode.computeIndentSize(code("""
                  test
                """)) == 2;

        assert SourceCode.computeIndentSize(code("""
                  {
                    test
                  }
                """)) == 2;
    }

    @Test
    void computeIndentSizeWithEmptyLine() {
        assert SourceCode.computeIndentSize(code("""
                  {
                    test

                    test
                  }
                """)) == 2;
    }

    @Test
    void computeIndentEmpty() {
        assert SourceCode.computeIndentSize(code("")) == 0;
    }

    /**
     * @param code
     * @return
     */
    private List<String> code(String code) {
        return I.list(code.split("\n"));
    }
}