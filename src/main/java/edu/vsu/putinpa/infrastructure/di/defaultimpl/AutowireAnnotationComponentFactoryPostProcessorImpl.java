package edu.vsu.putinpa.infrastructure.di.defaultimpl;

import edu.vsu.putinpa.infrastructure.di.ComponentDefinition;
import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.Autowire;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AutowireAnnotationComponentFactoryPostProcessorImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (ComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            try {
                Class<?> clazz = Class.forName(definition.getComponentClassName());
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Autowire.class)) {
                        Class<?> fieldType = field.getType();
                        Set<String> candidates = getAllDefinitionNamesByClass(componentFactory, fieldType);
                        if (candidates.size() > 1) {
                            throw new RuntimeException("Too many autowire candidates TODO: make normal message");
                        }

                        if (candidates.size() == 0) {
                            throw new RuntimeException("No autowire candidates were found TODO: make normal message");
                        }

                        List<String> candidate = new ArrayList<>(candidates);
                        definition.addDependencyComponentName(candidate.get(0), field.getName());
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<String> getAllDefinitionNamesByClass(ConfigurableListableComponentFactory componentFactory, Class<?> clazz) {
        return componentFactory.getComponentDefinitions().stream()
                .filter(definition -> definition.getComponentClassName().equals(clazz.getName()))
                .map(ComponentDefinition::getComponentName)
                .collect(Collectors.toSet());
    }
}
