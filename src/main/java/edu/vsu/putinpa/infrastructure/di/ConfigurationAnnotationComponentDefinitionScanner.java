package edu.vsu.putinpa.infrastructure.di;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConfigurationAnnotationComponentDefinitionScanner {
    private final String configSource;

    public ConfigurationAnnotationComponentDefinitionScanner(String configSource) {
        this.configSource = configSource;
    }

    public void getComponentDefinitions() {
        Set<Class<?>> classes = findAllClassesUsingClassLoader(configSource);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(ComponentConfiguration.class)) {

            }
        }
    }

    private Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(toSet());
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
