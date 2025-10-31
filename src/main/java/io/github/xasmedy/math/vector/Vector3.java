package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point3D;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector3(float x, float y, float z) implements Vector<Vector3>, Point3D {

    @Override
    public Vector3 sum(Vector3 vector) {
        return vector3(x() + vector.x(), y() + vector.y(), z() + vector.z());
    }

    public Vector3 sum(float x, float y, float z) {
        return sum(vector3(x, y, z));
    }

    @Override
    public Vector3 sub(Vector3 vector) {
        return vector3(x() - vector.x(), y() - vector.y(), z() - vector.z());
    }

    public Vector3 sub(float x, float y, float z) {
        return sub(vector3(x, y, z));
    }

    @Override
    public Vector3 mul(Vector3 vector) {
        return vector3(x() * vector.x(), y() * vector.y(), z() * vector.z());
    }

    @Override
    public Vector3 div(Vector3 vector) {
        return vector3(x() / vector.x(), y() / vector.y(), z() / vector.z());
    }

    @Override
    public float sum() {
        return x() + y() + z();
    }

    @Override
    public Vector3 with(float value) {
        return vector3(value, value, value);
    }

    @Override
    public Vector3 this_() {
        return this;
    }

    @Override
    public boolean isParallel(Vector3 vector, float epsilon) {
        final float newX = y() * vector.z() - z() * vector.y();
        final float newY = z() * vector.x() - x() * vector.z();
        final float newZ = x() * vector.y() - y() * vector.x();
        return vector3(newX, newY, newZ).length2() <= epsilon;
    }

    @Override
    public boolean epsilonEquals(Vector3 vector, float epsilon) {
        if (Math.abs(vector.x() - x()) > epsilon) return false;
        if (Math.abs(vector.y() - y()) > epsilon) return false;
        return !(Math.abs(vector.z() - z()) > epsilon);
    }
}
