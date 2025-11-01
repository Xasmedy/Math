package io.github.xasmedy.math;

/// Defines a generic interface for Java numeric operators.\
/// This will likely be replaced by Java operator overloading once it comes out.
public interface Operators<T> {

    T sum(T value);

    T sub(T value);

    T mul(T value);

    T div(T value);

    boolean lt(T value);

    boolean ltEq(T value);

    boolean gt(T value);

    boolean gtEq(T value);

    default boolean eq(T value) {
        return equals(value);
    }
}
