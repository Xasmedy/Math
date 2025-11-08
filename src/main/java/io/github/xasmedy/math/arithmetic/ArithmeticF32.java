package io.github.xasmedy.math.arithmetic;

public value class ArithmeticF32 implements Arithmetic<Float> {

    @Override
    public Float add(Float value1, Float value2) {
        return value1 + value2;
    }

    @Override
    public Float sub(Float value1, Float value2) {
        return value1 - value2;
    }

    @Override
    public Float mul(Float value1, Float value2) {
        return value1 * value2;
    }

    @Override
    public Float div(Float value1, Float value2) {
        return value1 / value2;
    }

    @Override
    public Float zero() {
        return 0f;
    }

    @Override
    public Float one() {
        return 1f;
    }

    @Override
    public Float sqrt(Float value) {
        return (float) Math.sqrt(value);
    }

    @Override
    public Float abs(Float value) {
        return Math.abs(value);
    }

    @Override
    public Float max(Float value1, Float value2) {
        return Math.max(value1, value2);
    }

    @Override
    public Float min(Float value1, Float value2) {
        return Math.min(value1, value2);
    }

    @Override
    public Float ceil(Float value) {
        return (float) Math.ceil(value);
    }

    @Override
    public Float floor(Float value) {
        return (float) Math.floor(value);
    }

    @Override
    public boolean lt(Float value1, Float value2) {
        return value1 < value2;
    }

    @Override
    public boolean ltEq(Float value1, Float value2) {
        return value1 <= value2;
    }

    @Override
    public boolean gt(Float value1, Float value2) {
        return value1 > value2;
    }

    @Override
    public boolean gtEq(Float value1, Float value2) {
        return value1 >= value2;
    }
}
