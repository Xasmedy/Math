package io.github.xasmedy.math.point.p3;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point3F64(@NullRestricted Double x,
                              @NullRestricted Double y,
                              @NullRestricted Double z) implements Point3.F64 {
}
