package io.github.xasmedy.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static io.github.xasmedy.math.rotation.Radians.degrees;
import static io.github.xasmedy.math.vector.Vectors.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public value class MathTest {

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
