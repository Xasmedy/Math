package io.github.xasmedy.math.point.abstracts;

public interface Point2<T> {

    T x();

    T y();

    interface F32 extends Point2<Float> {}

    interface F64 extends Point2<Double> {}

    interface I32 extends Point2<Integer> {}

    interface I64 extends Point2<Long> {}
}
