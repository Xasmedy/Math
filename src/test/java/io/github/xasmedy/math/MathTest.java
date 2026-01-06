package io.github.xasmedy.math;

import io.github.xasmedy.math.rotation.Radians;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.Random;
import static io.github.xasmedy.math.vector.Vectors.v2;
import static io.github.xasmedy.math.vector.Vectors.v3;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public value class MathTest {

    public static final Random RAN = new Random();

    @Test
    void testCollection() {

        final int[] results = new int[10_000_000];
        final boolean[] results2 = new boolean[10_000_000];
        for (int l = 0; l < 1000; l++) {
            for (int i = 0; i < results.length; i++) {

                final var _1 = v2(RAN.nextFloat(), RAN.nextFloat());
                final var _2 = v2(RAN.nextInt(), RAN.nextInt());

                final var _3 = i32Vector2() * _2.sum();
                results[i] = _3;
                results2[i] = _1.rotate(Radians.degrees(354)).ceil().asInt().dot(v2(213, 123)) < i64Vector2();
            }
        }
        final int index = new Random().nextInt(0, results.length);
        System.out.println("Bool: " + results[index]);
        System.out.println("Bool: " + results2[index]);
    }

    static int i32Vector2() {

        final var _1 = v2(RAN.nextInt(), RAN.nextInt());
        final var _2 = v2(RAN.nextInt(), RAN.nextInt());

        final var _3 = _1.add(_2)
                .mul(v2(RAN.nextInt(), RAN.nextInt()))
                .sub(_2)
                .sub(_2)
                .div(_1.min(v2(1, 1)));

        return _3.cross(_2) + _3.dot(_1);
    }

    static long i64Vector2() {

        final var _1 = v2(RAN.nextLong(), RAN.nextLong());
        final var _2 = v2(RAN.nextLong(), RAN.nextLong());

        final var _3 = _1.add(_2)
                .mul(_1.max(v2(RAN.nextLong(), RAN.nextLong())))
                .sub(_2)
                .div(_1.min(v2(1L, 1L)))
                .mul(RAN.nextLong(10));

        return _3.cross(_2) + _3.dot(_1) + v3(31f, 312f, 3f).rotate(v3(0f, 1f, 0f), Radians.degrees(120f)).asInt().length2();
    }
}
