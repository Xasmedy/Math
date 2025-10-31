package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.Operators;
import io.github.xasmedy.math.point.Point2D;
import io.github.xasmedy.math.point.Point3D;
import java.util.function.Function;
import static io.github.xasmedy.math.util.MathUtil.sqrt;

/// @implSpec T must extends P.
public interface Vector<T extends Vector<T, P>, P> extends Operators<P, T> {

    static Vector2 vector2(float x, float y) {
        return new Vector2(x, y);
    }

    static Vector2 vector2(Point2D point) {
        return vector2(point.x(), point.y());
    }

    static Vector3 vector3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    static Vector3 vector3(Point3D point) {
        return vector3(point.x(), point.y(), point.z());
    }

    default T mul(float scalar) {
        final var point = with(scalar).toPoint();
        return mul(point);
    }

    default float length() {
        return sqrt(length2());
    }

    default float length2() {
        return mul(toPoint()).sum();
    }

    default T withLength(float length) {
        return withLength2(length * length);
    }

    default T withLength2(float length2) {
        final float current = length2();
        if (current == 0 || current == length2) return this_(); // No changes done.
        return mul(sqrt(length2 / current));
    }

    default T limit(float limit) {
        return limit2(limit * limit);
    }

    default T limit2(float limit2) {
        final float current = length2();
        if (limit2 <= current) return this_(); // No changes done.
        return mul(sqrt(limit2 / current));
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
        final float current = length2();
        if (current == 0 || current == 1) return this_();
        return mul(1 / sqrt(current));
    }

    default float dot(P vector) {
        return mul(vector).sum();
    }

    default float distance(P vector) {
        return sqrt(distance2(vector));
    }

    default float distance2(P vector) {

        // I declare different views for the vectors.
        final var vec = fromPoint(vector);

        final var delta = vec.sub(toPoint());
        return delta.mul(delta.toPoint()).sum();
    }

    default T lerp(P target, float alpha) {
        final float invAlpha = 1.0f - alpha;
        return mul(invAlpha).sum(mul(alpha).toPoint());
    }

    default T interpolate(P target, float alpha, Function<Float, Float> interpolator) {
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

    boolean isParallel(P vector, float epsilon);

    default boolean isCollinear(P vector, float epsilon) {
        return isParallel(vector, epsilon) && hasSameDirection(vector);
    }

    default boolean isCollinearOpposite(P vector, float epsilon) {
        return isParallel(vector, epsilon) && hasOppositeDirection(vector);
    }

    default boolean isPerpendicular(P vector, float epsilon) {
        return Math.abs(dot(vector)) <= epsilon;
    }

    default boolean hasSameDirection(P vector) {
        return dot(vector) > 0;
    }

    default boolean hasOppositeDirection(P vector) {
        return dot(vector) < 0;
    }

    boolean epsilonEquals(P vector, float epsilon);

    float sum();

    /// Utility method to create a new vector with the components set as the provided value.
    T with(float value);

    /// Utility method to convert {@link Vector} to {@link T}.\
    /// This allows the Vector interface to generalize some code.
    T this_();

    /// Utility method to convert {@link T} to {@link P}.
    P toPoint();

    T fromPoint(P point);
}
