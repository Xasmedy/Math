package io.github.xasmedy.math.point.p1;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point1F64(@NullRestricted Double x) implements Point1.F64 {
}
