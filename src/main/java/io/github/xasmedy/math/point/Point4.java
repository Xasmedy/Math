package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Point4<T> {

    T x();

    T y();

    T z();

    T w();

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z,
                     @NullRestricted Integer w) implements Point4<Integer> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z,
                     @NullRestricted Long w   ) implements Point4<Long> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z,
                     @NullRestricted Float w  ) implements Point4<Float> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z,
                     @NullRestricted Double w ) implements Point4<Double> {}
}
