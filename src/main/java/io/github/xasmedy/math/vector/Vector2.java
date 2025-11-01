package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import io.github.xasmedy.math.unit.Radians;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

@LooselyConsistentValue
public value record Vector2(float x, float y) implements Vector<Vector2>, Point2D {

    @Override
    public Vector2 sum(Vector2 value) {
        return v2(x() + value.x(), y() + value.y());
    }

    public Vector2 sum(float x, float y) {
        return sum(v2(x, y));
    }

    @Override
    public Vector2 sub(Vector2 value) {
        return v2(x() - value.x(), y() - value.y());
    }

    public Vector2 sub(float x, float y) {
        return sub(v2(x, y));
    }

    @Override
    public Vector2 mul(Vector2 value) {
        return v2(x() * value.x(), y() * value.y());
    }

    @Override
    public Vector2 div(Vector2 value) {
        return v2(x() / value.x(), y() / value.y());
    }

    @Override
    public boolean lt(Vector2 value) {
        return x() < value.x() &&
               y() < value.y();
    }

    @Override
    public boolean ltEq(Vector2 value) {
        return x() <= value.x() &&
               y() <= value.y();
    }

    @Override
    public boolean gt(Vector2 value) {
        return x() > value.x() &&
               y() > value.y();
    }

    @Override
    public boolean gtEq(Vector2 value) {
        return x() >= value.x() &&
               y() >= value.y();
    }

    @Override
    public float sum() {
        return x() + y();
    }

    @Override
    public Vector2 abs() {
        return v2(Math.abs(x()), Math.abs(y()));
    }

    @Override
    public Vector2 operation(Function<Float, Float> operation) {
        final float x = operation.apply(x());
        final float y = operation.apply(y());
        return v2(x, y);
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public Vector2 with(float value) {
        return v2(value, value);
    }

    @Override
    public Vector2 this_() {
        return this;
    }

    public float dot(float x, float y) {
        return dot(v2(x, y));
    }

    public float distance(float x, float y) {
        return distance(v2(x, y));
    }

    public float distance2(float x, float y) {
        return distance2(v2(x, y));
    }

    public Vector2 lerp(float x, float y, float alpha) {
        return lerp(v2(x, y), alpha);
    }

    public Vector2 interpolate(float x, float y, float alpha, Function<Float, Float> interpolator) {
        return interpolate(v2(x, y), alpha, interpolator);
    }

    @Override
    public boolean isParallel(Vector2 vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public boolean isParallel(float x, float y, float epsilon) {
        return isParallel(v2(x, y), epsilon);
    }

    public boolean isCollinear(float x, float y, float epsilon) {
        return isCollinear(v2(x, y), epsilon);
    }

    public boolean isCollinearOpposite(float x, float y, float epsilon) {
        return isCollinearOpposite(v2(x, y), epsilon);
    }

    public boolean isPerpendicular(float x, float y, float epsilon) {
        return isPerpendicular(v2(x, y), epsilon);
    }

    public boolean hasSameDirection(float x, float y) {
        return hasSameDirection(v2(x, y));
    }

    public boolean hasOppositeDirection(float x, float y) {
        return hasOppositeDirection(v2(x, y));
    }

    public boolean epsilonEquals(float x, float y, float epsilon) {
        return epsilonEquals(v2(x, y), epsilon);
    }

    public float cross(Vector2 vector) {
        return x() * vector.y() - y() * vector.x();
    }

    public float cross(float x, float y) {
        return cross(v2(x, y));
    }

    public Vector3 withZ(float z) {
        return v3(x(), y(), z);
    }

    public Vector2 rotate(Radians radians) {

        final float cos = (float) Math.cos(radians.value());
        final float sin = (float) Math.sin(radians.value());

        final float newX = x() * cos - y() * sin;
        final float newY = x() * sin + y() * cos;

        return v2(newX, newY);
    }

    public Radians angle() {
        return Radians.radians(((float) Math.atan2(y, x)));
    }
}
