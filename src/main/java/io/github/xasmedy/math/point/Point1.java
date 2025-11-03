package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.NullRestricted;

public interface Point1<T extends Number> {

    T x();

    value record I32(@NullRestricted Integer x) implements Point1<Integer> {}

    value record I64(@NullRestricted Long x   ) implements Point1<Long> {}

    value record F32(@NullRestricted Float x  ) implements Point1<Float> {}

    value record F64(@NullRestricted Double x ) implements Point1<Double> {}
}
