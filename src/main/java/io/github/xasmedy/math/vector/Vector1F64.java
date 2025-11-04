package io.github.xasmedy.math.vector;

import jdk.internal.vm.annotation.NullRestricted;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vectors.v1;
import static io.github.xasmedy.math.vector.Vectors.v2;

public value record Vector1F64(@NullRestricted Double x) implements VectorF64<Vector1F64> {

    @Override
    public Vector1F64 add(Vector1F64 value) {
        return v1(x() + value.x());
    }

    @Override
    public Vector1F64 sub(Vector1F64 value) {
        return v1(x() - value.x());
    }

    @Override
    public Vector1F64 mul(Vector1F64 value) {
        return v1(x() * value.x());
    }

    @Override
    public Vector1F64 div(Vector1F64 value) {
        return v1(x() / value.x());
    }

    @Override
    public boolean lt(Vector1F64 value) {
        return x() < value.x();
    }

    @Override
    public boolean ltEq(Vector1F64 value) {
        return x() <= value.x();
    }

    @Override
    public boolean gt(Vector1F64 value) {
        return x() > value.x();
    }

    @Override
    public boolean gtEq(Vector1F64 value) {
        return x() >= value.x();
    }

    @Override
    public double sum() {
        return x();
    }

    @Override
    public Vector1F64 abs() {
        return v1(Math.abs(x()));
    }

    @Override
    public Vector1F64 operation(Function<Double, Double> operation) {
        return v1(operation.apply(x()));
    }

    @Override
    public int dimension() {
        return 1;
    }

    @Override
    public Vector1F64 with(double value) {
        return v1(value);
    }

    @Override
    public Vector1F64 this_() {
        return this;
    }

    @Override
    public boolean isCollinear(Vector1F64 vector, double epsilon) {
        if (x() == 0 || vector.x() == 0) return x().equals(vector.x());
        return true;
    }

    public Vector2F64 withY(double y) {
        return v2(x(), y);
    }
}
