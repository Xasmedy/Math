package io.github.xasmedy.math.vector.v4;

import io.github.xasmedy.math.point.abstracts.Point4;
import io.github.xasmedy.math.vector.abstracts.Vector;
import io.github.xasmedy.math.vector.abstracts.Vector4;
import io.github.xasmedy.math.vector.v3.Vector3I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.v4;

@LooselyConsistentValue
public value record Vector4I64(@NullRestricted Long x,
                               @NullRestricted Long y,
                               @NullRestricted Long z,
                               @NullRestricted Long w
) implements Vector4<Vector4I64, Long>, Vector.Int<Vector4I64, Long>, Point4.I64 {

    @Override
    public Vector4F64 asReal() {
        return new Vector4F64((double) x(), (double) y(), (double) z(), (double) w());
    }

    @Override
    public Vector3I64 withoutW() {
        return new Vector3I64(x(), y(), z());
    }

    @Override
    public Long component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            case 3 -> w();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Long sum() {
        return x() + y() + z() + w();
    }

    @Override
    public Vector4I64 add(Vector4I64 other) {
        final long x = x() + other.x();
        final long y = y() + other.y();
        final long z = z() + other.z();
        final long w = w() + other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I64 sub(Vector4I64 other) {
        final long x = x() - other.x();
        final long y = y() - other.y();
        final long z = z() - other.z();
        final long w = w() - other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I64 mul(Vector4I64 other) {
        final long x = x() * other.x();
        final long y = y() * other.y();
        final long z = z() * other.z();
        final long w = w() * other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I64 mul(Long scalar) {
        return mul(v4(scalar, scalar, scalar, scalar));
    }

    @Override
    public Vector4I64 div(Vector4I64 other) {
        final long x = x() / other.x();
        final long y = y() / other.y();
        final long z = z() / other.z();
        final long w = w() / other.w();
        return v4(x, y, z, w);
    }

    @Override
    public boolean lt(Vector4I64 other) {
        return x() < other.x() && y() < other.y() &&
                z() < other.z() && w() < other.w();
    }

    @Override
    public boolean ltEq(Vector4I64 other) {
        return x() <= other.x() && y() <= other.y() &&
                z() <= other.z() && w() <= other.w();
    }

    @Override
    public boolean gt(Vector4I64 other) {
        return x() > other.x() && y() > other.y() &&
                z() > other.z() && w() > other.w();
    }

    @Override
    public boolean gtEq(Vector4I64 other) {
        return x() >= other.x() && y() >= other.y() &&
                z() >= other.z() && w() >= other.w();
    }

    @Override
    public Vector4I64 abs() {
        final long x = Math.abs(x());
        final long y = Math.abs(y());
        final long z = Math.abs(z());
        final long w = Math.abs(w());
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I64 max(Vector4I64 other) {
        final long x = Math.max(x(), other.x());
        final long y = Math.max(y(), other.y());
        final long z = Math.max(z(), other.z());
        final long w = Math.max(w(), other.w());
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I64 min(Vector4I64 other) {
        final long x = Math.min(x(), other.x());
        final long y = Math.min(y(), other.y());
        final long z = Math.min(z(), other.z());
        final long w = Math.min(w(), other.w());
        return v4(x, y, z, w);
    }

    @Override
    public Long distance2(Vector4I64 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Long length2() {
        return mul(this).sum();
    }

    @Override
    public Long dot(Vector4I64 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector4I64 clamp(Long min, Long max) {
        final long x = Math.clamp(x(), min, max);
        final long y = Math.clamp(y(), min, max);
        final long z = Math.clamp(z(), min, max);
        final long w = Math.clamp(w(), min, max);
        return v4(x, y, z, w);
    }

    @Override
    public boolean hasSameDirection(Vector4I64 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector4I64 vector) {
        return dot(vector) < 0;
    }
}
