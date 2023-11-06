package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.ComponentDefinition;
import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;

@Component
public class RepositoryCreatorPostProcessorComponentFactoryImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (ComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            if (definition.getComponentClassName().equals(RepositoriesCreator.class.getName())) {
                try {
                    definition.createComponent(componentFactory);
                } catch (Exception e) {
                    throw new RuntimeException("Can't create RepositoryCreator", e);
                }
            }
        }
    }
}
