package io.github.xasmedy.math.arithmetic;

/// Defines a generic interface for Java numeric operators.\
/// This will likely be replaced by Java operator overloading once it comes out.
public interface Numeric<T> {

    /// Returns the value of this numeric is holding.
    T value();

    T add(T value);

    T sub(T value);

    T mul(T value);

    T div(T value);

    T abs();

    T max(T value);

    T min(T value);

    boolean lt(T value);

    boolean ltEq(T value);

    boolean gt(T value);

    boolean gtEq(T value);

    default boolean eq(T value) {
        return equals(value);
    }
}
