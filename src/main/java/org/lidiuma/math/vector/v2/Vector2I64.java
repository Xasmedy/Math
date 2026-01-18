/*
 * Copyright (c) 2026 Xasmedy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lidiuma.math.vector.v2;

import org.lidiuma.math.point.p2.Point2;
import org.lidiuma.math.rotation.Radians;
import org.lidiuma.math.vector.Vector;
import org.lidiuma.math.vector.v1.Vector1I64;
import org.lidiuma.math.vector.v3.Vector3I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static org.lidiuma.math.vector.Vectors.v2;

@LooselyConsistentValue
public value record Vector2I64(@NullRestricted Long x,
                               @NullRestricted Long y
) implements Vector2<Vector2I64, Long>, Vector.Int<Vector2I64, Long>, Point2.I64 {

    @Override
    public Vector2F64 asReal() {
        return new Vector2F64((double) x(), (double) y());
    }

    public Vector2I32 asI32() {
        return new Vector2I32((int) (long) x(), (int) (long) y());
    }

    @Override
    public Vector1I64 asV1() {
        return new Vector1I64(x());
    }

    @Override
    public Vector3I64 asV3(Long z) {
        return new Vector3I64(x(), y(), z);
    }

    @Override
    public Vector2I64 rotate(Radians radians) {

        final double cos = Math.cos(radians.value());
        final double sin = Math.sin(radians.value());

        final long newX = (long) (x() * cos - y() * sin);
        final long newY = (long) (x() * sin + y() * cos);

        return v2(newX, newY);
    }

    @Override
    public Radians angle() {
        return Radians.radians(Math.atan2(y(), x()));
    }

    @Override
    public Long cross(Vector2I64 vector) {
        return x() * vector.y() - y() * vector.x();
    }

    @Override
    public Long component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            default -> throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        };
    }

    @Override
    public Long sum() {
        return x() + y();
    }

    @Override
    public Vector2I64 add(Vector2I64 other) {
        return v2(x() + other.x(), y() + other.y());
    }

    @Override
    public Vector2I64 sub(Vector2I64 other) {
        return v2(x() - other.x(), y() - other.y());
    }

    @Override
    public Vector2I64 mul(Vector2I64 other) {
        return v2(x() * other.x(), y() * other.y());
    }

    @Override
    public Vector2I64 mul(Long scalar) {
        return mul(v2(scalar, scalar));
    }

    @Override
    public Vector2I64 div(Vector2I64 other) {
        return v2(x() / other.x(), y() / other.y());
    }

    @Override
    public boolean lt(Vector2I64 other) {
        return x() < other.x() && y() < other.y();
    }

    @Override
    public boolean ltEq(Vector2I64 other) {
        return x() <= other.x() && y() <= other.y();
    }

    @Override
    public boolean gt(Vector2I64 other) {
        return x() > other.x() && y() > other.y();
    }

    @Override
    public boolean gtEq(Vector2I64 other) {
        return x() >= other.x() && y() >= other.y();
    }

    @Override
    public Vector2I64 abs() {
        return v2(Math.abs(x()), Math.abs(y()));
    }

    @Override
    public Vector2I64 max(Vector2I64 other) {
        return v2(Math.max(x(), other.x()), Math.max(y(), other.y()));
    }

    @Override
    public Vector2I64 min(Vector2I64 other) {
        return v2(Math.min(x(), other.x()), Math.min(y(), other.y()));
    }

    @Override
    public Long distance2(Vector2I64 vector) {
        final var delta = sub(vector);
        final var delta2 = delta.mul(delta);
        return delta2.sum();
    }

    @Override
    public Long length2() {
        return mul(this).sum();
    }

    @Override
    public Long dot(Vector2I64 vector) {
        return mul(vector).sum();
    }

    @Override
    public Vector2I64 clamp(Long min, Long max) {
        final long x = Math.clamp(x(), min, max);
        final long y = Math.clamp(y(), min, max);
        return v2(x, y);
    }

    @Override
    public boolean hasSameDirection(Vector2I64 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector2I64 vector) {
        return dot(vector) < 0;
    }
}
