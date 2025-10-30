package io.github.xasmedy.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilTest {
    @Test
    void verifyHello() {
        assertEquals("Hello World!", new MathUtil().getMessage());
    }
}
