package io.github.xasmedy.math.point.p3;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point3I64(@NullRestricted Long x,
                              @NullRestricted Long y,
                              @NullRestricted Long z) implements Point3.I64 {
}
