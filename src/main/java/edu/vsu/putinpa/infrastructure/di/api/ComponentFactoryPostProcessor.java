package edu.vsu.putinpa.infrastructure.di.api;

import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;

public interface ComponentFactoryPostProcessor {
    void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory);
}
