/*
 * Copyright (c) 2026 Xasmedy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.xasmedy.math;

import rife.bld.NamedFile;
import rife.bld.Project;
import rife.bld.operations.CompileOperation;
import rife.bld.operations.RunOperation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;
import static rife.bld.dependencies.Repository.RIFE2_RELEASES;
import static rife.bld.dependencies.Scope.compile;
import static rife.bld.dependencies.Scope.test;

public final class MathBuild extends Project {

    private static final String JAVA_VERSION_NAME = "java-version.txt";

    public MathBuild() throws IOException {

        final var projectPath = workDirectory().toPath();

        pkg = "io.github.xasmedy.math";
        name = "Math";
        module = "xasmedy.math";
        version = version(0,1,0);
        javaTool = Files.readString(projectPath.resolve(JAVA_VERSION_NAME)) + "/bin/java";

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES);

        scope(compile).include(module("org.jspecify", "jspecify", version(1, 0, 0)));

        final var junitVersion = version(6,0,1);
        scope(test)
                .include(module("org.junit.jupiter", "junit-jupiter", junitVersion))
                .include(module("org.junit.platform", "junit-platform-console-standalone", junitVersion))
                .include(module("org.junit.platform", "junit-platform-launcher", junitVersion));

        jarExtra();
    }

    void main(String[] args) {
        start(args);
    }

    /// Adds LICENSE and a few attributes.
    private void jarExtra() {

        final var op = jarOperation();

        // I add the LICENSE inside META-INF when creating a new jar file.
        final var license = Path.of("LICENSE").toFile();
        op.sourceFiles(new NamedFile("META-INF/LICENSE", license));

        final Map<Attributes.Name, Object> attributes = Map.of(
                new Attributes.Name("Built-By"), "Xasmedy",
                new Attributes.Name("Version"), version().toString()
        );
        op.manifestAttributes(attributes);
    }

    @Override
    public RunOperation runOperation() {
        final var operation = super.runOperation();
        // I need to provide the path of where the module resides.
        operation.modulePath().add(buildMainDirectory().getAbsolutePath());
        operation.runOptions().add("--source=26");
        return operation;
    }

    @Override
    public CompileOperation compileOperation() {
        final var operation = super.compileOperation();
        final var options = operation.compileOptions();
        options.add("--source=26");
        options.add("--target=26");
        options.add("--enable-preview");
        options.add("--add-exports=java.base/jdk.internal.value=xasmedy.math");
        options.add("--add-exports=java.base/jdk.internal.vm.annotation=xasmedy.math");
        return operation;
    }
}