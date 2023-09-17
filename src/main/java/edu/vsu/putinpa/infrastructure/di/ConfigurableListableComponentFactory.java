package edu.vsu.putinpa.infrastructure.di;

import java.util.HashMap;
import java.util.Set;

public class ConfigurableListableComponentFactory {
    private final HashMap<String, ComponentDefinition> componentDefinitions = new HashMap<>();

    public void registryComponentDefinition(ComponentDefinition definition) {
        componentDefinitions.put(definition.getComponentName(), definition);
    }

    public Set<String> getComponentDefinitionNames() {
        return componentDefinitions.keySet();
    }

    public Set<ComponentDefinition> getComponentDefinitions() {
        return (Set<ComponentDefinition>) componentDefinitions.values();
    }
}
