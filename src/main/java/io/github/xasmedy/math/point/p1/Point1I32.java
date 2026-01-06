package io.github.xasmedy.math.point.p1;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point1I32(@NullRestricted Integer x) implements Point1.I32 {
}
