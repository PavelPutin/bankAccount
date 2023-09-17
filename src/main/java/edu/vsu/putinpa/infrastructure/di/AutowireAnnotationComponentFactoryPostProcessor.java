package edu.vsu.putinpa.infrastructure.di;

import java.util.HashSet;
import java.util.Set;

public class AutowireAnnotationComponentFactoryPostProcessor implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory factory) {
        for (ComponentDefinition definition : factory.getComponentDefinitions()) {
            try {
                Class<?> componentClass = Class.forName(definition.getComponentClassName());
                Set<String> autowireCandidateNames = new HashSet<>();
                for ()
            } catch (ClassNotFoundException e) {
                throw new ComponentNotFoundException(e);
            }
        }
    }
}
