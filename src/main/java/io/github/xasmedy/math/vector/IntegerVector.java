package io.github.xasmedy.math.vector;

/// @param <I> the integer parameter.
public interface IntegerVector<T extends IntegerVector<T, I>, I extends Number> extends Vector<T, I>  {

    /// Converts this integer vector to a real/floating-point vector.
    /// This conversion may be lossy if the integer cannot be represented exactly as a floating-point.
    /// If a conversion with no precision loss is needed, it is possible with pattern matching:
    /// ```java
    /// var vector = new Vector2.F64(10, 30);
    /// if (vector instanceof Vector2.F64(int x, int y)) {
    ///     var newVector = new Vector2.I32(x, y);
    /// }
    /// ```
    FloatVector<?, ?> asReal();
}
