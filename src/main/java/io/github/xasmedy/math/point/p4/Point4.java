package io.github.xasmedy.math.point.p4;

public interface Point4<T> {

    T x();

    T y();

    T z();

    T w();

    interface F32 extends Point4<Float> {}

    interface F64 extends Point4<Double> {}

    interface I32 extends Point4<Integer> {}

    interface I64 extends Point4<Long> {}
}
