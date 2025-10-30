package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

@LooselyConsistentValue
public record Vector2(float x, float y) implements Vector<Vector2, Point2D>, Point2D {

    private static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    @Override
    public Vector2 add(Point2D vector) {
        return vector2(x() + vector.x(), y() + vector.y());
    }

    public Vector2 add(float x, float y) {
        return add(vector2(x, y));
    }

    @Override
    public Vector2 sub(Point2D vector) {
        return vector2(x() - vector.x(), y() - vector.y());
    }

    public Vector2 sub(float x, float y) {
        return sub(vector2(x, y));
    }

    @Override
    public Vector2 scale(Point2D vector) {
        return vector2(x() * vector.x(), y() * vector.y());
    }

    @Override
    public Vector2 scale(float scalar) {
        return scale(vector2(scalar, scalar));
    }

    public Vector2 scale(float scaleX, float scaleY) {
        return scale(vector2(scaleX, scaleY));
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
    public Vector2 withLength(float length) {
        return withLength2(length * length);
    }

    @Override
    public Vector2 withLength2(float length2) {
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

    public float dot(float x, float y) {
        return dot(vector2(x, y));
    }

    @Override
    public float distance(Point2D vector) {
        return sqrt(distance2(vector));
    }

    public float distance(float x, float y) {
        return distance(vector2(x, y));
    }

    @Override
    public float distance2(Point2D vector) {
        final float deltaX = vector.x() - x();
        final float deltaY = vector.y() - y();
        return deltaX * deltaX + deltaY * deltaY;
    }

    public float distance2(float x, float y) {
        return distance2(vector2(x, y));
    }

    @Override
    public Vector2 lerp(Point2D target, float alpha) {
        final float invAlpha = 1.0f - alpha;
        return vector2((x() * invAlpha) + (target.x() * alpha),
                  (y() * invAlpha) + (target.y() * alpha));
    }

    public Vector2 lerp(float x, float y, float alpha) {
        return lerp(vector2(x, y), alpha);
    }

    @Override
    public Vector2 interpolate(Point2D target, float alpha, Function<Float, Float> interpolator) {
        return lerp(target, interpolator.apply(alpha));
    }

    public Vector2 interpolate(float x, float y, float alpha, Function<Float, Float> interpolator) {
        return interpolate(vector2(x, y), alpha, interpolator);
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
    public boolean isLength2Zero(float margin) {
        return length2() < margin;
    }

    @Override
    public boolean isParallel(Point2D vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public boolean isParallel(float x, float y, float epsilon) {
        return isParallel(vector2(x, y), epsilon);
    }

    @Override
    public boolean isCollinear(Point2D vector, float epsilon) {
        return isParallel(vector, epsilon) && hasSameDirection(vector);
    }

    public boolean isCollinear(float x, float y, float epsilon) {
        return isCollinear(vector2(x, y), epsilon);
    }

    @Override
    public boolean isCollinearOpposite(Point2D vector, float epsilon) {
        return isParallel(vector, epsilon) && hasOppositeDirection(vector);
    }

    public boolean isCollinearOpposite(float x, float y, float epsilon) {
        return isCollinearOpposite(vector2(x, y), epsilon);
    }

    @Override
    public boolean isPerpendicular(Point2D vector, float epsilon) {
        return Math.abs(dot(vector)) <= epsilon;
    }

    public boolean isPerpendicular(float x, float y, float epsilon) {
        return isPerpendicular(vector2(x, y), epsilon);
    }

    @Override
    public boolean hasSameDirection(Point2D vector) {
        return dot(vector) > 0;
    }

    public boolean hasSameDirection(float x, float y) {
        return hasSameDirection(vector2(x, y));
    }

    @Override
    public boolean hasOppositeDirection(Point2D vector) {
        return dot(vector) < 0;
    }

    public boolean hasOppositeDirection(float x, float y) {
        return hasOppositeDirection(vector2(x, y));
    }

    @Override
    public boolean epsilonEquals(Point2D vector, float epsilon) {
        if (Math.abs(vector.x() - x()) > epsilon) return false;
        return !(Math.abs(vector.y() - y()) > epsilon);
    }

    public boolean epsilonEquals(float x, float y, float epsilon) {
        return epsilonEquals(vector2(x, y), epsilon);
    }

    public float cross(Point2D vector) {
        return x() * vector.y() - y() * vector.x();
    }

    public float cross(float x, float y) {
        return cross(vector2(x, y));
    }
}
