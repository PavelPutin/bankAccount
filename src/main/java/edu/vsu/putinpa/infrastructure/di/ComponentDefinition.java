package edu.vsu.putinpa.infrastructure.di;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentDefinition {
    private String componentName;
    private String componentClassName;
    private Object constructorArgumentValues;
    private final Map<String, String> dependsOnAndFieldName = new HashMap<>();

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentClassName() {
        return componentClassName;
    }

    public void setComponentClassName(String componentClassName) {
        this.componentClassName = componentClassName;
    }

    public Object getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(Object constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public Set<String> getDependsOn() {
        return dependsOnAndFieldName.keySet();
    }

    public void addDependencyComponentName(String dependencyComponentName, String fieldName) {
        this.dependsOnAndFieldName.put(dependencyComponentName, fieldName);
    }

    public void createComponent(ConfigurableListableComponentFactory componentFactory) throws Exception {
        Class<?> componentClass = Class.forName(componentClassName);
        Object instance = componentClass.getConstructor().newInstance();
        for (Map.Entry<String, String> dependency : dependsOnAndFieldName.entrySet()) {
            if (componentFactory.getComponent(dependency.getKey()) == null) {
                ComponentDefinition dependencyDefinition = componentFactory.getComponentDefinition(dependency.getKey());
                dependencyDefinition.createComponent(componentFactory);
            }

            Object instanceDependency = componentFactory.getComponent(dependency.getKey());
            Field injectTarget = componentClass.getDeclaredField(dependency.getValue());
            injectTarget.setAccessible(true);
            injectTarget.set(instance, instanceDependency);
        }
        componentFactory.registryComponent(componentName, instance);
    }
}
