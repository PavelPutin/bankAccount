package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;
import edu.vsu.putinpa.infrastructure.di.api.InitMethod;

import java.lang.reflect.Method;
import java.util.Set;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.findAllClassesUsingClassLoader;

public class AnnotationComponentReader {
    private AnnotationContext componentDefinitionRegistry;

    public AnnotationComponentReader(AnnotationContext componentDefinitionRegistry) {
        this.componentDefinitionRegistry = componentDefinitionRegistry;
    }

    public int loadComponentDefinitions(String componentsPackage) {
        int found = 0;
        Set<Class<?>> classes = findAllClassesUsingClassLoader(componentsPackage);
        for (Class<?> clazz : classes) {
            Component annotation = clazz.getAnnotation(Component.class);
            if (annotation != null) {
                IComponentDefinition definition = new ComponentDefinition();
                definition.setComponentClassName(clazz.getName());

                if (annotation.name().equals("")) {
                    definition.setComponentName(clazz.getSimpleName());
                } else {
                    definition.setComponentName(annotation.name());
                }

                String initMethodName = null;
                for (Method m : clazz.getMethods()) {
                    if (m.isAnnotationPresent(InitMethod.class)) {
                        if (initMethodName != null) {
                            throw new RuntimeException("Only one init method can exists. Error in component %s".formatted(definition.getComponentName()));
                        }
                        definition.setInitMethodName(m.getName());
                        initMethodName = m.getName();
                    }
                }

                componentDefinitionRegistry.registryComponentDefinition(definition);
                found++;
            }
        }
        return found;
    }
}
