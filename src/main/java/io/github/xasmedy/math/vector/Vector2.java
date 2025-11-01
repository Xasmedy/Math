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

    @Override
    public Vector2 sub(Vector2 value) {
        return v2(x() - value.x(), y() - value.y());
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

    @Override
    public boolean isParallel(Vector2 vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public float cross(Vector2 vector) {
        return x() * vector.y() - y() * vector.x();
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
