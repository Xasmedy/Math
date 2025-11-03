package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Rectangle<T extends Number, P extends Point2<T>> {

    P position();

    T width();

    T height();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2.I32 position,
                     @NullRestricted Integer width,
                     @NullRestricted Integer height      ) implements Rectangle<Integer, Point2.I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2.I64 position,
                     @NullRestricted Long width,
                     @NullRestricted Long height         ) implements Rectangle<Long, Point2.I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2.F32 position,
                     @NullRestricted Float width ,
                     @NullRestricted Float height        ) implements Rectangle<Float, Point2.F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2.F64 position,
                     @NullRestricted Double width,
                     @NullRestricted Double height       ) implements Rectangle<Double, Point2.F64> {}
}
