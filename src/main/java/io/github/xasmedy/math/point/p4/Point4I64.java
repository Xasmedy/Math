package io.github.xasmedy.math.point.p4;

import io.github.xasmedy.math.point.abstracts.Point4;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point4I64(@NullRestricted Long x,
                              @NullRestricted Long y,
                              @NullRestricted Long z,
                              @NullRestricted Long w) implements Point4.I64 {
}
