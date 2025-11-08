package io.github.xasmedy.math.arithmetic;

public value class ArithmeticF64 implements Arithmetic<Double> {

    ArithmeticF64() {}

    @Override
    public Double add(Double value1, Double value2) {
        return value1 + value2;
    }

    @Override
    public Double sub(Double value1, Double value2) {
        return value1 - value2;
    }

    @Override
    public Double mul(Double value1, Double value2) {
        return value1 * value2;
    }

    @Override
    public Double div(Double value1, Double value2) {
        return value1 / value2;
    }

    @Override
    public boolean lt(Double value1, Double value2) {
        return value1 < value2;
    }

    @Override
    public boolean ltEq(Double value1, Double value2) {
        return value1 <= value2;
    }

    @Override
    public boolean gt(Double value1, Double value2) {
        return value1 > value2;
    }

    @Override
    public boolean gtEq(Double value1, Double value2) {
        return value1 >= value2;
    }

    @Override
    public Double zero() {
        return 0d;
    }

    @Override
    public Double one() {
        return 1d;
    }

    @Override
    public Double sqrt(Double value) {
        return Math.sqrt(value);
    }

    @Override
    public Double abs(Double value) {
        return Math.abs(value);
    }

    @Override
    public Double max(Double value1, Double value2) {
        return Math.max(value1, value2);
    }

    @Override
    public Double min(Double value1, Double value2) {
        return Math.min(value1, value2);
    }

    @Override
    public Double ceil(Double value) {
        return Math.ceil(value);
    }

    @Override
    public Double floor(Double value) {
        return Math.floor(value);
    }
}
