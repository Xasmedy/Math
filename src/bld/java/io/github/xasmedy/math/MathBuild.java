package io.github.xasmedy.math;

import rife.bld.Project;
import rife.bld.operations.CompileOperation;
import java.util.List;
import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.*;

public final class MathBuild extends Project {

    public MathBuild() {
        pkg = "io.github.xasmedy.math";
        name = "Math";
        mainClass = "io.github.xasmedy.math.Main";
        module = "xasmedy.math";
        version = version(0,1,0);

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES);

        scope(compile).include(module("org.jspecify", "jspecify", version(1, 0, 0)));
        scope(test)
            .include(dependency("org.junit.jupiter", "junit-jupiter", version(5,11,4)))
            .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1,11,4)));
    }

    void main(String[] args) {
        start(args);
    }

    @Override
    public CompileOperation compileOperation() {
        return new CompileOperation()
                .compileOptions(List.of("--enable-preview"));
    }
}