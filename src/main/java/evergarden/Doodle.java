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

import kiss.Variable;
import kiss.XML;

/**
 * Represents a unit of sample code associated with a specific class and method.
 * Includes optional documentation comment.
 *
 * @param classID the class reference
 * @param methodID the method reference
 * @param code the raw sample code text
 * @param comment the optional comment for the sample, initially empty
 */
public record Doodle(String classID, String methodID, String code, Variable<XML> comment) {

    /**
     * Constructs a {@code Doodle} instance with a comment.
     *
     * @param classID the class reference
     * @param methodID the method reference
     * @param code the raw sample code text
     */
    public Doodle(String classID, String methodID, String code) {
        this(classID, methodID, code, Variable.empty());
    }

    /**
     * Constructs a {@code Doodle} instance with a comment.
     *
     * @param classID the class reference
     * @param methodID the method reference
     * @param code the raw sample code text
     * @param comment the XML comment associated with this sample
     */
    public Doodle(String classID, String methodID, String code, XML comment) {
        this(classID, methodID, code, Variable.of(comment));
    }

    /**
     * Get the sample ID in the format of {@code classID#methodID}.
     *
     * @return the unique sample identifier
     */
    public String id() {
        return classID + "#" + methodID;
    }
}