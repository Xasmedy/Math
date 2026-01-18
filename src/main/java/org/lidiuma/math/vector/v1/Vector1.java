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
import org.lidiuma.math.vector.Vector;
import org.lidiuma.math.vector.v2.Vector2;

public interface Vector1<T extends Vector1<T, N>, N> extends Vector<T, N>, Point1<N> {

    @Override
    default int dimension() {
        return 1;
    }

    Vector2<?, N> asV2(N y);

    // In 1D integers vector will always have an integer length.
    N length();

    // In 1D integers vector will always have an integer distance.
    N distance(T vector);
}
