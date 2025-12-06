package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

// TODO Have refined interfaces for T, because of performance,
//  and move the value record class as Point1F32, Point1I32 (the same as the vectors)
public interface Point1<T> {

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
