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

package io.github.xasmedy.math.vector.v3;

import io.github.xasmedy.math.point.p3.Point3;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.Vector;
import io.github.xasmedy.math.vector.v4.Vector4;
import io.github.xasmedy.math.vector.v2.Vector2;

public interface Vector3<T extends Vector3<T, N>, N> extends Vector<T, N>, Point3<N> {

    @Override
    default int dimension() {
        return 3;
    }

    Vector2<?, N> asV2();

    Vector4<?, N> asV4(N w);

    T cross(T vector);

    T rotate(T axis, Radians angle);
}
