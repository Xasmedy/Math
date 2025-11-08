package io.github.xasmedy.math.vector;

public value class Vectors {

    private Vectors() {}

    /* ===== Vector1 ===== */

    public static Vector1.I32 v1(int x) {
        return new Vector1.I32(x);
    }

    public static Vector1.I64 v1(long x) {
        return new Vector1.I64(x);
    }

    public static Vector1.F32 v1(float x) {
        return new Vector1.F32(x);
    }

    public static Vector1.F64 v1(double x) {
        return new Vector1.F64(x);
    }

    /* ===== Vector2 ===== */

    public static Vector2.I32 v2(int x, int y) {
        return new Vector2.I32(x, y);
    }

    public static Vector2.I64 v2(long x, long y) {
        return new Vector2.I64(x, y);
    }

    public static Vector2.F32 v2(float x, float y) {
        return new Vector2.F32(x, y);
    }

    public static Vector2.F64 v2(double x, double y) {
        return new Vector2.F64(x, y);
    }

    /* ===== Vector2 ===== */

    public static Vector3.I32 v3(int x, int y, int z) {
        return new Vector3.I32(x, y, z);
    }

    public static Vector3.I64 v3(long x, long y, long z) {
        return new Vector3.I64(x, y, z);
    }

    public static Vector3.F32 v3(float x, float y, float z) {
        return new Vector3.F32(x, y, z);
    }

    public static Vector3.F64 v3(double x, double y, double z) {
        return new Vector3.F64(x, y, z);
    }

    /* ===== Vector4 ===== */

    public static Vector4.I32 v4(int x, int y, int z, int w) {
        return new Vector4.I32(x, y, z, w);
    }

    public static Vector4.I64 v4(long x, long y, long z, long w) {
        return new Vector4.I64(x, y, z, w);
    }

    public static Vector4.F32 v4(float x, float y, float z, float w) {
        return new Vector4.F32(x, y, z, w);
    }

    public static Vector4.F64 v4(double x, double y, double z, double w) {
        return new Vector4.F64(x, y, z, w);
    }
}
