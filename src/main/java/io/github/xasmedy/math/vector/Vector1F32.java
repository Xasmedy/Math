package io.github.xasmedy.math.vector;

import jdk.internal.vm.annotation.NullRestricted;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vectors.v1;
import static io.github.xasmedy.math.vector.Vectors.v2;

// No need for @LooselyConsistentValue, the class is 64 bit big, meaning the CPU supports atomic operations.
public value record Vector1F32(@NullRestricted Float x) implements VectorF32<Vector1F32> {

    @Override
    public Vector1F32 add(Vector1F32 value) {
        return v1(x() + value.x());
    }

    @Override
    public Vector1F32 sub(Vector1F32 value) {
        return v1(x() - value.x());
    }

    @Override
    public Vector1F32 mul(Vector1F32 value) {
        return v1(x() * value.x());
    }

    @Override
    public Vector1F32 div(Vector1F32 value) {
        return v1(x() / value.x());
    }

    @Override
    public boolean lt(Vector1F32 value) {
        return x() < value.x();
    }

    @Override
    public boolean ltEq(Vector1F32 value) {
        return x() <= value.x();
    }

    @Override
    public boolean gt(Vector1F32 value) {
        return x() > value.x();
    }

    @Override
    public boolean gtEq(Vector1F32 value) {
        return x() >= value.x();
    }

    @Override
    public float sum() {
        return x();
    }

    @Override
    public Vector1F32 abs() {
        return v1(Math.abs(x()));
    }

    @Override
    public Vector1F32 operation(Function<Float, Float> operation) {
        return v1(operation.apply(x()));
    }

    @Override
    public int dimension() {
        return 1;
    }

    @Override
    public Vector1F32 with(float value) {
        return v1(value);
    }

    @Override
    public Vector1F32 this_() {
        return this;
    }

    @Override
    public boolean isCollinear(Vector1F32 vector, float epsilon) {
        if (x() == 0 || vector.x() == 0) return x().equals(vector.x());
        return true;
    }

    public Vector2F32 withY(float y) {
        return v2(x(), y);
    }
}