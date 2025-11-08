package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.Arithmetic;
import io.github.xasmedy.math.arithmetic.Numeric;
import io.github.xasmedy.math.arithmetic.Operator;

public interface Vector<T extends Vector<T, N>, N extends Number> extends Numeric<T> {

    // TODO Make this class generic for Float, Double, Int etc, the class has a type F extends Numeric,
    //  then uses intValue(), floatValue(), doubleValue() to do generic computations,
    //  if the conversion is lossy (like BigInteger/BigDecimal),
    //  the methods should be overwritable to provide a non-lossy version.

    /// @return The dimension of this vector.
    int dimension();

    /// @return the component at the provided index.<br>
    ///  Example, for a Vector2, `0` returns `x`, `1` returns `y`, while `2` throws {@link IndexOutOfBoundsException}.
    /// @apiNote **Warning!** This method is slow since it needs to resolve the index, and
    ///  most operations can be computed by using the fast {@link Numeric},
    ///  {@link Arithmetic}, {@link Vector#operation(Vector, Operation)}, or {@link Vector#operation(Transformation)}.
    N component(int index) throws IndexOutOfBoundsException;

    /// Sums the vector components together.\
    /// In the case of a {@link Vector2}, the result would be `x + y`.
    N sum();

    /// Utility method to create a new vector with all the components set as the provided value.
    T filled(N value);

    Arithmetic<N> arithmetic();

    // TODO Docs, and convert all the code! This is crazy good for me.
    T operation(T other, Operation<N> operation);

    T operation(Transformation<N> operation);

    boolean condition(T other, Predicate<N> predicate);

    @Override
    default T add(T value) {
        return operation(value, Operator::add);
    }

    @Override
    default T sub(T value) {
        return operation(value, Operator::sub);
    }

    @Override
    default T mul(T value) {
        return operation(value, Operator::mul);
    }

    default T mul(N scalar) {
        return mul(filled(scalar));
    }

    @Override
    default T div(T value) {
        return operation(value, Operator::div);
    }

    @Override
    default boolean lt(T value) {
        return condition(value, Operator::lt);
    }

    @Override
    default boolean ltEq(T value) {
        return condition(value, Operator::ltEq);
    }

    @Override
    default boolean gt(T value) {
        return condition(value, Operator::gt);
    }

    @Override
    default boolean gtEq(T value) {
        return condition(value, Operator::gtEq);
    }

    @Override
    default T abs() {
        return operation(Arithmetic::abs);
    }

    @Override
    default T max(T value) {
        return operation(value, Arithmetic::max);
    }

    @Override
    default T min(T value) {
        return operation(value, Arithmetic::min);
    }

    default N distance2(T vector) {
        final var delta = vector.sub(value());
        return delta.mul(delta).sum();
    }

    default N length2() {
        return mul(value()).sum();
    }

    default N dot(T vector) {
        return mul(vector).sum();
    }

    default boolean isZero() {
        return eq(filled(arithmetic().zero()));
    }

    default boolean hasSameDirection(T vector) {
        final var op = arithmetic();
        // dot(this, vector) > 0
        return op.gt(dot(vector), op.zero());
    }

    default boolean hasOppositeDirection(T vector) {
        final var op = arithmetic();
        // dot(this, vector) < 0
        return op.lt(dot(vector), op.zero());
    }

    @FunctionalInterface
    interface Operation<N extends Number> {
        N calculate(Arithmetic<N> op, N current, N other);
    }

    @FunctionalInterface
    interface Transformation<N extends Number> {
        N calculate(Arithmetic<N> op, N current);
    }

    @FunctionalInterface
    interface Predicate<N extends Number> {
        boolean test(Arithmetic<N> op, N current, N other);
    }
}
