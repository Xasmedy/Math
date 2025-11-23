package io.github.xasmedy.math;

import io.github.xasmedy.math.vector.Vector2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.Random;
import static io.github.xasmedy.math.vector.Vectors.v2;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public value class MathTest {

    @Test
    void testCollection() {

        final boolean[] results = new boolean[10_000_000];
        final boolean[] results2 = new boolean[10_000_000];
        for (int l = 0; l < 1000; l++) {
            for (int i = 0; i < results.length; i++) {

                final Vector2.F32 v2 = v2(i, i + 1f)
                        .add(v2(-i, (float) -i))
                        .mul(v2(-1, -2f));

                final float cross = v2.cross(v2.filled(3f));
                final float dot = v2.dot(v2.filled(4f));
                results[i] = cross < dot;

                results2[i] = v2.ceilAsInt().dot(v2(213, 123)) < 10;
            }
        }
        final int index = new Random().nextInt(0, results.length);
        System.out.println("Bool: " + results[index]);
        System.out.println("Bool: " + results2[index]);
    }
}
