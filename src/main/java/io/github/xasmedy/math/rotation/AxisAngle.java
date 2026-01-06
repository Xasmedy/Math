package io.github.xasmedy.math.rotation;

import io.github.xasmedy.math.vector.v3.Vector3F64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record AxisAngle(@NullRestricted Vector3F64 axis, @NullRestricted Radians angle) {}
