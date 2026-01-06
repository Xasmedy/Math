package io.github.xasmedy.math.point.p1;

public interface Point1<T> {

    T x();

    interface F32 extends Point1<Float> {}

    interface F64 extends Point1<Double> {}

    interface I32 extends Point1<Integer> {}

    interface I64 extends Point1<Long> {}
}
