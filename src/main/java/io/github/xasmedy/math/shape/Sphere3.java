package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point3;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Sphere3<T extends Number, P extends Point3<T>> {

    P center();

    T radius();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point3.I32 center,
                     @NullRestricted Integer    radius) implements Sphere3<Integer, Point3.I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point3.I64 center,
                     @NullRestricted Long       radius) implements Sphere3<Long, Point3.I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point3.F32 center,
                     @NullRestricted Float      radius) implements Sphere3<Float, Point3.F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point3.F64 center,
                     @NullRestricted Double     radius) implements Sphere3<Double, Point3.F64> {}
}
