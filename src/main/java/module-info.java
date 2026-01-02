import org.jspecify.annotations.NullMarked;

@NullMarked // Makes the whole codebase non-null by default.
module xasmedy.math {
    requires org.jspecify;
    requires jdk.incubator.vector;
    requires java.desktop;
    exports io.github.xasmedy.math.rotation;
    exports io.github.xasmedy.math.matrix;

    exports io.github.xasmedy.math.point;
    exports io.github.xasmedy.math.point.p1;
    exports io.github.xasmedy.math.point.p2;
    exports io.github.xasmedy.math.point.p3;
    exports io.github.xasmedy.math.point.p4;

    exports io.github.xasmedy.math.shape;
    exports io.github.xasmedy.math.unit;

    exports io.github.xasmedy.math.vector;
    exports io.github.xasmedy.math.vector.v1;
    exports io.github.xasmedy.math.vector.v2;
    exports io.github.xasmedy.math.vector.v3;
    exports io.github.xasmedy.math.vector.v4;
}