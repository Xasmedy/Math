package io.github.xasmedy.math.rotation;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record SwingTwist(@NullRestricted Quaternion swing, @NullRestricted Quaternion twist) {
}
