package io.github.xasmedy.math.point.p3;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Point3I32(@NullRestricted Integer x,
                              @NullRestricted Integer y,
                              @NullRestricted Integer z) implements Point3.I32 {
}
