package io.github.xasmedy.math.matrix;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public interface Matrix<T extends Matrix<T, N>, N> {

    int size();

    int rows();

    int columns();

    T add(T other);

    T sub(T other);

    T mul(N scalar);

    /// Post-Multiples `this` matrix with the `other` matrix.\
    /// Results in `A := AB`.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `this * other != other * this`
    T mul(T other);

    /// Pre-Multiples the `other` matrix with `this` matrix.\
    /// Results in `A := BA`.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `other * this != this * other`
    T preMul(T other);

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

    /// @return a matrix with the translational part removed (set to 0) and transposed.
    T toNormalMatrix();

    /// @return The array presentation of this matrix.
    /// @apiNote The returned array is stored having the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    /// @implNote Arrays are identity objects, meaning they are heavy for the garbage collector.
    N[] asArray();

    /// Copies this matrix onto a new {@link MemorySegment} allocated from the provided {@link Arena}.
    /// @apiNote The matrix memory segment is stored using the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    MemorySegment asMemorySegment(Arena arena);
}
