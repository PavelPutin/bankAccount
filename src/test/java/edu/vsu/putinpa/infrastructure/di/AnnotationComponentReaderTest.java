package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotationComponentReaderTest {
    private final String CURRENT_PACKAGE = this.getClass().getPackageName();
    private AnnotationContext context;
    private AnnotationComponentReader reader;
    @BeforeEach
    public void init() throws Exception {
        context = new AnnotationContext(CURRENT_PACKAGE);
        reader = new AnnotationComponentReader(context);
    }

    @Test
    public void readerTest() {
        int foundComponents = reader.loadComponentDefinitions(CURRENT_PACKAGE);
        assertEquals(3, foundComponents);
    }
}