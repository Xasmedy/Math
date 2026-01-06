package io.github.xasmedy.math.rotation;

public enum GimbalPole {

    NORTH(1),
    SOUTH(-1),
    NONE(0);

    public final int sign;

    GimbalPole(int sign) {
        this.sign = sign;
    }
}
