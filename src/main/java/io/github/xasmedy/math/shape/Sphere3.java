package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.abstracts.*;
import io.github.xasmedy.math.point.p3.Point3F32;
import io.github.xasmedy.math.point.p3.Point3F64;
import io.github.xasmedy.math.point.p3.Point3I32;
import io.github.xasmedy.math.point.p3.Point3I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Sphere3<T extends Number, P extends Point3<T>> {

    P center();

    T radius();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point3I32 center,
                     @NullRestricted Integer radius) implements Sphere3<Integer, Point3I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point3I64 center,
                     @NullRestricted Long radius) implements Sphere3<Long, Point3I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point3F32 center,
                     @NullRestricted Float radius) implements Sphere3<Float, Point3F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point3F64 center,
                     @NullRestricted Double radius) implements Sphere3<Double, Point3F64> {}
}
