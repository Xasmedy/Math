package io.github.xasmedy.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.Random;
import static io.github.xasmedy.math.rotation.Radians.degrees;
import static io.github.xasmedy.math.vector.Vectors.*;

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
                // This inlines here and does not allocate.
                final var _4 = v3(31f, 312f, 3f).rotate(v3(0f, 1f, 0f), degrees(120f)).asInt().length2();

                final var _3 = i32Vector2() * _2.sum();
                results[i] = _3;
                results2[i] = _1.rotate(degrees(354)).ceil().asInt().dot(v2(213, 123)) < i64Vector2() + _4;
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

        // If I use v3.rotate() this method fails to inline properly.
        return _3.cross(_2) + _3.dot(_1);
    }

    @Test
    void testRotationV2() {

        final var start = v2(10f, 10f);

        final var end = start.rotate(degrees(180));
        final var full = start.rotate(degrees(360));

        Assertions.assertEquals(v2(-10f, -10f), end);
        Assertions.assertEquals(start, full);
    }

    @Test
    void testRotationV3() {

        final var start = v3(10f, 10f, 10f);

        final var endX = start.rotate(v3(1f, 0f, 0f), degrees(180));
        final var endY = start.rotate(v3(0f, 1f, 0f), degrees(180));
        final var endZ = start.rotate(v3(0f, 0f, 1f), degrees(180));
        final var full = start.rotate(v3(0f, 1f, 0f), degrees(360));

        Assertions.assertEquals(v3(10f, -10f, -10f), endX);
        Assertions.assertEquals(v3(-10f, 10f, -10f), endY);
        Assertions.assertEquals(v3(-10f, -10f, 10f), endZ);
        Assertions.assertEquals(v3(-10f, -10f, 10f), endZ);
        Assertions.assertEquals(v3(-10f, -10f, 10f), endZ);
        Assertions.assertEquals(start, full);
    }
}
