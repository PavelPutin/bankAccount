package edu.vsu.putinpa.infrastructure.di;

public class ComponentDefinition {
    private String componentName;
    private String componentClassName;
    private Object constructorArgumentValues;
    private String[] dependsOn;

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

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }
}
