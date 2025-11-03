package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.NullRestricted;

public interface Point3<T extends Number> {

    T x();

    T y();

    T z();

    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z) implements Point3<Integer> {}

    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z   ) implements Point3<Long> {}

    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z  ) implements Point3<Float> {}

    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z ) implements Point3<Double> {}
}
