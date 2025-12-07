package io.github.xasmedy.math.vector.abstracts;

import io.github.xasmedy.math.point.abstracts.Point4;

public interface Vector4<T extends Vector4<T, N>, N> extends Vector<T, N>, Point4<N> {

    @Override
    default int dimension() {
        return 4;
    }

    Vector3<?, N> withoutW();
}
