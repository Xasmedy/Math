package io.github.xasmedy.math.point.p2;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point2F64(@NullRestricted Double x,
                              @NullRestricted Double y) implements Point2.F64 {
}
