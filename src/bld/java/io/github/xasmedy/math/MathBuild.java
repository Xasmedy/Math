package io.github.xasmedy.math;

import rife.bld.Project;
import rife.bld.operations.CompileOperation;
import rife.bld.operations.RunOperation;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.*;

public final class MathBuild extends Project {

    private static final String JAVA_VERSION_NAME = "java-version.txt";

    public MathBuild() throws IOException {

        final var projectPath = workDirectory().toPath();

        pkg = "io.github.xasmedy.math";
        name = "Math";
        mainClass = "io.github.xasmedy.math.Main";
        module = "xasmedy.math";
        version = version(0,1,0);
        javaTool = Files.readString(projectPath.resolve(JAVA_VERSION_NAME));

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES);

        scope(compile).include(module("org.jspecify", "jspecify", version(1, 0, 0)));
        scope(test)
            .include(dependency("org.junit.jupiter", "junit-jupiter", version(5,11,4)))
            .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1,11,4)))
            .include(dependency("org.openjdk.jmh", "jmh-core", version(1, 37)));
        scope(provided).include(dependency("org.openjdk.jmh", "jmh-generator-annprocess", version(1, 37)));
    }

    void main(String[] args) {
        start(args);
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