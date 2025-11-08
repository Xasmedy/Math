package io.github.xasmedy.math.arithmetic;

public value class ArithmeticI64 implements Arithmetic<Long> {

    @Override
    public Long add(Long value1, Long value2) {
        return value1 + value2;
    }

    @Override
    public Long sub(Long value1, Long value2) {
        return value1 - value2;
    }

    @Override
    public Long mul(Long value1, Long value2) {
        return value1 * value2;
    }

    @Override
    public Long div(Long value1, Long value2) {
        return value1 / value2;
    }

    @Override
    public boolean lt(Long value1, Long value2) {
        return value1 < value2;
    }

    @Override
    public boolean ltEq(Long value1, Long value2) {
        return value1 <= value2;
    }

    @Override
    public boolean gt(Long value1, Long value2) {
        return value1 > value2;
    }

    @Override
    public boolean gtEq(Long value1, Long value2) {
        return value1 >= value2;
    }

    @Override
    public Long zero() {
        return 0L;
    }

    @Override
    public Long one() {
        return 1L;
    }

    @Override
    public Long sqrt(Long value) {
        return (long) Math.sqrt(value);
    }

    @Override
    public Long abs(Long value) {
        return Math.abs(value);
    }

    @Override
    public Long max(Long value1, Long value2) {
        return Math.max(value1, value2);
    }

    @Override
    public Long min(Long value1, Long value2) {
        return Math.min(value1, value2);
    }

    @Override
    public Long ceil(Long value) {
        return value;
    }

    @Override
    public Long floor(Long value) {
        return value;
    }
}
