package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.abstracts.Point2;
import io.github.xasmedy.math.point.p2.Point2F32;
import io.github.xasmedy.math.point.p2.Point2F64;
import io.github.xasmedy.math.point.p2.Point2I32;
import io.github.xasmedy.math.point.p2.Point2I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Rectangle<T extends Number, P extends Point2<T>> {

    P position();

    T width();

    T height();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2I32 position,
                     @NullRestricted Integer width,
                     @NullRestricted Integer height) implements Rectangle<Integer, Point2I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2I64 position,
                     @NullRestricted Long width,
                     @NullRestricted Long height) implements Rectangle<Long, Point2I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2F32 position,
                     @NullRestricted Float width,
                     @NullRestricted Float height) implements Rectangle<Float, Point2F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2F64 position,
                     @NullRestricted Double width,
                     @NullRestricted Double height) implements Rectangle<Double, Point2F64> {}
}
