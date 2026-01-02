package io.github.xasmedy.math.unit;

// No need for @LooselyConsistentValue, the class is 64 bit big, meaning the CPU supports atomic operations.
public value record Radians(double value) {

    // TODO rad() and deg() aliases?

    public static Radians radians(double radians) {
        return new Radians(radians);
    }

    /// @return A radians value converted from the provided degrees.
    public static Radians degrees(double degrees) {
        return radians(Math.toRadians(degrees));
    }

    public double toDegrees() {
        return Math.toDegrees(value);
    }
}
