package io.github.xasmedy.math.matrix;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

/// Generic Matrix interface having common methods.
/// @param <M> is the matrix implementation.
/// @param <N> is the numerical type used for the matrix. (e.g., {@link Float}, {@link Double})
public interface Matrix<M extends Matrix<M, N>, N> {

    /// @return [Matrix#rows()] multiplied by [Matrix#columns()].
    int size();

    int rows();

    int columns();

    /// @return the total number of bytes required to store this matrix.
    /// @implNote The size is calculated as `size(`{@link N}`) * `{@link #size()}.
    long byteSize();

    /// @return this matrix with each element added by the other matrix.
    M add(M other);

    /// @return this matrix with each element subtracted by the other matrix.
    M sub(M other);

    /// Scalar Matrix Multiplication.
    /// @return this matrix with each element multiplied by the scalar.
    M mul(N scalar);

    /// Post-Multiples `this` matrix with the `other` matrix.\
    /// Results in `A := AB`.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `this * other != other * this`
    M mul(M other);

    /// Pre-Multiples the `other` matrix with `this` matrix.\
    /// Results in `A := BA`.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `other * this != this * other`
    M preMul(M other);

    /// @return the transposed version of this matrix.
    M transpose();

    /// @return The determinant of this matrix.
    N determinant();

    /// Inverts this matrix given that the determinant is != 0.
    /// @return This matrix for the purpose of chaining operations.
    /// @throws ArithmeticException if the matrix cannot be inverted because it is singular.
    M invert() throws ArithmeticException;

    /// @return true if the matrix is a singular matrix.
    boolean isSingular();

    /// @return a matrix with the translational part removed (set to 0) and transposed.
    M toNormalMatrix();

    /// Copies this matrix into the given {@link MemorySegment} starting at the specified logical index.\
    /// The memory segment must be able to hold *at least* `(index + 1) * `{@link #byteSize()}.
    /// @param segment the output memory segment.
    /// @param index the logical index in units of {@link #byteSize()} where copying begins.
    /// @apiNote The copying is issued using the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    void toMemorySegment(MemorySegment segment, long index);

    /// Creates a representation of this matrix as a new {@link MemorySegment} allocated from the provided {@link Arena}.
    /// @apiNote The copying is issued using the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    MemorySegment asMemorySegment(Arena arena);
}
