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
        Sample1 sample1 = context.getComponent("SampleComponent1", Sample1.class);
        Sample2 sample2 = context.getComponent("comp2", Sample2.class);
        assertEquals(sample1, sample2.getSampleComponent1());
    }

    @Test
    public void multipleInjectTest() {
        Sample1 sample1 = context.getComponent("SampleComponent1", Sample1.class);
        Sample2 sample2 = context.getComponent("comp2", Sample2.class);
        SampleComponent3 sample3 = context.getComponent("comp3", SampleComponent3.class);
        assertAll(
                () -> assertEquals(sample1, sample3.getComp1()),
                () -> assertEquals(sample2, sample3.getComp2())
        );
    }
}