package io.github.xasmedy.math.shape;

import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

@LooselyConsistentValue
public value record Rectangle<T, P>(@NullRestricted P pos,
                                    @NullRestricted T width,
                                    @NullRestricted T height) implements Shape.Rectangle<T, P> {}