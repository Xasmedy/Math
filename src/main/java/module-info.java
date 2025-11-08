import org.jspecify.annotations.NullMarked;

@NullMarked // Makes the whole codebase non-null by default.
module xasmedy.math {
    requires org.jspecify;
    exports io.github.xasmedy.math.vector;
    exports io.github.xasmedy.math.point;
    exports io.github.xasmedy.math.shape;
    exports io.github.xasmedy.math.unit;
    exports io.github.xasmedy.math.arithmetic;
}