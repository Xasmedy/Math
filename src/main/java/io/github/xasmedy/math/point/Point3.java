package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Point3<T extends Number> {

    T x();

    T y();

    T z();

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z) implements Point3<Integer> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z   ) implements Point3<Long> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z  ) implements Point3<Float> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z ) implements Point3<Double> {}
}
