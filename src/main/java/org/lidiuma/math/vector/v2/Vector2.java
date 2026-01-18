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
import org.lidiuma.math.vector.v3.Vector3;
import org.lidiuma.math.vector.v1.Vector1;

public interface Vector2<T extends Vector2<T, N>, N> extends Vector<T, N>, Point2<N> {

    @Override
    default int dimension() {
        return 2;
    }

    Vector1<?, N> asV1();

    Vector3<?, N> asV3(N z);

    T rotate(Radians radians);

    Radians angle();

    N cross(T vector);
}
