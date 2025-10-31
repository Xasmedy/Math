package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point3D;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector3(float x, float y, float z) implements Vector<Vector3, Point3D>, Point3D {

    @Override
    public Vector3 sum(Point3D vector) {
        return vector3(x() + vector.x(), y() + vector.y(), z() + vector.z());
    }

    public Vector3 add(float x, float y, float z) {
        return sum(vector3(x, y, z));
    }

    @Override
    public Vector3 sub(Point3D vector) {
        return vector3(x() - vector.x(), y() - vector.y(), z() - vector.z());
    }

    public Vector3 sub(float x, float y, float z) {
        return sub(vector3(x, y, z));
    }

    @Override
    public Vector3 scale(Point3D vector) {
        return vector3(x() * vector.x(), y() * vector.y(), z() * vector.z());
    }

    @Override
    public Vector3 mul(float scalar) {
        return scale(vector3(scalar, scalar, scalar));
    }

    @Override
    public float length2() {
        return x() * x() + y() * y() + z() * z();
    }

    @Override
    public Vector3 normalize() {
    }

    @Override
    public float dot(Point3D vector) {
        return 0;
    }

    @Override
    public float distance2(Point3D vector) {
        return 0;
    }

    @Override
    public Vector3 lerp(Point3D target, float alpha) {
        return null;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isParallel(Point3D vector, float epsilon) {
        return false;
    }

    @Override
    public boolean epsilonEquals(Point3D vector, float epsilon) {
        return false;
    }

    @Override
    public Vector3 this_() {
        return this;
    }

    public Vector2 withoutZ() {
        return vector2(x(), y());
    }
}
