package io.github.xasmedy.math.point;

import jdk.internal.vm.annotation.NullRestricted;

public interface Point2<T extends Number> {

    T x();

    T y();

    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y) implements Point2<Integer> {}

    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y   ) implements Point2<Long> {}

    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y  ) implements Point2<Float> {}

    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y ) implements Point2<Double> {}
}
