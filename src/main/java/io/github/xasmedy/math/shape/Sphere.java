package io.github.xasmedy.math.shape;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Sphere<T, P>(@NullRestricted P center,
                                 @NullRestricted T radius) implements Shape.Sphere<T, P> {}
