package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2;
import io.github.xasmedy.math.unit.Radians;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vectors.*;

@LooselyConsistentValue
public value record Vector2F32(@NullRestricted Float x, @NullRestricted Float y) implements VectorF32<Vector2F32>, Point2<Float> {

    @Override
    public Vector2F32 add(Vector2F32 value) {
        return v2(x() + value.x(), y() + value.y());
    }

    @Override
    public Vector2F32 sub(Vector2F32 value) {
        return v2(x() - value.x(), y() - value.y());
    }

    @Override
    public Vector2F32 mul(Vector2F32 value) {
        return v2(x() * value.x(), y() * value.y());
    }

    @Override
    public Vector2F32 div(Vector2F32 value) {
        return v2(x() / value.x(), y() / value.y());
    }

    @Override
    public boolean lt(Vector2F32 value) {
        return x() < value.x() &&
               y() < value.y();
    }

    @Override
    public boolean ltEq(Vector2F32 value) {
        return x() <= value.x() &&
               y() <= value.y();
    }

    @Override
    public boolean gt(Vector2F32 value) {
        return x() > value.x() &&
               y() > value.y();
    }

    @Override
    public boolean gtEq(Vector2F32 value) {
        return x() >= value.x() &&
               y() >= value.y();
    }

    @Override
    public float sum() {
        return x() + y();
    }

    @Override
    public Vector2F32 abs() {
        return v2(Math.abs(x()), Math.abs(y()));
    }

    @Override
    public Vector2F32 operation(Function<Float, Float> operation) {
        final float x = operation.apply(x());
        final float y = operation.apply(y());
        return v2(x, y);
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public Vector2F32 with(float value) {
        return v2(value, value);
    }

    @Override
    public Vector2F32 this_() {
        return this;
    }

    @Override
    public boolean isCollinear(Vector2F32 vector, float epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public float cross(Vector2F32 vector) {
        return x() * vector.y() - y() * vector.x();
    }

    public Vector2F32 rotate(Radians radians) {

        final float cos = (float) Math.cos(radians.value());
        final float sin = (float) Math.sin(radians.value());

        final float newX = x() * cos - y() * sin;
        final float newY = x() * sin + y() * cos;

        return v2(newX, newY);
    }

    public Radians angle() {
        return Radians.radians(((float) Math.atan2(y, x)));
    }

    public Vector1F32 withoutY() {
        return v1(x());
    }

    public Vector3 withZ(float z) {
        return v3(x(), y(), z);
    }
}
