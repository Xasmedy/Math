package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;

@LooselyConsistentValue
public value record Rectangle(Point2<Float> position, float width, float height) {}
