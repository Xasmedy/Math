package io.github.xasmedy.math.vector.v4;

import io.github.xasmedy.math.vector.abstracts.Vector;
import io.github.xasmedy.math.vector.abstracts.Vector4;
import io.github.xasmedy.math.vector.v3.Vector3I32;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.v4;

@LooselyConsistentValue
public value record Vector4I32(@NullRestricted Integer x,
                               @NullRestricted Integer y,
                               @NullRestricted Integer z,
                               @NullRestricted Integer w
) implements Vector4<Vector4I32, Integer>, Vector.Int<Vector4I32, Integer> {

    @Override
    public Vector4F32 asReal() {
        return new Vector4F32((float) x(), (float) y(), (float) z(), (float) w());
    }

    @Override
    public Vector3I32 withoutW() {
        return new Vector3I32(x(), y(), z());
    }

    @Override
    public Integer component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            case 3 -> w();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Integer sum() {
        return x() + y() + z() + w();
    }

    @Override
    public Vector4I32 add(Vector4I32 other) {
        final int x = x() + other.x();
        final int y = y() + other.y();
        final int z = z() + other.z();
        final int w = w() + other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I32 sub(Vector4I32 other) {
        final int x = x() - other.x();
        final int y = y() - other.y();
        final int z = z() - other.z();
        final int w = w() - other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I32 mul(Vector4I32 other) {
        final int x = x() * other.x();
        final int y = y() * other.y();
        final int z = z() * other.z();
        final int w = w() * other.w();
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I32 mul(Integer scalar) {
        return mul(v4(scalar, scalar, scalar, scalar));
    }

    @Override
    public Vector4I32 div(Vector4I32 other) {
        final int x = x() / other.x();
        final int y = y() / other.y();
        final int z = z() / other.z();
        final int w = w() / other.w();
        return v4(x, y, z, w);
    }

    @Override
    public boolean lt(Vector4I32 other) {
        return x() < other.x() && y() < other.y() &&
                z() < other.z() && w() < other.w();
    }

    @Override
    public boolean ltEq(Vector4I32 other) {
        return x() <= other.x() && y() <= other.y() &&
                z() <= other.z() && w() <= other.w();
    }

    @Override
    public boolean gt(Vector4I32 other) {
        return x() > other.x() && y() > other.y() &&
                z() > other.z() && w() > other.w();
    }

    @Override
    public boolean gtEq(Vector4I32 other) {
        return x() >= other.x() && y() >= other.y() &&
                z() >= other.z() && w() >= other.w();
    }

    @Override
    public Vector4I32 abs() {
        final int x = Math.abs(x());
        final int y = Math.abs(y());
        final int z = Math.abs(z());
        final int w = Math.abs(w());
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I32 max(Vector4I32 other) {
        final int x = Math.max(x(), other.x());
        final int y = Math.max(y(), other.y());
        final int z = Math.max(z(), other.z());
        final int w = Math.max(w(), other.w());
        return v4(x, y, z, w);
    }

    @Override
    public Vector4I32 min(Vector4I32 other) {
        final int x = Math.min(x(), other.x());
        final int y = Math.min(y(), other.y());
        final int z = Math.min(z(), other.z());
        final int w = Math.min(w(), other.w());
        return v4(x, y, z, w);
    }

    @Override
    public Integer distance2(Vector4I32 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Integer length2() {
        return mul(this).sum();
    }

    @Override
    public Integer dot(Vector4I32 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector4I32 clamp(Integer min, Integer max) {
        final int x = Math.clamp(x(), min, max);
        final int y = Math.clamp(y(), min, max);
        final int z = Math.clamp(z(), min, max);
        final int w = Math.clamp(w(), min, max);
        return v4(x, y, z, w);
    }

    @Override
    public boolean hasSameDirection(Vector4I32 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector4I32 vector) {
        return dot(vector) < 0;
    }
}
