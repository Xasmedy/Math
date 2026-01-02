package io.github.xasmedy.math.vector.v1;

import io.github.xasmedy.math.point.p1.Point1;
import io.github.xasmedy.math.vector.v2.Vector2I32;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

import static io.github.xasmedy.math.vector.Vectors.v1;

@LooselyConsistentValue
public value record Vector1I32(@NullRestricted Integer x
) implements Vector1<Vector1I32, Integer>, Vector1.Int<Vector1I32, Integer>, Point1.I32 {

    @Override
    public Vector1F32 asReal() {
        return v1((float) x());
    }

    @Override
    public Vector2I32 asV2(Integer y) {
        return new Vector2I32(x(), y);
    }

    @Override
    public Integer length() {
        return abs().x();
    }

    @Override
    public Integer distance(Vector1I32 vector) {
        final var delta = x() - vector.x();
        return Math.abs(delta);
    }

    @Override
    public Integer component(int index) throws IndexOutOfBoundsException {
        if (index != 0) throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        return x();
    }

    @Override
    public Integer sum() {
        return x();
    }

    @Override
    public Vector1I32 add(Vector1I32 other) {
        return v1(x() + other.x());
    }

    @Override
    public Vector1I32 sub(Vector1I32 other) {
        return v1(x() - other.x());
    }

    @Override
    public Vector1I32 mul(Vector1I32 other) {
        return v1(x() * other.x());
    }

    @Override
    public Vector1I32 mul(Integer scalar) {
        return v1(x() * scalar);
    }

    @Override
    public Vector1I32 div(Vector1I32 other) {
        return v1(x() / other.x());
    }

    @Override
    public boolean lt(Vector1I32 other) {
        return x() < other.x();
    }

    @Override
    public boolean ltEq(Vector1I32 other) {
        return x() <= other.x();
    }

    @Override
    public boolean gt(Vector1I32 other) {
        return x() > other.x();
    }

    @Override
    public boolean gtEq(Vector1I32 other) {
        return x() >= other.x();
    }

    @Override
    public Vector1I32 abs() {
        return v1(Math.abs(x()));
    }

    @Override
    public Vector1I32 max(Vector1I32 other) {
        return v1(Math.max(x(), other.x()));
    }

    @Override
    public Vector1I32 min(Vector1I32 other) {
        return v1(Math.min(x(), other.x()));
    }

    @Override
    public Integer distance2(Vector1I32 vector) {
        final var delta = x() - vector.x();
        return delta * delta;
    }

    @Override
    public Integer length2() {
        return x() * x();
    }

    @Override
    public Integer dot(Vector1I32 vector) {
        return x() * vector.x();
    }

    @Override
    public Vector1I32 clamp(Integer min, Integer max) {
        return v1(Math.clamp(x(), min, max));
    }

    @Override
    public boolean hasSameDirection(Vector1I32 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector1I32 vector) {
        return dot(vector) < 0;
    }
}
