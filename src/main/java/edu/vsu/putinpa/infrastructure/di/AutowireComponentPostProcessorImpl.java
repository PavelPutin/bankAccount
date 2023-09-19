package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.ComponentPostProcessor;

public class AutowireComponentPostProcessorImpl implements ComponentPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object component, String componentName) {
        return component;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, String componentName) {
        return component;
    }
}
