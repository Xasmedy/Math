package io.github.xasmedy.math.point.p4;

import io.github.xasmedy.math.point.abstracts.Point4;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point4F64(@NullRestricted Double x,
                              @NullRestricted Double y,
                              @NullRestricted Double z,
                              @NullRestricted Double w) implements Point4.F64 {
}
