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
