package edu.vsu.putinpa.infrastructure.di.api;

public interface ComponentPostProcessor {
    Object postProcessBeforeInitialization(Object component, String componentName);
    Object postProcessAfterInitialization(Object component, String componentName);
}
