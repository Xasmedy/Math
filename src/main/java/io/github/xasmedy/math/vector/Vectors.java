package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point1;
import io.github.xasmedy.math.point.Point2;
import io.github.xasmedy.math.point.Point3;
import io.github.xasmedy.math.point.Point4;

public value class Vectors {

    private Vectors() {}

    public static Vector1 v1(float x) {
        return new Vector1(x);
    }

    public static Vector1 v1(Point1<Float> point) {
        return v1(point.x());
    }

    public static Vector2 v2(float x, float y) {
        return new Vector2(x, y);
    }

    public static Vector2 v2(Point2<Float> point) {
        return v2(point.x(), point.y());
    }

    public static Vector3 v3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    public static Vector3 v3(Point3<Float> point) {
        return v3(point.x(), point.y(), point.z());
    }

    public static Vector4 v4(float x, float y, float z, float w) {
        return new Vector4(x, y, z, w);
    }

    public static Vector4 v4(Point4<Float> point) {
        return v4(point.x(), point.y(), point.z(), point.w());
    }
}
