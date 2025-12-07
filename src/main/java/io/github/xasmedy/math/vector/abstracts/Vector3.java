package io.github.xasmedy.math.vector.abstracts;

import io.github.xasmedy.math.point.abstracts.Point3;

public interface Vector3<T extends Vector3<T, N>, N> extends Vector<T, N>, Point3<N> {

    // TODO I need to implement Quaternion for rotation..

    @Override
    default int dimension() {
        return 3;
    }

    Vector2<?, N> withoutZ();

    Vector4<?, N> withW(N w);

    T cross(T vector);
}
