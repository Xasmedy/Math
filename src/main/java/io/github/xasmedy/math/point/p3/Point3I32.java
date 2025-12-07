package io.github.xasmedy.math.point.p3;

import io.github.xasmedy.math.point.abstracts.Point3;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point3I32(@NullRestricted Integer x,
                              @NullRestricted Integer y,
                              @NullRestricted Integer z) implements Point3.I32 {
}
