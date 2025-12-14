package io.github.xasmedy.math.point.p2;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point2F32(@NullRestricted Float x,
                              @NullRestricted Float y) implements Point2.F32 {
}
