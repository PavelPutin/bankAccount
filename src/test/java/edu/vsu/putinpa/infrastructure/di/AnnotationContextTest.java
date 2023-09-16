package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationContextTest {
    private AnnotationContext context = new AnnotationContext();
    @BeforeEach
    public void initContext() {
        context = new AnnotationContext();
    }

    @Test
    public void containerTest() {
        assertEquals(5, context.getComponent("c1", Integer.class));
        assertEquals("abc", context.getComponent("c2", String.class));
    }
}