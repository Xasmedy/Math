package io.github.xasmedy.math.point.p3;

import io.github.xasmedy.math.point.abstracts.Point3;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point3F32(@NullRestricted Float x,
                              @NullRestricted Float y,
                              @NullRestricted Float z) implements Point3.F32 {
}
