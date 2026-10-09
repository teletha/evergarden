/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */

import javax.lang.model.SourceVersion;

public class Project extends bee.api.Project {

    {
        product("io.github.teletha", "evergarden", ref("version.txt"));
        require(SourceVersion.latest(), SourceVersion.RELEASE_21);

        require("io.github.teletha", "sinobu");
        require("io.github.teletha", "stylist");
        require("io.github.teletha", "psychopath");
        require("io.github.teletha", "lycoris");
        require("com.github.teletha", "icymanipulator").atAnnotation();
        require("io.github.teletha", "antibug").atTest();
        require("io.github.teletha", "viewtify").atTest();
        require("com.github.javaparser", "javaparser-core");
        require("org.commonmark", "commonmark");
        require("org.commonmark", "commonmark-ext-gfm-tables");

        versionControlSystem("https://github.com/teletha/evergarden");

        describe("""
                Evergarden is both a Doclet that generates Javadoc written in modern HTML and CSS/JS, and an SSG that automatically generates software project sites.
                """);
    }
}