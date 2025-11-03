package io.github.xasmedy.math.shape;

import io.github.xasmedy.math.point.Point2;
import jdk.internal.vm.annotation.LooselyConsistentValue;

@LooselyConsistentValue
public value record Line(Point2<Float> start, Point2<Float> end) {}
