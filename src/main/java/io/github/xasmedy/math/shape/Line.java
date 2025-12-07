package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.abstracts.Point2;
import io.github.xasmedy.math.point.p2.Point2F32;
import io.github.xasmedy.math.point.p2.Point2F64;
import io.github.xasmedy.math.point.p2.Point2I32;
import io.github.xasmedy.math.point.p2.Point2I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

public interface Line<P extends Point2<?>> {

    P start();

    P end();

    @LooselyConsistentValue
    value record I32(@NullRestricted Point2I32 start,
                     @NullRestricted Point2I32 end) implements Line<Point2I32> {}

    @LooselyConsistentValue
    value record I64(@NullRestricted Point2I64 start,
                     @NullRestricted Point2I64 end) implements Line<Point2I64> {}

    @LooselyConsistentValue
    value record F32(@NullRestricted Point2F32 start,
                     @NullRestricted Point2F32 end) implements Line<Point2F32> {}

    @LooselyConsistentValue
    value record F64(@NullRestricted Point2F64 start,
                     @NullRestricted Point2F64 end) implements Line<Point2F64> {}
}
