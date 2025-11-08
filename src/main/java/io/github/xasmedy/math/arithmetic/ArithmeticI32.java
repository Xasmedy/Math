package io.github.xasmedy.math.arithmetic;

public value class ArithmeticI32 implements Arithmetic<Integer> {

    @Override
    public Integer add(Integer value1, Integer value2) {
        return value1 + value2;
    }

    @Override
    public Integer sub(Integer value1, Integer value2) {
        return value1 - value2;
    }

    @Override
    public Integer mul(Integer value1, Integer value2) {
        return value1 * value2;
    }

    @Override
    public Integer div(Integer value1, Integer value2) {
        return value1 / value2;
    }

    @Override
    public boolean lt(Integer value1, Integer value2) {
        return value1 < value2;
    }

    @Override
    public boolean ltEq(Integer value1, Integer value2) {
        return value1 <= value2;
    }

    @Override
    public boolean gt(Integer value1, Integer value2) {
        return value1 > value2;
    }

    @Override
    public boolean gtEq(Integer value1, Integer value2) {
        return value1 >= value2;
    }

    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer one() {
        return 1;
    }

    @Override
    public Integer sqrt(Integer value) {
        return (int) Math.sqrt(value); // Kinda useless.
    }

    @Override
    public Integer abs(Integer value) {
        return Math.abs(value);
    }

    @Override
    public Integer max(Integer value1, Integer value2) {
        return Math.max(value1, value2);
    }

    @Override
    public Integer min(Integer value1, Integer value2) {
        return Math.min(value1, value2);
    }

    @Override
    public Integer ceil(Integer value) {
        return value;
    }

    @Override
    public Integer floor(Integer value) {
        return value;
    }
}
