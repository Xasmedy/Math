package io.github.xasmedy.math.arithmetic;

public interface Arithmetic<T extends Number> extends Operator<T> {

    /// @return the zero representation for this numeric type.
    T zero();

    /// @return the one representation for this numeric type.
    T one();

    T sqrt(T value);

    T abs(T value);

    T max(T value1, T value2);

    T min(T value1, T value2);

    T ceil(T value);

    T floor(T value);
}
