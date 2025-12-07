package io.github.xasmedy.math.point.abstracts;

// TODO Have refined interfaces for T, because of performance,
//  and move the value record class as Point1F32, Point1I32 (the same as the vectors)
public interface Point1<T> {

    T x();

    interface F32 extends Point1<Float> {}

    interface F64 extends Point1<Double> {}

    interface I32 extends Point1<Integer> {}

    interface I64 extends Point1<Long> {}
}
