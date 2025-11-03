package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Sphere2<T extends Number, P extends Point2<T>> {

    P center();

    T radius();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2.I32 center,
                     @NullRestricted Integer    radius) implements Sphere2<Integer, Point2.I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2.I64 center,
                     @NullRestricted Long       radius) implements Sphere2<Long, Point2.I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2.F32 center,
                     @NullRestricted Float      radius) implements Sphere2<Float, Point2.F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2.F64 center,
                     @NullRestricted Double     radius) implements Sphere2<Double, Point2.F64> {}
}