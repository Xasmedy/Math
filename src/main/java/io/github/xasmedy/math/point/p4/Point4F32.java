package io.github.xasmedy.math.point.p4;

import io.github.xasmedy.math.point.abstracts.Point4;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point4F32(@NullRestricted Float x,
                              @NullRestricted Float y,
                              @NullRestricted Float z,
                              @NullRestricted Float w) implements Point4.F32 {
}
