package io.github.xasmedy.math.shape;

public interface Shape {

    interface Line<P> {

        P start();

        P end();
    }

    interface Sphere<T, P> {

        P center();

        T radius();
    }

    interface Rectangle<T, P> {

        P pos();

        T width();

        T height();
    }

    interface Cuboid<T, P> {

        P pos();

        T width();

        T height();

        T length();
    }
}
