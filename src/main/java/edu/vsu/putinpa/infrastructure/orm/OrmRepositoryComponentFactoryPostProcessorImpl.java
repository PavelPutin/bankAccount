package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.forNameWithoutThrown;

@Component
public class OrmRepositoryComponentFactoryPostProcessorImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (IComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            Class<?> defClass = forNameWithoutThrown(definition.getComponentClassName());
            if (OrmRepository.class.isAssignableFrom(defClass) && defClass.isInterface()) {
                System.out.println(defClass);
            }
        }
    }
}
