package edu.vsu.putinpa.infrastructure.di;

import java.util.HashSet;
import java.util.Set;

public class ComponentDefinition {
    private String componentName;
    private String componentClassName;
    private Object constructorArgumentValues;
    private final Set<String> dependsOn = new HashSet<>();

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
        return dependsOn;
    }

    public void addDependencyComponentName(String dependencyComponentName) {
        this.dependsOn.add(dependencyComponentName);
    }
}
