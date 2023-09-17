package edu.vsu.putinpa.infrastructure.di;

public interface ComponentFactoryPostProcessor {
    void postProcessComponentFactory(ConfigurableListableComponentFactory factory);
}
