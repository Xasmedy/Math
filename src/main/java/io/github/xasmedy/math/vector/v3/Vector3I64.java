package io.github.xasmedy.math.vector.v3;

import io.github.xasmedy.math.point.p3.Point3;
import io.github.xasmedy.math.vector.v2.Vector2I64;
import io.github.xasmedy.math.vector.v4.Vector4I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

import static io.github.xasmedy.math.vector.Vectors.v3;

@LooselyConsistentValue
public value record Vector3I64(@NullRestricted Long x,
                               @NullRestricted Long y,
                               @NullRestricted Long z
) implements Vector3<Vector3I64, Long>, Vector3.Int<Vector3I64, Long>, Point3.I64 {

    @Override
    public Vector3F64 asReal() {
        return new Vector3F64((double) x(), (double) y(), (double) z());
    }

    @Override
    public Vector2I64 withoutZ() {
        return new Vector2I64(x(), y());
    }

    @Override
    public Vector4I64 withW(Long w) {
        return new Vector4I64(x(), y(), z(), w);
    }

    @Override
    public Vector3I64 cross(Vector3I64 vector) {
        final long x = y() * vector.z() - z() * vector.y();
        final long y = z() * vector.x() - x() * vector.z();
        final long z = x() * vector.y() - y() * vector.x();
        return v3(x, y, z);
    }

    @Override
    public Long component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Long sum() {
        return x() + y() + z();
    }

    @Override
    public Vector3I64 add(Vector3I64 other) {
        final long x = x() + other.x();
        final long y = y() + other.y();
        final long z = z() + other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I64 sub(Vector3I64 other) {
        final long x = x() - other.x();
        final long y = y() - other.y();
        final long z = z() - other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I64 mul(Vector3I64 other) {
        final long x = x() * other.x();
        final long y = y() * other.y();
        final long z = z() * other.z();
        return v3(x, y, z);
    }

    @Override
    public Vector3I64 mul(Long scalar) {
        return mul(v3(scalar, scalar, scalar));
    }

    @Override
    public Vector3I64 div(Vector3I64 other) {
        final long x = x() / other.x();
        final long y = y() / other.y();
        final long z = z() / other.z();
        return v3(x, y, z);
    }

    @Override
    public boolean lt(Vector3I64 other) {
        return x() < other.x() && y() < other.y() && z() < other.z();
    }

    @Override
    public boolean ltEq(Vector3I64 other) {
        return x() <= other.x() && y() <= other.y() && z() <= other.z();
    }

    @Override
    public boolean gt(Vector3I64 other) {
        return x() > other.x() && y() > other.y() && z() > other.z();
    }

    @Override
    public boolean gtEq(Vector3I64 other) {
        return x() >= other.x() && y() >= other.y() && z() >= other.z();
    }

    @Override
    public Vector3I64 abs() {
        return v3(Math.abs(x()), Math.abs(y()), Math.abs(z()));
    }

    @Override
    public Vector3I64 max(Vector3I64 other) {
        return v3(Math.max(x(), other.x()), Math.max(y(), other.y()), Math.max(z(), other.z()));
    }

    @Override
    public Vector3I64 min(Vector3I64 other) {
        return v3(Math.min(x(), other.x()), Math.min(y(), other.y()), Math.min(z(), other.z()));
    }

    @Override
    public Long distance2(Vector3I64 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Long length2() {
        return mul(this).sum();
    }

    @Override
    public Long dot(Vector3I64 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector3I64 clamp(Long min, Long max) {
        final long x = Math.clamp(x(), min, max);
        final long y = Math.clamp(y(), min, max);
        final long z = Math.clamp(z(), min, max);
        return v3(x, y, z);
    }

    @Override
    public boolean hasSameDirection(Vector3I64 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector3I64 vector) {
        return dot(vector) < 0;
    }
}
