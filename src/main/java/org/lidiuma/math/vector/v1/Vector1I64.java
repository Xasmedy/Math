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

package org.lidiuma.math.vector.v1;

import org.lidiuma.math.point.p1.Point1;
import org.lidiuma.math.vector.v2.Vector2I64;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static org.lidiuma.math.vector.Vectors.v1;

@LooselyConsistentValue
public value record Vector1I64(@NullRestricted Long x
) implements Vector1<Vector1I64, Long>, Vector1.Int<Vector1I64, Long>, Point1.I64 {

    @Override
    public Vector1F64 asReal() {
        return new Vector1F64((double) x());
    }

    public Vector1I32 asI32() {
        return new Vector1I32((int) (long) x());
    }

    @Override
    public Vector2I64 asV2(Long y) {
        return new Vector2I64(x(), y);
    }

    @Override
    public Long length() {
        return abs().x();
    }

    @Override
    public Long distance(Vector1I64 vector) {
        final var delta = x() - vector.x();
        return Math.abs(delta);
    }

    @Override
    public Long component(int index) throws IndexOutOfBoundsException {
        if (index != 0) throw new IndexOutOfBoundsException("There's no component for index " + index + ".");
        return x();
    }

    @Override
    public Long sum() {
        return x();
    }

    @Override
    public Vector1I64 add(Vector1I64 other) {
        return v1(x() + other.x());
    }

    @Override
    public Vector1I64 sub(Vector1I64 other) {
        return v1(x() - other.x());
    }

    @Override
    public Vector1I64 mul(Vector1I64 other) {
        return v1(x() * other.x());
    }

    @Override
    public Vector1I64 mul(Long scalar) {
        return v1(x() * scalar);
    }

    @Override
    public Vector1I64 div(Vector1I64 other) {
        return v1(x() / other.x());
    }

    @Override
    public boolean lt(Vector1I64 other) {
        return x() < other.x();
    }

    @Override
    public boolean ltEq(Vector1I64 other) {
        return x() <= other.x();
    }

    @Override
    public boolean gt(Vector1I64 other) {
        return x() > other.x();
    }

    @Override
    public boolean gtEq(Vector1I64 other) {
        return x() >= other.x();
    }

    @Override
    public Vector1I64 abs() {
        return v1(Math.abs(x()));
    }

    @Override
    public Vector1I64 max(Vector1I64 other) {
        return v1(Math.max(x(), other.x()));
    }

    @Override
    public Vector1I64 min(Vector1I64 other) {
        return v1(Math.min(x(), other.x()));
    }

    @Override
    public Long distance2(Vector1I64 vector) {
        final var delta = x() - vector.x();
        return delta * delta;
    }

    @Override
    public Long length2() {
        return x() * x();
    }

    @Override
    public Long dot(Vector1I64 vector) {
        return x() * vector.x();
    }

    @Override
    public Vector1I64 clamp(Long min, Long max) {
        return v1(Math.clamp(x(), min, max));
    }

    @Override
    public boolean hasSameDirection(Vector1I64 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector1I64 vector) {
        return dot(vector) < 0;
    }
}
