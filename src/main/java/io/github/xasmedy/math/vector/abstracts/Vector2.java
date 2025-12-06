package io.github.xasmedy.math.vector.abstracts;

import io.github.xasmedy.math.point.Point2;
import io.github.xasmedy.math.unit.Radians;

public interface Vector2<T extends Vector2<T, N>, N> extends Vector<T, N>, Point2<N> {

    @Override
    default int dimension() {
        return 2;
    }

    Vector1<?, N> withoutY();

    Vector3<?, N> withZ(N z);

    T rotate(Radians radians);

    Radians angle();

    N cross(T vector);
}
