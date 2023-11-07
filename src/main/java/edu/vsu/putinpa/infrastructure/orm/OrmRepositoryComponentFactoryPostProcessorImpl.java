package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.ComponentDefinition;
import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;
import edu.vsu.putinpa.infrastructure.orm.OrmRepository;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.forNameWithoutThrown;

public class OrmRepositoryComponentFactoryPostProcessorImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (IComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            String name = definition.getComponentName();
            Class<?> clazz = forNameWithoutThrown(definition.getComponentClassName());
            if (OrmRepository.class.isAssignableFrom(clazz) && clazz.isInterface()) {

            }
        }
    }
}
