package io.github.xasmedy.math.point.p3;

public interface Point3<T> {

    T x();

    T y();

    T z();

    interface F32 extends Point3<Float> {}

    interface F64 extends Point3<Double> {}

    interface I32 extends Point3<Integer> {}

    interface I64 extends Point3<Long> {}
}
