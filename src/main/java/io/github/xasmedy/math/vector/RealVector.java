package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.Arithmetic;
import java.util.function.Function;

public interface RealVector<T extends RealVector<T, F>, F extends Number> extends Vector<T, F> {

    IntegerVector<?, ?> ceilAsInt();

    IntegerVector<?, ?> floorAsInt();

    default T ceil() {
        return operation(Arithmetic::ceil);
    }

    default T floor() {
        return operation(Arithmetic::floor);
    }

    default F length() {
        return arithmetic().sqrt(length2());
    }

    default T withLength(F length) {

        final var op = arithmetic();
        final F len = length();
        // len == 0 || len == length
        if (op.eq(len, op.zero()) || op.eq(len, length)) return value(); // No changes done.

        // this * (length / len)
        return mul(op.div(length, len));
    }

    default T withLength2(F length2) {
        return withLength(arithmetic().sqrt(length2));
    }

    default T limit(F limit) {
        final F limit2 = arithmetic().mul(limit, limit);
        return limit2(limit2);
    }

    default T limit2(F limit2) {

        final var op = arithmetic();
        final F len2 = length2();
        // len2 == 0 || len2 <= limit2
        if (op.eq(len2, op.zero()) || op.ltEq(len2, limit2)) return value(); // No changes done.

        // this * sqrt(limit2 / len2)
        return mul(op.sqrt(op.div(limit2, len2)));
    }

    default T clamp(F min, F max) {

        final var op = arithmetic();

        final F len2 = length2();
        // len2 == 0
        if (op.eq(len2, op.zero())) return value();

        // max * max
        final F max2 = op.mul(max, max);
        // len2 > max2
        // this * sqrt(max2 / len2)
        if (op.gt(len2, max2)) return mul(op.sqrt(op.div(max2, len2)));

        // min * min
        final F min2 = op.mul(min, min);
        // len2 < min2
        // this * sqrt(min2 / len2)
        if (op.lt(len2, min2)) return mul(op.sqrt(op.div(min2, len2)));
        return value();
    }

    default T normalize() {
        return withLength(arithmetic().one());
    }

    default F distance(T vector) {
        return arithmetic().sqrt(distance2(vector));
    }

    default T lerp(T target, F alpha) {
        final var op = arithmetic();
        final F invAlpha = op.sub(op.one(), alpha);
        return mul(invAlpha).add(target.mul(alpha));
    }

    default T interpolate(T target, F alpha, Function<F, F> interpolator) {
        return lerp(target, interpolator.apply(alpha));
    }

    default boolean isUnit(F margin) {
        // |len2 - 1| < margin
        final var op = arithmetic();
        final F abs = op.abs(op.sub(length2(), op.one()));
        return op.lt(abs, margin);
    }

    default boolean isCollinear(T vector, F epsilon) {

        final var op = arithmetic();
        final F zero = op.zero();

        final F len = length();
        final F vLen = vector.length();
        // len == 0 || vLen == 0
        if (op.eq(len, zero) || op.eq(vLen, zero)) return false;

        // |dot(vec) / (len * vLen)|
        final F cosTheta = op.abs(op.div(dot(vector), op.mul(len, vLen)));
        // |cosTheta - 1|
        final F absDiv = op.abs(op.sub(cosTheta, op.one()));
        // absDiv <= epsilon
        return op.ltEq(absDiv, epsilon);
    }

    default boolean isPerpendicular(T vector, F epsilon) {
        final var op = arithmetic();
        // |dot(this, vector)| <= epsilon
        return op.ltEq(op.abs(dot(vector)), epsilon);
    }

    default boolean epsilonEquals(T vector, F epsilon) {
        return vector.sub(value())
                .abs()
                .ltEq(filled(epsilon));
    }

    default boolean isZero(F epsilon) {
        return epsilonEquals(filled(arithmetic().zero()), epsilon);
    }
}
