package io.github.xasmedy.math.unit;

// No need for @LooselyConsistentValue, the class is 64 bit big, meaning the CPU supports atomic operations.
public value record Radians(float value) {

    public static Radians radians(float radians) {
        return new Radians(radians);
    }

    /// @return A radians value converted from the provided degrees.
    public static Radians degrees(float degrees) {
        return radians((float) Math.toRadians(degrees));
    }

    public float toDegrees() {
        return (float) Math.toDegrees(value);
    }
}
