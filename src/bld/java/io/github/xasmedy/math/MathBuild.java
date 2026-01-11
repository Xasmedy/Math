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
import rife.bld.operations.JarOperation;
import rife.bld.operations.JavadocOperation;
import rife.bld.operations.RunOperation;
import rife.bld.publish.PublishDeveloper;
import rife.bld.publish.PublishInfo;
import rife.bld.publish.PublishLicense;
import rife.bld.publish.PublishScm;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import static java.lang.String.format;
import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.compile;
import static rife.bld.dependencies.Scope.test;

public final class MathBuild extends Project {

    private static final String JAVA_VERSION_NAME = "java-version.txt";

    public MathBuild() throws IOException {

        final var projectPath = workDirectory().toPath();

        pkg = "io.github.xasmedy.math";
        name = "math";
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

        configureMavenPublishing();
        addAttributesToJar(jarOperation());
        addAttributesToJar(jarSourcesOperation());
    }

    void main(String[] args) {
        start(args);
    }

    private void configureMavenPublishing() {

        final String groupId = "io.github.xasmedy";
        final String artifactId = name();
        final String dev = "xasmedy";
        final String github = "https://github.com";
        final String devUrl = format("%s/%s", github, dev);
        final String project = format("%s/%s/%s", github, dev, artifactId);

        final var license = new PublishLicense()
                .name("The Apache License, Version 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.txt");

        final var developer = new PublishDeveloper()
                .id(dev)
                .name("Xasmedy")
                .email("xasmedy@pm.me")
                .url(devUrl);

        final var scm = new PublishScm()
                .connection(format("scm:git:%s.git", project))
                .developerConnection(format("scm:git:git@github.com:%s/%s.git", dev, artifactId))
                .url(project);

        final var publishInfo = new PublishInfo()
                .groupId(groupId)
                .artifactId(artifactId)
                .version(version())
                .name("Math")
                .description("Valhalla-based Math Library")
                .url(project)
                .developer(developer)
                .license(license)
                .scm(scm)
                .signKey(property("sign.key"))
                .signPassphrase(property("sign.passphrase"));

        publishOperation()
                .repositories(CENTRAL_RELEASES.withCredentials(
                        property("sonatype.username"),
                        property("sonatype.password")
                )).info(publishInfo);
    }

    private static String nowUTC() {
        final var format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ZonedDateTime.now(Clock.systemUTC()).format(format);
    }

    /// Adds LICENSE and a few attributes.
    private void addAttributesToJar(JarOperation op) {

        // I add the LICENSE inside META-INF when creating a new jar file.
        final var license = Path.of("LICENSE").toFile();
        op.sourceFiles(new NamedFile("META-INF/LICENSE", license));

        final Map<Attributes.Name, Object> attributes = Map.of(
                new Attributes.Name("Built-By"), "Xasmedy",
                new Attributes.Name("Built-Date"), nowUTC(),
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

    private void addCompilationOptions(ArrayList<String> options) {
        options.add("--source=26");
        options.add("--enable-preview");
        options.add("--add-exports=java.base/jdk.internal.value=xasmedy.math");
        options.add("--add-exports=java.base/jdk.internal.vm.annotation=xasmedy.math");
    }

    @Override
    public CompileOperation compileOperation() {
        final var operation = super.compileOperation();
        final var options = operation.compileOptions();
        options.add("--target=26");
        addCompilationOptions(options);
        return operation;
    }

    @Override
    public JavadocOperation javadocOperation() {
        final var operation = super.javadocOperation();
        final var options = operation.javadocOptions();
        addCompilationOptions(options);
        options.tag("apiNote", "a", "API Note:");
        options.tag("implNote", "a", "Implementation Note:");
        return operation;
    }
}