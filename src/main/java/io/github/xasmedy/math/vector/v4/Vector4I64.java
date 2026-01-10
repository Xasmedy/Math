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

package io.github.xasmedy.math.vector.v4;

import io.github.xasmedy.math.point.p4.Point4;
import io.github.xasmedy.math.vector.Vector;
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

    public Vector4I32 asI32() {
        return new Vector4I32((int) (long) x(), (int) (long) y(), (int) (long) z(), (int) (long) w());
    }

    @Override
    public Vector3I64 asV3() {
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
