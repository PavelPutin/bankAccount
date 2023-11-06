package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.ComponentPostProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurableListableComponentFactory {
    private final Map<String, ComponentDefinition> componentDefinitions = new HashMap<>();
    private final List<ComponentFactoryPostProcessor> componentFactoryPostProcessors = new ArrayList<>();
    private final List<ComponentPostProcessor> componentPostProcessors = new ArrayList<>();
    private final Map<String, Object> components = new HashMap<>();

    public void registryComponentDefinition(ComponentDefinition definition) {
        componentDefinitions.put(definition.getComponentName(), definition);
    }

    public Set<String> getComponentDefinitionNames() {
        return componentDefinitions.keySet();
    }

    public Set<ComponentDefinition> getComponentDefinitions() {
        return new HashSet<>(componentDefinitions.values());
    }

    public ComponentDefinition getComponentDefinition(String name) {
        return componentDefinitions.get(name);
    }

    public void refreshComponents() throws Exception {
        registryComponentFactoryPostProcessors();
        for (ComponentFactoryPostProcessor postProcessor : componentFactoryPostProcessors) {
            postProcessor.postProcessComponentFactory(this);
        }

        for (ComponentDefinition definition : componentDefinitions.values()) {
            if (!components.containsKey(definition.getComponentName())) {
                definition.createComponent(this);
            }
            Object component = components.get(definition.getComponentName());
            if (Arrays.asList(component.getClass().getInterfaces()).contains(ComponentPostProcessor.class)) {
                componentPostProcessors.add((ComponentPostProcessor) component);
            }
        }

        for (ComponentDefinition definition : componentDefinitions.values()) {
            Object component = components.get(definition.getComponentName());
            for (ComponentPostProcessor postProcessor : componentPostProcessors) {
                postProcessor.postProcessBeforeInitialization(component, definition.getComponentName());
            }
            if (definition.getInitMethodName() != null) {
                component.getClass().getMethod(definition.getInitMethodName()).invoke(component);
            }
        }
    }

    public Object getComponent(String name) {
        return components.get(name);
    }

    public void registryComponent(String name, Object component) {
        components.put(name, component);
    }

    private void registryComponentFactoryPostProcessors() {
        for (ComponentDefinition definition : getComponentDefinitions()) {
            try {
                Class<?> clazz = Class.forName(definition.getComponentClassName());
                if (Arrays.asList(clazz.getInterfaces()).contains(ComponentFactoryPostProcessor.class)) {
                    ComponentFactoryPostProcessor component = (ComponentFactoryPostProcessor) clazz.getConstructor().newInstance();
                    componentFactoryPostProcessors.add(component);
                }
            } catch (Exception e) {
                // TODO: обработать остальные exception (XD как будто это кто-то будет делать)
                throw new RuntimeException(e);
            }
        }
    }

    public Set<String> getAllDefinitionNamesByClass(Class<?> clazz) {
        return this.getComponentDefinitions().stream()
                .filter(definition -> definition.getComponentClassName().equals(clazz.getName()))
                .map(ComponentDefinition::getComponentName)
                .collect(Collectors.toSet());
    }
}
