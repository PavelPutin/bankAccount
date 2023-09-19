package edu.vsu.putinpa.infrastructure.di;

import java.util.HashMap;

public class AnnotationContext {
    private final ConfigurableListableComponentFactory componentFactory = new ConfigurableListableComponentFactory();

    public AnnotationContext(String packageName) throws Exception {
        AnnotationComponentReader reader = new AnnotationComponentReader(this);
        int foundComponentsAmount = reader.loadComponentDefinitions(packageName);
        int defaultFoundComponentsAmount = reader.loadComponentDefinitions("edu.vsu.putinpa.infrastructure.di.defaultimpl");
        componentFactory.refreshComponents();
    }

    public void registryComponentDefinition(ComponentDefinition definition) {
        componentFactory.registryComponentDefinition(definition);
    }

    public ConfigurableListableComponentFactory getComponentFactory() {
        return componentFactory;
    }
}
