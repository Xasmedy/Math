package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.Operators;
import io.github.xasmedy.math.point.Point1D;
import io.github.xasmedy.math.point.Point2D;
import io.github.xasmedy.math.point.Point3D;
import java.util.function.Function;
import static io.github.xasmedy.math.util.MathUtil.sqrt;

/// @implSpec T must extends P.
public interface Vector<T extends Vector<T>> extends Operators<T> {

    static Vector1 v1(float x) {
        return new Vector1(x);
    }

    static Vector1 v1(Point1D point) {
        return v1(point.x());
    }

    static Vector2 v2(float x, float y) {
        return new Vector2(x, y);
    }

    static Vector2 v2(Point2D point) {
        return v2(point.x(), point.y());
    }

    static Vector3 v3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    static Vector3 v3(Point3D point) {
        return v3(point.x(), point.y(), point.z());
    }

    /// Sums the vector components together.\
    /// In the case of a {@link Vector2}, the result would be `x + y`.
    float sum();

    /// @return the absolute value of all the vector components.
    /// @see Math#abs(float)
    T abs();

    /// Generic operation that uses a pure function to mutate all the components of the vector.\
    /// For example, we can implement an abs operation thanks to the function `comp -> Math.abs(comp)`.
    /// @apiNote There's no guarantee on the order of components passed to the function.
    T operation(Function<Float, Float> operation);

    /// @return The dimension of this vector.
    int dimension();

    /// Utility method to create a new vector with all the components set as the provided value.
    T with(float value);

    /// Utility method to convert {@link Vector} to {@link T}.\
    /// This allows the Vector interface to generalize some code.
    T this_();

    boolean isParallel(T vector, float epsilon);

    default T mul(float scalar) {
        return mul(with(scalar));
    }

    default float length() {
        return sqrt(length2());
    }

    default float length2() {
        return mul(this_()).sum();
    }

    default T withLength(float length) {
        return withLength2(length * length);
    }

    default T withLength2(float length2) {
        final float len2 = length2();
        if (len2 == 0 || len2 == length2) return this_(); // No changes done.
        return mul(sqrt(length2 / len2));
    }

    default T limit(float limit) {
        return limit2(limit * limit);
    }

    default T limit2(float limit2) {
        final float len2 = length2();
        if (limit2 <= len2) return this_(); // No changes done.
        return mul(sqrt(limit2 / len2));
    }

    default T clamp(float min, float max) {

        final float len2 = length2();
        if (len2 == 0) return this_();

        final float max2 = max * max;
        if (len2 > max2) return mul(sqrt(max2 / len2));

        final float min2 = min * min;
        if (len2 < min2) return mul(sqrt(min2 / len2));
        return this_();
    }

    default T normalize() {
        final float len2 = length2();
        if (len2 == 0 || len2 == 1) return this_();
        return mul(1 / sqrt(len2));
    }

    default float dot(T vector) {
        return mul(vector).sum();
    }

    default float distance(T vector) {
        return sqrt(distance2(vector));
    }

    default float distance2(T vector) {
        final var delta = vector.sub(this_());
        return delta.mul(delta).sum();
    }

    default T lerp(T target, float alpha) {
        final float invAlpha = 1.0f - alpha;
        return mul(invAlpha).sum(mul(alpha));
    }

    default T interpolate(T target, float alpha, Function<Float, Float> interpolator) {
        return lerp(target, interpolator.apply(alpha));
    }

    default boolean isUnit(float margin) {
        return Math.abs(length2() - 1f) < margin;
    }

    default boolean isUnit() {
        return isUnit(0.000000001f);
    }

    default boolean isZero() {
        return equals(with(0));
    }

    default boolean isLengthZero(float margin) {
        return length2() < margin;
    }

    default boolean isCollinear(T vector, float epsilon) {
        return isParallel(vector, epsilon) && hasSameDirection(vector);
    }

    default boolean isCollinearOpposite(T vector, float epsilon) {
        return isParallel(vector, epsilon) && hasOppositeDirection(vector);
    }

    default boolean isPerpendicular(T vector, float epsilon) {
        return Math.abs(dot(vector)) <= epsilon;
    }

    default boolean hasSameDirection(T vector) {
        return dot(vector) > 0;
    }

    default boolean hasOppositeDirection(T vector) {
        return dot(vector) < 0;
    }

    default boolean epsilonEquals(T vector, float epsilon) {
        return vector.sub(this_())
                .abs()
                .ltEq(with(epsilon));
    }
}
