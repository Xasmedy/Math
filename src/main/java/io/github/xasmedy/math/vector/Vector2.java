package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

/*@LooselyConsistent*/
public /*value*/ record Vector2(float x, float y) implements Vector<Vector2, Point2D>, Point2D {

    private static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    @Override
    public Vector2 add(Point2D vector) {
        return vector2(x() + vector.x(), y() + vector.y());
    }

    @Override
    public Vector2 sub(Point2D vector) {
        return vector2(x() - vector.x(), y() - vector.y());
    }

    @Override
    public Vector2 scale(Point2D vector) {
        return vector2(x() * vector.x(), y() * vector.y());
    }

    @Override
    public Vector2 scale(float scalar) {
        return scale(vector2(scalar, scalar));
    }

    @Override
    public float length() {
        return sqrt(length2());
    }

    @Override
    public float length2() {
        return x() * x() + y() * y();
    }

    @Override
    public Vector2 length(float length) {
        return length2(length * length);
    }

    @Override
    public Vector2 length2(float length2) {
        final float current = length2();
        if (current == 0 || current == length2) return this; // No changes done.
        return scale(sqrt(length2 / current));
    }

    @Override
    public Vector2 limit(float limit) {
        return limit2(limit * limit);
    }

    @Override
    public Vector2 limit2(float limit2) {
        final float length2 = length2();
        if (limit2 <= length2) return this; // No changes done.
        return scale(sqrt(limit2 / length2));
    }

    @Override
    public Vector2 clamp(float min, float max) {

        final float len2 = length2();
        if (len2 == 0f) return this;

        final float max2 = max * max;
        if (len2 > max2) return scale(sqrt(max2 / len2));

        final float min2 = min * min;
        if (len2 < min2) return scale(sqrt(min2 / len2));
        return this;
    }

    @Override
    public Vector2 normalize() {

        final float length = length();
        if (length == 0) return this;

        return vector2(x() / length, y() / length);
    }

    @Override
    public float dot(Point2D vector) {
        return x() * vector.x() + y() * vector.y();
    }

    @Override
    public float distance(Point2D vector) {
        return sqrt(distance2(vector));
    }

    @Override
    public float distance2(Point2D vector) {
        final float deltaX = vector.x() - x();
        final float deltaY = vector.y() - y();
        return deltaX * deltaX + deltaY * deltaY;
    }

    @Override
    public Vector2 lerp(Point2D target, float alpha) {
        final float invAlpha = 1.0f - alpha;
        return vector2((x() * invAlpha) + (target.x() * alpha),
                  (y() * invAlpha) + (target.y() * alpha));
    }

    @Override
    public Vector2 interpolate(Point2D target, float alpha, Function<Float, Float> interpolator) {
        return lerp(target, interpolator.apply(alpha));
    }

    @Override
    public boolean isUnit(float margin) {
        return Math.abs(length2() - 1f) < margin;
    }

    @Override
    public boolean isUnit() {
        return isUnit(0.000000001f);
    }

    @Override
    public boolean isZero() {
        return x() == 0 && y() == 0;
    }

    @Override
    public boolean isLengthZero(float margin) {
        return length2() < margin;
    }

    @Override
    public boolean isParallel(Point2D vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    @Override
    public boolean isCollinear(Point2D vector, float epsilon) {
        return isParallel(vector, epsilon) && hasSameDirection(vector);
    }

    @Override
    public boolean isCollinearOpposite(Point2D vector, float epsilon) {
        return isParallel(vector, epsilon) && hasOppositeDirection(vector);
    }

    @Override
    public boolean isPerpendicular(Point2D vector, float epsilon) {
        return Math.abs(dot(vector)) <= epsilon;
    }

    @Override
    public boolean hasSameDirection(Point2D vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Point2D vector) {
        return dot(vector) < 0;
    }

    @Override
    public boolean epsilonEquals(Point2D vector, float epsilon) {
        if (Math.abs(vector.x() - x()) > epsilon) return false;
        return !(Math.abs(vector.y() - y()) > epsilon);
    }

    public float cross(Point2D vector) {
        return x() * vector.y() - y() * vector.x();
    }
}
