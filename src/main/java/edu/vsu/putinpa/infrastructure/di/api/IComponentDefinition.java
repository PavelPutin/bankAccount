package edu.vsu.putinpa.infrastructure.di.api;

import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;

import java.util.Set;

public interface IComponentDefinition {
    String getComponentName();

    void setComponentName(String componentName);

    String getComponentClassName();

    void setComponentClassName(String componentClassName);

    Object getConstructorArgumentTypes();

    void setConstructorArgumentTypes(Class<?>[] constructorArgumentTypes);

    void setConstructorArgumentComponentNames(String[] constructorArgumentComponentNames);

    Set<String> getDependsOn();

    void addDependencyComponentName(String dependencyComponentName, String fieldName);

    String getInitMethodName();

    void setInitMethodName(String initMethodName);

    void createComponent(ConfigurableListableComponentFactory componentFactory) throws Exception;
}
