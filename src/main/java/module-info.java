import org.jspecify.annotations.NullMarked;

@NullMarked
module xasmedy.math {
    requires org.jspecify;
    exports io.github.xasmedy.math.vector;
    exports io.github.xasmedy.math.point;
    exports io.github.xasmedy.math.shape;
}