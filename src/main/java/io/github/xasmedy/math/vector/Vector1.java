package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point1D;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector1(float x) implements Vector<Vector1>, Point1D {

    @Override
    public Vector1 sum(Vector1 value) {
        return v1(x() + value.x());
    }

    @Override
    public Vector1 sub(Vector1 value) {
        return v1(x() - value.x());
    }

    @Override
    public Vector1 mul(Vector1 value) {
        return v1(x() * value.x());
    }

    @Override
    public Vector1 div(Vector1 value) {
        return v1(x() / value.x());
    }

    @Override
    public boolean lt(Vector1 value) {
        return x() < value.x();
    }

    @Override
    public boolean ltEq(Vector1 value) {
        return x() <= value.x();
    }

    @Override
    public boolean gt(Vector1 value) {
        return x() > value.x();
    }

    @Override
    public boolean gtEq(Vector1 value) {
        return x() >= value.x();
    }

    @Override
    public float sum() {
        return x();
    }

    @Override
    public Vector1 abs() {
        return v1(Math.abs(x()));
    }

    @Override
    public Vector1 operation(Function<Float, Float> operation) {
        return v1(operation.apply(x()));
    }

    @Override
    public int dimension() {
        return 1;
    }

    @Override
    public Vector1 with(float value) {
        return v1(value);
    }

    @Override
    public Vector1 this_() {
        return this;
    }

    @Override
    public boolean isParallel(Vector1 vector, float epsilon) {
        if (x() == 0 || vector.x() == 0) return x() == vector.x();
        return true;
    }
}
