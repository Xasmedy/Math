package io.github.xasmedy.math.vector.abstracts;

import io.github.xasmedy.math.point.abstracts.Point1;

public interface Vector1<T extends Vector1<T, N>, N> extends Vector<T, N>, Point1<N> {

    @Override
    default int dimension() {
        return 1;
    }

    Vector2<?, N> withY(N y);

    // In 1D integers vector will always have an integer length.
    N length();

    // In 1D integers vector will always have an integer distance.
    N distance(T vector);
}
