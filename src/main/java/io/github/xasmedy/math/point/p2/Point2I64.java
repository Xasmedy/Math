package io.github.xasmedy.math.point.p2;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point2I64(@NullRestricted Long x,
                              @NullRestricted Long y) implements Point2.I64 {
}
