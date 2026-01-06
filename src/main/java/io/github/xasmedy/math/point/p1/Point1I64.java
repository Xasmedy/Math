package io.github.xasmedy.math.point.p1;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point1I64(@NullRestricted Long x) implements Point1.I64 {
}
