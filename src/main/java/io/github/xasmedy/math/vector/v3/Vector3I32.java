package io.github.xasmedy.math.vector.v3;

import io.github.xasmedy.math.point.p3.Point3;
import io.github.xasmedy.math.vector.v2.Vector2I32;
import io.github.xasmedy.math.vector.v4.Vector4I32;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.v3;

@LooselyConsistentValue
public value record Vector3I32(@NullRestricted Integer x,
                               @NullRestricted Integer y,
                               @NullRestricted Integer z
) implements Vector3<Vector3I32, Integer>, Vector3.Int<Vector3I32, Integer>, Point3.I32 {

    @Override
    public Vector3F32 asReal() {
        return new Vector3F32((float) x(), (float) y(), (float) z());
    }

    @Override
    public Vector2I32 withoutZ() {
        return new Vector2I32(x(), y());
    }

    @Override
    public Vector4I32 withW(Integer w) {
        return new Vector4I32(x(), y(), z(), w);
    }

    @Override
    public Vector3I32 cross(Vector3I32 vector) {
        final int x = y() * vector.z() - z() * vector.y();
        final int y = z() * vector.x() - x() * vector.z();
        final int z = x() * vector.y() - y() * vector.x();
        return v3(x, y, z);
    }

    @Override
    public Integer component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Integer sum() {
        return x() + y() + z();
    }

    @Override
    public Vector3I32 add(Vector3I32 other) {
        final int x = x() + other.x();
        final int y = y() + other.y();
        final int z = z() + other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I32 sub(Vector3I32 other) {
        final int x = x() - other.x();
        final int y = y() - other.y();
        final int z = z() - other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I32 mul(Vector3I32 other) {
        final int x = x() * other.x();
        final int y = y() * other.y();
        final int z = z() * other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I32 mul(Integer scalar) {
        return mul(v3(scalar, scalar, scalar));
    }

    @Override
    public Vector3I32 div(Vector3I32 other) {
        final int x = x() / other.x();
        final int y = y() / other.y();
        final int z = z() / other.z();
        return v3(x, y, z);
    }

    @Override
    public boolean lt(Vector3I32 other) {
        return x() < other.x() && y() < other.y() && z() < other.z();
    }

    @Override
    public boolean ltEq(Vector3I32 other) {
        return x() <= other.x() && y() <= other.y() && z() <= other.z();
    }

    @Override
    public boolean gt(Vector3I32 other) {
        return x() > other.x() && y() > other.y() && z() > other.z();
    }

    @Override
    public boolean gtEq(Vector3I32 other) {
        return x() >= other.x() && y() >= other.y() && z() >= other.z();
    }

    @Override
    public Vector3I32 abs() {
        return v3(Math.abs(x()), Math.abs(y()), Math.abs(z()));
    }

    @Override
    public Vector3I32 max(Vector3I32 other) {
        return v3(Math.max(x(), other.x()), Math.max(y(), other.y()), Math.max(z(), other.z()));
    }

    @Override
    public Vector3I32 min(Vector3I32 other) {
        return v3(Math.min(x(), other.x()), Math.min(y(), other.y()), Math.min(z(), other.z()));
    }

    @Override
    public Integer distance2(Vector3I32 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Integer length2() {
        return mul(this).sum();
    }

    @Override
    public Integer dot(Vector3I32 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector3I32 clamp(Integer min, Integer max) {
        final int x = Math.clamp(x(), min, max);
        final int y = Math.clamp(y(), min, max);
        final int z = Math.clamp(z(), min, max);
        return v3(x, y, z);
    }

    @Override
    public boolean hasSameDirection(Vector3I32 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector3I32 vector) {
        return dot(vector) < 0;
    }
}
