package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point3D;
import java.util.function.Function;
import static io.github.xasmedy.math.vector.Vector.*;

public value record Vector3(float x, float y, float z) implements Vector<Vector3, Point3D>, Point3D {

    @Override
    public Vector3 add(Point3D point3D) {
        return null;
    }

    @Override
    public Vector3 sub(Point3D point3D) {
        return null;
    }

    @Override
    public Vector3 scale(Point3D point3D) {
        return null;
    }

    @Override
    public Vector3 scale(float scalar) {
        return null;
    }

    @Override
    public float length2() {
        return 0;
    }

    @Override
    public Vector3 withLength2(float length2) {
        return null;
    }

    @Override
    public Vector3 limit2(float limit2) {
        return null;
    }

    @Override
    public Vector3 clamp(float min, float max) {
        return null;
    }

    @Override
    public Vector3 normalize() {
        return null;
    }

    @Override
    public float dot(Point3D point3D) {
        return 0;
    }

    @Override
    public float distance2(Point3D point3D) {
        return 0;
    }

    @Override
    public Vector3 lerp(Point3D target, float alpha) {
        return null;
    }

    @Override
    public Vector3 interpolate(Point3D target, float alpha, Function<Float, Float> interpolator) {
        return null;
    }

    @Override
    public boolean isUnit(float margin) {
        return false;
    }

    @Override
    public boolean isUnit() {
        return false;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isLengthZero(float margin) {
        return false;
    }

    @Override
    public boolean isParallel(Point3D point3D, float epsilon) {
        return false;
    }

    @Override
    public boolean isPerpendicular(Point3D point3D, float epsilon) {
        return false;
    }

    @Override
    public boolean hasSameDirection(Point3D point3D) {
        return false;
    }

    @Override
    public boolean hasOppositeDirection(Point3D point3D) {
        return false;
    }

    @Override
    public boolean epsilonEquals(Point3D point3D, float epsilon) {
        return false;
    }

    public Vector2 withoutZ() {
        return vector2(x(), y());
    }
}
