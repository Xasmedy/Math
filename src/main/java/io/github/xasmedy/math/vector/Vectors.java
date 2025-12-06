package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.vector.v1.Vector1F32;
import io.github.xasmedy.math.vector.v1.Vector1F64;
import io.github.xasmedy.math.vector.v1.Vector1I32;
import io.github.xasmedy.math.vector.v1.Vector1I64;
import io.github.xasmedy.math.vector.v2.Vector2F32;
import io.github.xasmedy.math.vector.v2.Vector2F64;
import io.github.xasmedy.math.vector.v2.Vector2I32;
import io.github.xasmedy.math.vector.v2.Vector2I64;
import io.github.xasmedy.math.vector.v3.Vector3F32;
import io.github.xasmedy.math.vector.v3.Vector3F64;
import io.github.xasmedy.math.vector.v3.Vector3I32;
import io.github.xasmedy.math.vector.v3.Vector3I64;
import io.github.xasmedy.math.vector.v4.Vector4F32;
import io.github.xasmedy.math.vector.v4.Vector4F64;
import io.github.xasmedy.math.vector.v4.Vector4I32;
import io.github.xasmedy.math.vector.v4.Vector4I64;

public value class Vectors {

    private Vectors() {}

    /* ===== Vector1 ===== */

    public static Vector1I32 v1(int x) {
        return new Vector1I32(x);
    }

    public static Vector1I64 v1(long x) {
        return new Vector1I64(x);
    }

    public static Vector1F32 v1(float x) {
        return new Vector1F32(x);
    }

    public static Vector1F64 v1(double x) {
        return new Vector1F64(x);
    }

    /* ===== Vector2 ===== */

    public static Vector2I32 v2(int x, int y) {
        return new Vector2I32(x, y);
    }

    public static Vector2I64 v2(long x, long y) {
        return new Vector2I64(x, y);
    }

    public static Vector2F32 v2(float x, float y) {
        return new Vector2F32(x, y);
    }

    public static Vector2F64 v2(double x, double y) {
        return new Vector2F64(x, y);
    }

    /* ===== Vector2 ===== */

    public static Vector3I32 v3(int x, int y, int z) {
        return new Vector3I32(x, y, z);
    }

    public static Vector3I64 v3(long x, long y, long z) {
        return new Vector3I64(x, y, z);
    }

    public static Vector3F32 v3(float x, float y, float z) {
        return new Vector3F32(x, y, z);
    }

    public static Vector3F64 v3(double x, double y, double z) {
        return new Vector3F64(x, y, z);
    }

    /* ===== Vector4 ===== */

    public static Vector4I32 v4(int x, int y, int z, int w) {
        return new Vector4I32(x, y, z, w);
    }

    public static Vector4I64 v4(long x, long y, long z, long w) {
        return new Vector4I64(x, y, z, w);
    }

    public static Vector4F32 v4(float x, float y, float z, float w) {
        return new Vector4F32(x, y, z, w);
    }

    public static Vector4F64 v4(double x, double y, double z, double w) {
        return new Vector4F64(x, y, z, w);
    }
}
