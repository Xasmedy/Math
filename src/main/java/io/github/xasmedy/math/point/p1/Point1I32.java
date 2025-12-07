package io.github.xasmedy.math.point.p1;

import io.github.xasmedy.math.point.abstracts.Point1;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
value public record Point1I32(@NullRestricted Integer x) implements Point1.I32 {
}
