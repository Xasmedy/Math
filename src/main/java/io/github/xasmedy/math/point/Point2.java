package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Point2<T extends Number> {

    T x();

    T y();

    // No need for @LooselyConsistentValue, the class is 64 bit big, meaning the CPU supports atomic operations.
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y) implements Point2<Integer> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y   ) implements Point2<Long> {}

    // No need for @LooselyConsistentValue, the class is 64 bit big, meaning the CPU supports atomic operations.
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y  ) implements Point2<Float> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y ) implements Point2<Double> {}
}
