package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.abstracts.*;
import io.github.xasmedy.math.point.p2.Point2F32;
import io.github.xasmedy.math.point.p2.Point2F64;
import io.github.xasmedy.math.point.p2.Point2I32;
import io.github.xasmedy.math.point.p2.Point2I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Sphere2<T extends Number, P extends Point2<T>> {

    P center();

    T radius();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2I32 center,
                     @NullRestricted Integer radius) implements Sphere2<Integer, Point2I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2I64 center,
                     @NullRestricted Long radius) implements Sphere2<Long, Point2I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2F32 center,
                     @NullRestricted Float radius) implements Sphere2<Float, Point2F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2F64 center,
                     @NullRestricted Double radius) implements Sphere2<Double, Point2F64> {}
}