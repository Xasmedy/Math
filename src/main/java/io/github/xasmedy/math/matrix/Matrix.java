package io.github.xasmedy.math.matrix;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public interface Matrix<T extends Matrix<T, N>, N> {

    int size();

    int rows();

    int columns();

    T add(T other);

    T sub(T other);

    /// Post-Multiples `this` matrix with the `other` matrix.\
    /// Results in `A := AB`.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `this * other != other * this`
    T mul(T other);

    /// @return the transposed version of this matrix.
    T transpose();

    /// @return The determinant of this matrix.
    N determinant();

    /// Inverts this matrix given that the determinant is != 0.
    /// @return This matrix for the purpose of chaining operations.
    /// @throws ArithmeticException if the matrix cannot be inverted because it is singular.
    T invert() throws ArithmeticException;

    /// @return true if the matrix is a singular matrix.
    boolean isSingular();

    N[] asArray();

    MemorySegment asMemorySegment(Arena arena);
}
