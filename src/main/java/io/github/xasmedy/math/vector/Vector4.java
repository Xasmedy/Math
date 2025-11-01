package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point4D;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

@LooselyConsistentValue
public value record Vector4(float x, float y, float z, float w) implements Vector<Vector4>, Point4D {

    @Override
    public Vector4 sum(Vector4 value) {
        return v4(x() + value.x(),
                  y() + value.y(),
                  z() + value.z(),
                  w() + value.w());
    }

    @Override
    public Vector4 sub(Vector4 value) {
        return v4(x() - value.x(),
                  y() - value.y(),
                  z() - value.z(),
                  w() - value.w());
    }

    @Override
    public Vector4 mul(Vector4 value) {
        return v4(x() * value.x(),
                  y() * value.y(),
                  z() * value.z(),
                  w() * value.w());
    }

    @Override
    public Vector4 div(Vector4 value) {
        return v4(x() / value.x(),
                  y() / value.y(),
                  z() / value.z(),
                  w() / value.w());
    }

    @Override
    public boolean lt(Vector4 value) {
        return x() < value.x() &&
               y() < value.y() &&
               z() < value.z() &&
               w() < value.w();
    }

    @Override
    public boolean ltEq(Vector4 value) {
        return x() <= value.x() &&
               y() <= value.y() &&
               z() <= value.z() &&
               w() <= value.w();
    }

    @Override
    public boolean gt(Vector4 value) {
        return x() > value.x() &&
               y() > value.y() &&
               z() > value.z() &&
               w() > value.w();
    }

    @Override
    public boolean gtEq(Vector4 value) {
        return x() >= value.x() &&
               y() >= value.y() &&
               z() >= value.z() &&
               w() >= value.w();
    }

    @Override
    public float sum() {
        return x() + y() + z() + w();
    }

    @Override
    public Vector4 abs() {
        return v4(Math.abs(x()),
                  Math.abs(y()),
                  Math.abs(z()),
                  Math.abs(w()));
    }

    @Override
    public Vector4 operation(Function<Float, Float> operation) {
        final float newX = operation.apply(x());
        final float newY = operation.apply(y());
        final float newZ = operation.apply(z());
        final float newW = operation.apply(w());
        return v4(newX, newY, newZ, newW);
    }

    @Override
    public int dimension() {
        return 4;
    }

    @Override
    public Vector4 with(float value) {
        return v4(value, value, value, value);
    }

    @Override
    public Vector4 this_() {
        return this;
    }

    public Vector3 withoutW() {
        return v3(x(), y(), z());
    }
}
