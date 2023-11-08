package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;

@Component
public class AnnotationContext {
    private final ConfigurableListableComponentFactory componentFactory = new ConfigurableListableComponentFactory();
    private final String sourcePackageName;

    public AnnotationContext(String packageName) throws Exception {
        this.sourcePackageName = packageName;
        AnnotationComponentReader reader = new AnnotationComponentReader(this);
        int foundComponentsAmount = reader.loadComponentDefinitions(packageName);
        int defaultFoundComponentsAmount = reader.loadComponentDefinitions("edu.vsu.putinpa.infrastructure.di.defaultimpl");
        componentFactory.registryComponent("AnnotationContext", this);
        componentFactory.refreshComponents();
    }

    public void registryComponentDefinition(IComponentDefinition definition) {
        componentFactory.registryComponentDefinition(definition);
    }

    public <T> T getComponent(String name, Class<T> type) {
        return type.cast(componentFactory.getComponent(name));
    }

    public ConfigurableListableComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public String getSourcePackageName() {
        return sourcePackageName;
    }

    public Object getComponent(String name) {
        return componentFactory.getComponent(name);
    }
}
