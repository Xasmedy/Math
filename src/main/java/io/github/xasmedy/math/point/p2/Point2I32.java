package io.github.xasmedy.math.point.p2;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point2I32(@NullRestricted Integer x,
                              @NullRestricted Integer y) implements Point2.I32 {
}
