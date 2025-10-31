package io.github.xasmedy.math;

/// Defines a generic interface for math operators.\
/// This will likely be replaced by Java operator overloading once it comes out.
public interface Operators<T> {

    T sum(T input);

    T sub(T input);

    T mul(T input);

    T div(T input);
}
