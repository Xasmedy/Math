package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

// TODO Generalize to all dimensions, and call this a Sphere, if worth it
public interface Circle<T extends Number, P extends Point2<T>> {

    P center();

    T radius();

    // TODO If I want to generalise the dimension,
    //  I need to make a lot of marker interfaces for point; PointI32, PointF32.
    @LooselyConsistentValue
    value record I32(@NullRestricted Point2.I32 center,
                     @NullRestricted Integer radius    ) implements Circle<Integer, Point2.I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2.I64 center,
                     @NullRestricted Long radius       ) implements Circle<Long, Point2.I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2.F32 center,
                     @NullRestricted Float radius      ) implements Circle<Float, Point2.F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2.F64 center,
                     @NullRestricted Double radius     ) implements Circle<Double, Point2.F64> {}
}