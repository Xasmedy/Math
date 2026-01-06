package io.github.xasmedy.math.rotation;

import jdk.internal.vm.annotation.LooselyConsistentValue;

@LooselyConsistentValue
public value record Radians(double value) {

    public static Radians radians(double radians) {
        return new Radians(radians);
    }

    /// @return A radians value converted from the provided degrees.
    public static Radians degrees(double degrees) {
        return radians(Math.toRadians(degrees));
    }

    public double asDegrees() {
        return Math.toDegrees(value);
    }
}
