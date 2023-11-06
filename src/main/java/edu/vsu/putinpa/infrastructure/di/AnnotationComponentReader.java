package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.InitMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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
                ComponentDefinition definition = new ComponentDefinition();
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

    private Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        Set<Class<?>> result = new HashSet<>();
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.endsWith(".class")) {
                    result.add(getClass(line, packageName));
                } else {
                    result.addAll(findAllClassesUsingClassLoader(packageName + "." + line));
                }
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
        return result;
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
