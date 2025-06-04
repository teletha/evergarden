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
import java.util.ArrayList;
import java.util.List;

import psychopath.Locator;

public class EvergardenDemo {

    public static void main(String[] args) throws IOException {
        String repo = Locator.directory("F:/Application/Maven Repository/").isPresent() ? "F:/Application/Maven Repository/"
                : "D:/Application/Java/lib/bee/repository/";

        List<String> path = new ArrayList();
        path.add(repo + "org/junit/jupiter/junit-jupiter-api/5.13.0-M2/junit-jupiter-api-5.13.0-M2.jar");
        path.add(repo + "org/junit/jupiter/junit-jupiter-engine/5.13.0-M2/junit-jupiter-engine-5.13.0-M2.jar");
        path.add(repo + "org/junit/jupiter/junit-jupiter-params/5.13.0-M2/junit-jupiter-params-5.13.0-M2.jar");
        path.add(repo + "org/junit/platform/junit-platform-commons/1.13.0-M2/junit-platform-commons-1.13.0-M2.jar");
        path.add(repo + "org/junit/platform/junit-platform-engine/1.13.0-M2/junit-platform-engine-1.13.0-M2.jar");
        path.add(repo + "org/junit/platform/junit-platform-launcher/1.13.0-M2/junit-platform-launcher-1.13.0-M2.jar");
        path.add(repo + "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar");
        path.add(repo + "com/github/teletha/antibug/1.0.3/antibug-1.0.3.jar");
        path.add(repo + "com/github/teletha/bee-api/0.74.0/bee-api-0.74.0.jar");
        path.add(repo + "com/github/teletha/sinobu/4.12.0/sinobu-4.12.0.jar");
        path.add(repo + "com/github/teletha/lycoris/1.1.0/lycoris-1.1.0.jar");
        path.add(repo + "com/github/teletha/stylist/1.15.0/stylist-1.15.0.jar");
        path.add(repo + "com/github/teletha/icymanipulator/1.6.3/icymanipulator-1.6.3.jar");
        path.add(repo + "com/github/teletha/psychopath/2.2.1/psychopath-2.2.1.jar");
        path.add(repo + "org/commonmark/commonmark/0.24.0/commonmark-0.24.0.jar");
        path.add(repo + "org/commonmark/commonmark-ext-gfm-tables/0.24.0/commonmark-ext-gfm-tables-0.24.0.jar");
        path.add(repo + "com/github/javaparser/javaparser-core/3.26.4/javaparser-core-3.26.4.jar");
        path.add(repo + "org/checkerframework/checker-qual/3.33.0/checker-qual-3.33.0.jar");
        path.add(repo + "com/google/errorprone/error_prone_annotations/2.18.0/error_prone_annotations-2.18.0.jar");
        path.add(repo + "com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar");

        Letter letter = Violet.with.address("docs2")
                .title("Evergarden")
                .sources("src/main/java")
                .documents("src/test/java")
                .classpathBy(path)
                .host("https://github.com/teletha/evergarden")
                .useExternalJDKDoc()
                .write();

        Launcher.launch(letter.address());
    }
}