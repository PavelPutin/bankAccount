package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;

import java.util.*;
import java.util.stream.Collectors;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.forNameWithoutThrown;

public class ConfigurableListableComponentFactory {
    private final Map<String, IComponentDefinition> componentDefinitions = new HashMap<>();
    private final List<ComponentFactoryPostProcessor> componentFactoryPostProcessors = new ArrayList<>();
    private final Map<String, Object> components = new HashMap<>();

    public void registryComponentDefinition(IComponentDefinition definition) {
        componentDefinitions.put(definition.getComponentName(), definition);
    }

    public Set<String> getComponentDefinitionNames() {
        return componentDefinitions.keySet();
    }

    public Set<IComponentDefinition> getComponentDefinitions() {
        return new HashSet<>(componentDefinitions.values());
    }

    public IComponentDefinition getComponentDefinition(String name) {
        return componentDefinitions.get(name);
    }

    public void refreshComponents() throws Exception {
        registryComponentFactoryPostProcessors();
        for (ComponentFactoryPostProcessor postProcessor : componentFactoryPostProcessors) {
            postProcessor.postProcessComponentFactory(this);
        }

        for (IComponentDefinition definition : componentDefinitions.values()) {
            if (!components.containsKey(definition.getComponentName())) {
                definition.createComponent(this);
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
        for (IComponentDefinition definition : getComponentDefinitions()) {
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
                .filter(definition -> forNameWithoutThrown(definition.getComponentClassName()).isAssignableFrom(clazz))
                .map(IComponentDefinition::getComponentName)
                .collect(Collectors.toSet());
    }
}
