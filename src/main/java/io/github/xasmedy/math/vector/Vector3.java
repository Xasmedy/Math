package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point3D;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector3(float x, float y, float z) implements Vector<Vector3>, Point3D {

    @Override
    public Vector3 sum(Vector3 value) {
        return v3(x() + value.x(), y() + value.y(), z() + value.z());
    }

    public Vector3 sum(float x, float y, float z) {
        return sum(v3(x, y, z));
    }

    @Override
    public Vector3 sub(Vector3 value) {
        return v3(x() - value.x(), y() - value.y(), z() - value.z());
    }

    public Vector3 sub(float x, float y, float z) {
        return sub(v3(x, y, z));
    }

    @Override
    public Vector3 mul(Vector3 value) {
        return v3(x() * value.x(), y() * value.y(), z() * value.z());
    }

    @Override
    public Vector3 div(Vector3 value) {
        return v3(x() / value.x(), y() / value.y(), z() / value.z());
    }

    @Override
    public boolean lt(Vector3 value) {
        return x() < value.x() &&
               y() < value.y() &&
               z() < value.z();
    }

    @Override
    public boolean ltEq(Vector3 value) {
        return x() <= value.x() &&
               y() <= value.y() &&
               z() <= value.z();
    }

    @Override
    public boolean gt(Vector3 value) {
        return x() > value.x() &&
               y() > value.y() &&
               z() > value.z();
    }

    @Override
    public boolean gtEq(Vector3 value) {
        return x() >= value.x() &&
               y() >= value.y() &&
               z() >= value.z();
    }

    @Override
    public float sum() {
        return x() + y() + z();
    }

    @Override
    public Vector3 abs() {
        return v3(Math.abs(x()), Math.abs(y()), Math.abs(z()));
    }

    @Override
    public Vector3 operation(Function<Float, Float> operation) {
        final float x = operation.apply(x());
        final float y = operation.apply(y());
        final float z = operation.apply(z());
        return v3(x, y, z);
    }

    @Override
    public int dimension() {
        return 3;
    }

    @Override
    public Vector3 with(float value) {
        return v3(value, value, value);
    }

    @Override
    public Vector3 this_() {
        return this;
    }

    @Override
    public boolean isParallel(Vector3 vector, float epsilon) {
        final float newX = y() * vector.z() - z() * vector.y();
        final float newY = z() * vector.x() - x() * vector.z();
        final float newZ = x() * vector.y() - y() * vector.x();
        return v3(newX, newY, newZ).length2() <= epsilon;
    }
}
