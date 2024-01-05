package edu.vsu.putinpa.infrastructure.di.defaultimpl;

import edu.vsu.putinpa.infrastructure.di.Environment;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.ComponentPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.Property;

import java.lang.reflect.Field;

@Component
public class PropertyAnnotationComponentPostProcessorImpl implements ComponentPostProcessor {

    private Environment environment;

    @AutoInjected
    public PropertyAnnotationComponentPostProcessorImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Object postProcessBeforeInitialization(Object component, String componentName) {
        Class<?> clazz = component.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Property property = field.getAnnotation(Property.class);
            if (property != null) {
                String value = property.value();
                if (value.startsWith("{") && value.endsWith("}")) {
                    String propertyName = value.substring(1, value.length() - 1);
                    value = environment.getProperty(propertyName);
                }
                field.setAccessible(true);
                try {
                    field.set(component, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return component;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, String componentName) {
        return null;
    }
}
