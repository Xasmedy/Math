package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point1D;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector1(float x) implements Vector<Vector1>, Point1D {

    @Override
    public Vector1 sum(Vector1 input) {
        return vector1(x() + input.x());
    }

    @Override
    public Vector1 sub(Vector1 input) {
        return vector1(x() - input.x());
    }

    @Override
    public Vector1 mul(Vector1 input) {
        return vector1(x() * input.x());
    }

    @Override
    public Vector1 div(Vector1 input) {
        return vector1(x() / input.x());
    }

    @Override
    public float sum() {
        return x();
    }

    @Override
    public Vector1 with(float value) {
        return vector1(value);
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

    @Override
    public boolean epsilonEquals(Vector1 vector, float epsilon) {
        return !(Math.abs(vector.x() - x()) > epsilon);
    }
}
