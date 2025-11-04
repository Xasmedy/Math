package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.Operators;

/// @implSpec T must extends P.
interface BaseVector<T extends BaseVector<T>> extends Operators<T> {

    /// @return the absolute value of all the vector components.
    /// @see Math#abs(float)
    T abs();

    /// Utility method to convert {@link BaseVector} to {@link T}.\
    /// This allows the Vector interface to generalize some code.
    T this_();

    /// @return The dimension of this vector.
    int dimension();
}
