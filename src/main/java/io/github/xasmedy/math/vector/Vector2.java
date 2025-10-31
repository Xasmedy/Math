package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import io.github.xasmedy.math.unit.Radians;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

@LooselyConsistentValue
public value record Vector2(float x, float y) implements Vector<Vector2, Point2D>, Point2D {

    @Override
    public float sum() {
        return x() + y();
    }

    @Override
    public Vector2 sum(Point2D vector) {
        return vector2(x() + vector.x(), y() + vector.y());
    }

    public Vector2 sum(float x, float y) {
        return sum(vector2(x, y));
    }

    @Override
    public Vector2 sub(Point2D vector) {
        return vector2(x() - vector.x(), y() - vector.y());
    }

    public Vector2 sub(float x, float y) {
        return sub(vector2(x, y));
    }

    @Override
    public Vector2 mul(Point2D vector) {
        return vector2(x() * vector.x(), y() * vector.y());
    }

    @Override
    public Vector2 div(Point2D vector) {
        return vector2(x() / vector.x(), y() / vector.y());
    }

    public float dot(float x, float y) {
        return dot(vector2(x, y));
    }

    public float distance(float x, float y) {
        return distance(vector2(x, y));
    }

    public float distance2(float x, float y) {
        return distance2(vector2(x, y));
    }

    public Vector2 lerp(float x, float y, float alpha) {
        return lerp(vector2(x, y), alpha);
    }

    public Vector2 interpolate(float x, float y, float alpha, Function<Float, Float> interpolator) {
        return interpolate(vector2(x, y), alpha, interpolator);
    }

    @Override
    public boolean isParallel(Point2D vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public boolean isParallel(float x, float y, float epsilon) {
        return isParallel(vector2(x, y), epsilon);
    }

    public boolean isCollinear(float x, float y, float epsilon) {
        return isCollinear(vector2(x, y), epsilon);
    }

    public boolean isCollinearOpposite(float x, float y, float epsilon) {
        return isCollinearOpposite(vector2(x, y), epsilon);
    }

    public boolean isPerpendicular(float x, float y, float epsilon) {
        return isPerpendicular(vector2(x, y), epsilon);
    }

    public boolean hasSameDirection(float x, float y) {
        return hasSameDirection(vector2(x, y));
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

    @Override
    public Vector2 this_() {
        return this;
    }

    @Override
    public Vector2 fromPoint(Point2D point) {
        return vector2(point.x(), point.y());
    }

    @Override
    public Point2D toPoint() {
        return this;
    }

    @Override
    public Vector2 with(float value) {
        return vector2(value, value);
    }

    public float cross(Point2D vector) {
        return x() * vector.y() - y() * vector.x();
    }

    public float cross(float x, float y) {
        return cross(vector2(x, y));
    }

    public Vector3 withZ(float z) {
        return vector3(x(), y(), z);
    }

    public Vector2 rotate(Radians radians) {

        final float cos = (float) Math.cos(radians.value());
        final float sin = (float) Math.sin(radians.value());

        final float newX = x() * cos - y() * sin;
        final float newY = x() * sin + y() * cos;

        return vector2(newX, newY);
    }

    public Radians angle() {
        return Radians.radians(((float) Math.atan2(y, x)));
    }
}
