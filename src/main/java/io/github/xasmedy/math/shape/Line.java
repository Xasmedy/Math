package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Line<P extends Point2<?>> {

    P start();

    P end();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2.I32 start,
                     @NullRestricted Point2.I32 end   ) implements Line<Point2.I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2.I64 start,
                     @NullRestricted Point2.I64 end   ) implements Line<Point2.I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2.F32 start,
                     @NullRestricted Point2.F32 end   ) implements Line<Point2.F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2.F64 start,
                     @NullRestricted Point2.F64 end   ) implements Line<Point2.F64> {}
}
