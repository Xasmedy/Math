package io.github.xasmedy.math.vector.v4;

import io.github.xasmedy.math.point.p4.Point4;
import io.github.xasmedy.math.vector.Vector;
import io.github.xasmedy.math.vector.v3.Vector3;

public interface Vector4<T extends Vector4<T, N>, N> extends Vector<T, N>, Point4<N> {

    @Override
    default int dimension() {
        return 4;
    }

    Vector3<?, N> withoutW();
}
