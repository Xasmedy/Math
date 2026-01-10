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

import org.jspecify.annotations.NullMarked;

@NullMarked // Makes the whole codebase non-null by default.
module xasmedy.math {
    requires org.jspecify;
    exports io.github.xasmedy.math.rotation;
    exports io.github.xasmedy.math.matrix;

    exports io.github.xasmedy.math.point;
    exports io.github.xasmedy.math.point.p1;
    exports io.github.xasmedy.math.point.p2;
    exports io.github.xasmedy.math.point.p3;
    exports io.github.xasmedy.math.point.p4;

    exports io.github.xasmedy.math.shape;

    exports io.github.xasmedy.math.vector;
    exports io.github.xasmedy.math.vector.v1;
    exports io.github.xasmedy.math.vector.v2;
    exports io.github.xasmedy.math.vector.v3;
    exports io.github.xasmedy.math.vector.v4;
}