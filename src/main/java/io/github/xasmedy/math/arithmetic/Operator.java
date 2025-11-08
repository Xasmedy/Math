package io.github.xasmedy.math.arithmetic;

public interface Operator<T> {

    T add(T value1, T value2);

    T sub(T value1, T value2);

    T mul(T value1, T value2);

    T div(T value1, T value2);

    boolean lt(T value1, T value2);

    boolean ltEq(T value1, T value2);

    boolean gt(T value1, T value2);

    boolean gtEq(T value1, T value2);

    default boolean eq(T value1, T value2) {
        return value1.equals(value2);
    }
}
