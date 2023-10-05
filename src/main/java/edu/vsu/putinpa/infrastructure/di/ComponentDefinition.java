package edu.vsu.putinpa.infrastructure.di;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentDefinition {
    private String componentName;
    private String componentClassName;
    private Class<?>[] constructorArgumentTypes;
    private String[] constructorArgumentComponentNames;
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

    public Object getConstructorArgumentTypes() {
        return constructorArgumentTypes;
    }

    public void setConstructorArgumentTypes(Class<?>[] constructorArgumentTypes) {
        this.constructorArgumentTypes = constructorArgumentTypes;
    }

    public void setConstructorArgumentComponentNames(String[] constructorArgumentComponentNames) {
        this.constructorArgumentComponentNames = constructorArgumentComponentNames;
    }

    public Set<String> getDependsOn() {
        return dependsOnAndFieldName.keySet();
    }

    public void addDependencyComponentName(String dependencyComponentName, String fieldName) {
        this.dependsOnAndFieldName.put(dependencyComponentName, fieldName);
    }

    public void createComponent(ConfigurableListableComponentFactory componentFactory) throws Exception {
        Class<?> componentClass = Class.forName(componentClassName);

        Object[] initialArguments = new Object[constructorArgumentComponentNames.length];
        for (int i = 0; i < constructorArgumentComponentNames.length; i++) {
            String name = constructorArgumentComponentNames[i];
            if (componentFactory.getComponent(name) == null) {
                componentFactory.getComponentDefinition(name).createComponent(componentFactory);
            }
            initialArguments[i] = componentFactory.getComponent(name);
        }

        Object instance = componentClass.getConstructor(constructorArgumentTypes).newInstance(initialArguments);
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
