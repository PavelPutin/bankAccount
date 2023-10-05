package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotationContextTest {
    private AnnotationContext context;
    @BeforeEach
    private void init() throws Exception {
        context = new AnnotationContext(this.getClass().getPackageName());
    }

    @Test
    public void injectTest() {
        Object sample1 = context.getComponentFactory().getComponent("SampleComponent1");
        SampleComponent2 sample2 = (SampleComponent2) context.getComponentFactory().getComponent("comp2");
        assertEquals(sample1, sample2.getSampleComponent1());
    }

    @Test
    public void multipleInjectTest() {
        Object sample1 = context.getComponentFactory().getComponent("SampleComponent1");
        SampleComponent2 sample2 = (SampleComponent2) context.getComponentFactory().getComponent("comp2");
        SampleComponent3 sample3 = (SampleComponent3) context.getComponentFactory().getComponent("comp3");
        assertAll(
                () -> assertEquals(sample1, sample3.getComp1()),
                () -> assertEquals(sample2, sample3.getComp2())
        );
    }
}