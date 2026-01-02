package io.github.xasmedy.math.vector.v2;

import io.github.xasmedy.math.point.p2.Point2;
import io.github.xasmedy.math.unit.Radians;
import io.github.xasmedy.math.vector.Vector;
import io.github.xasmedy.math.vector.v3.Vector3;
import io.github.xasmedy.math.vector.v1.Vector1;

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
