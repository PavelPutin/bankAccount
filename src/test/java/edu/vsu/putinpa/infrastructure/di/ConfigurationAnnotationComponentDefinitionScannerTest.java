package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationAnnotationComponentDefinitionScannerTest {
    private ConfigurationAnnotationComponentDefinitionScanner scanner;
    @BeforeEach
    public void initContext() {
        scanner = new ConfigurationAnnotationComponentDefinitionScanner("edu.vsu.putinpa.infrastructure.di");
    }
}