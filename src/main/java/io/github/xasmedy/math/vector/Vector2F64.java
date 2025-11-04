package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2;
import io.github.xasmedy.math.unit.Radians;
import jdk.internal.vm.annotation.NullRestricted;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vectors.*;

public value record Vector2F64(@NullRestricted Double x, @NullRestricted Double y) implements VectorF64<Vector2F64>, Point2<Double> {

    @Override
    public Vector2F64 add(Vector2F64 value) {
        return v2(x() + value.x(), y() + value.y());
    }

    @Override
    public Vector2F64 sub(Vector2F64 value) {
        return v2(x() - value.x(), y() - value.y());
    }

    @Override
    public Vector2F64 mul(Vector2F64 value) {
        return v2(x() * value.x(), y() * value.y());
    }

    @Override
    public Vector2F64 div(Vector2F64 value) {
        return v2(x() / value.x(), y() / value.y());
    }

    @Override
    public boolean lt(Vector2F64 value) {
        return x() < value.x() &&
                y() < value.y();
    }

    @Override
    public boolean ltEq(Vector2F64 value) {
        return x() <= value.x() &&
                y() <= value.y();
    }

    @Override
    public boolean gt(Vector2F64 value) {
        return x() > value.x() &&
                y() > value.y();
    }

    @Override
    public boolean gtEq(Vector2F64 value) {
        return x() >= value.x() &&
                y() >= value.y();
    }

    @Override
    public double sum() {
        return x() + y();
    }

    @Override
    public Vector2F64 abs() {
        return v2(Math.abs(x()), Math.abs(y()));
    }

    @Override
    public Vector2F64 operation(Function<Double, Double> operation) {
        final double x = operation.apply(x());
        final double y = operation.apply(y());
        return v2(x, y);
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public Vector2F64 with(double value) {
        return v2(value, value);
    }

    @Override
    public Vector2F64 this_() {
        return this;
    }

    @Override
    public boolean isCollinear(Vector2F64 vector, double epsilon) {
        return Math.abs(cross(vector)) <= epsilon;
    }

    public double cross(Vector2F64 vector) {
        return x() * vector.y() - y() * vector.x();
    }

    public Vector2F64 rotate(Radians radians) {

        final double cos = (double) Math.cos(radians.value());
        final double sin = (double) Math.sin(radians.value());

        final double newX = x() * cos - y() * sin;
        final double newY = x() * sin + y() * cos;

        return v2(newX, newY);
    }

    public Radians angle() {
        return Radians.radians((float) Math.atan2(y, x));
    }

    public Vector1F64 withoutY() {
        return v1(x());
    }

    public Vector3 withZ(double z) {
        return v3(x(), y(), z);
    }
}
