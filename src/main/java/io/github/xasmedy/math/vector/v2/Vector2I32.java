package io.github.xasmedy.math.vector.v2;

import io.github.xasmedy.math.point.p2.Point2;
import io.github.xasmedy.math.unit.Radians;
import io.github.xasmedy.math.vector.v1.Vector1I32;
import io.github.xasmedy.math.vector.v3.Vector3I32;
import jdk.internal.vm.annotation.NullRestricted;

import static io.github.xasmedy.math.vector.Vectors.v2;

public value record Vector2I32(@NullRestricted Integer x,
                               @NullRestricted Integer y
) implements Vector2<Vector2I32, Integer>, Vector2.Int<Vector2I32, Integer>, Point2.I32 {

    @Override
    public Vector2F32 asReal() {
        return new Vector2F32((float) x(), (float) y());
    }

    @Override
    public Vector1I32 asV1() {
        return new Vector1I32(x());
    }

    @Override
    public Vector3I32 asV3(Integer z) {
        return new Vector3I32(x(), y(), z);
    }

    @Override
    public Vector2I32 rotate(Radians radians) {

        final float cos = (float) Math.cos(radians.value());
        final float sin = (float) Math.sin(radians.value());

        final int newX = (int) (x() * cos - y() * sin);
        final int newY = (int) (x() * sin + y() * cos);

        return v2(newX, newY);
    }

    @Override
    public Radians angle() {
        return Radians.radians(Math.atan2(y(), x()));
    }

    @Override
    public Integer cross(Vector2I32 vector) {
        return x() * vector.y() - y() * vector.x();
    }

    @Override
    public Integer component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Integer sum() {
        return x() + y();
    }

    @Override
    public Vector2I32 add(Vector2I32 other) {
        return v2(x() + other.x(), y() + other.y());
    }

    @Override
    public Vector2I32 sub(Vector2I32 other) {
        return v2(x() - other.x(), y() - other.y());
    }

    @Override
    public Vector2I32 mul(Vector2I32 other) {
        return v2(x() * other.x(), y() * other.y());
    }

    @Override
    public Vector2I32 mul(Integer scalar) {
        return mul(v2(scalar, scalar));
    }

    @Override
    public Vector2I32 div(Vector2I32 other) {
        return v2(x() / other.x(), y() / other.y());
    }

    @Override
    public boolean lt(Vector2I32 other) {
        return x() < other.x() && y() < other.y();
    }

    @Override
    public boolean ltEq(Vector2I32 other) {
        return x() <= other.x() && y() <= other.y();
    }

    @Override
    public boolean gt(Vector2I32 other) {
        return x() > other.x() && y() > other.y();
    }

    @Override
    public boolean gtEq(Vector2I32 other) {
        return x() >= other.x() && y() >= other.y();
    }

    @Override
    public Vector2I32 abs() {
        return v2(Math.abs(x()), Math.abs(y()));
    }

    @Override
    public Vector2I32 max(Vector2I32 other) {
        return v2(Math.max(x(), other.x()), Math.max(y(), other.y()));
    }

    @Override
    public Vector2I32 min(Vector2I32 other) {
        return v2(Math.min(x(), other.x()), Math.min(y(), other.y()));
    }

    @Override
    public Integer distance2(Vector2I32 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Integer length2() {
        return mul(this).sum();
    }

    @Override
    public Integer dot(Vector2I32 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector2I32 clamp(Integer min, Integer max) {
        final int x = Math.clamp(x(), min, max);
        final int y = Math.clamp(y(), min, max);
        return v2(x, y);
    }

    @Override
    public boolean hasSameDirection(Vector2I32 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector2I32 vector) {
        return dot(vector) < 0;
    }
}
