package io.github.xasmedy.math.point.p3;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point3F32(@NullRestricted Float x,
                              @NullRestricted Float y,
                              @NullRestricted Float z) implements Point3.F32 {
}
