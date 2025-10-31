package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2D;
import jdk.internal.vm.annotation.LooselyConsistentValue;

@LooselyConsistentValue
public value record Circle(Point2D position, float radius) {}
