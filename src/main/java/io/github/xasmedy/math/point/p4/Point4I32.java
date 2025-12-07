package io.github.xasmedy.math.point.p4;

import io.github.xasmedy.math.point.abstracts.Point4;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point4I32(@NullRestricted Integer x,
                              @NullRestricted Integer y,
                              @NullRestricted Integer z,
                              @NullRestricted Integer w) implements Point4.I32 {
}
