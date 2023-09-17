package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationContextTest {
    private AnnotationContext context;
    @BeforeEach
    private void init() {
        context = new AnnotationContext(this.getClass().getPackageName());
    }

    @Test
    public void componentDefinitionNamesTest() {
        Set<String> actualNames = context.getComponentFactory().getComponentDefinitionNames();
        Set<String> expected = Set.of("SampleComponent1", "comp2");
        assertEquals(expected, actualNames);
    }
}