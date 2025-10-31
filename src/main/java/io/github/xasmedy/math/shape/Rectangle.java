package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2D;
import jdk.internal.vm.annotation.LooselyConsistentValue;

@LooselyConsistentValue
public value record Rectangle(Point2D position, float width, float height) {}
