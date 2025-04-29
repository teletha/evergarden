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

public class DollDemo {

    public static void main(String[] args) throws IOException {
        String repo = Locator.directory("F:/Application/Maven Repository/").isPresent() ? "F:/Application/Maven Repository/"
                : "D:/Application/Java/lib/bee/repository/";

        List<String> path = new ArrayList();
        path.add(repo + "com/fasterxml/jackson/core/jackson-core/2.12.2/jackson-core-2.12.2.jar");
        path.add(repo + "com/fasterxml/jackson/core/jackson-databind/2.12.2/jackson-databind-2.12.2.jar");
        path.add(repo + "com/alibaba/fastjson/2.0.15.graal/fastjson-2.0.15.graal.jar");
        path.add(repo + "com/google/code/gson/gson/2.9.1/gson-2.9.1.jar");
        path.add(repo + "com/pgs-soft/HttpClientMock/1.0.0/HttpClientMock-1.0.0.jar");
        path.add(repo + "net/bytebuddy/byte-buddy/1.12.18/byte-buddy-1.12.18.jar");
        path.add(repo + "org/junit/jupiter/junit-jupiter-api/5.9.0-M1/junit-jupiter-api-5.9.0-M1.jar");
        path.add(repo + "org/junit/platform/junit-platform-commons/1.9.0-M1/junit-platform-commons-1.9.0-M1.jar");
        path.add(repo + "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar");
        path.add(repo + "com/github/teletha/antibug/1.0.3/antibug-1.0.3.jar");

        AutoMemoriesDoll doll = AutoMemoriesDoll.with.sources("../sinobu/src/main/java")
                .output("docs")
                .title("Sinobu")
                .description("Sinobu is not obsolete framework but utility, which can manipulate objects as a extremely-condensed facade.")
                .samples("../sinobu/src/test/java")
                .classpathBy(path)
                .host("https://github.com/teletha/sinobu")
                .useExternalJDKDoc()
                .write();

        Launcher.launch(doll.output);
    }
}