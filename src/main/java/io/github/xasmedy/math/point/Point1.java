package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Point1<T extends Number> {

    T x();

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x) implements Point1<Integer> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x   ) implements Point1<Long> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x  ) implements Point1<Float> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x ) implements Point1<Double> {}
}
