package edu.vsu.putinpa.infrastructure.di.defaultimpl;

import edu.vsu.putinpa.infrastructure.di.ComponentDefinition;
import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.forNameWithoutThrown;

@Component
public class AutoInjectAnnotationComponentFactoryPostProcessorImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (IComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            Class<?> clazz = forNameWithoutThrown(definition.getComponentClassName());
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(AutoInjected.class))
                    continue;

                Class<?> fieldType = field.getType();
                Set<String> candidates = componentFactory.getAllDefinitionNamesByClass(fieldType);
                if (candidates.size() > 1)
                    throw new RuntimeException("Too many autowire candidates TODO: make normal message");

                if (candidates.isEmpty())
                    throw new RuntimeException("No autowire candidates were found TODO: make normal message");

                List<String> candidate = new ArrayList<>(candidates);
                definition.addDependencyComponentName(candidate.get(0), field.getName());
            }

            List<String> componentNames = new ArrayList<>();
            int autoInjectFoundConstructors = 0;
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (!constructor.isAnnotationPresent(AutoInjected.class))
                    continue;

                if (++autoInjectFoundConstructors > 1)
                    throw new RuntimeException("Too many auto inject constructors");

                Class<?>[] paramTypes = constructor.getParameterTypes();
                definition.setConstructorArgumentTypes(paramTypes);
                Annotation[][] paramsAnnotations = constructor.getParameterAnnotations();

                for (int i = 0; i < constructor.getParameterCount(); i++) {
                    Class<?> paramClass = paramTypes[i];
                    Annotation[] paramAnnotation = paramsAnnotations[i];

                    String componentName = null;
                    for (Annotation a : paramAnnotation) {
                        if (a instanceof Qualify qualify) {
                            componentName = qualify.value();
                        }
                    }

                    if (componentName == null) {
                        for (IComponentDefinition componentDefinition : componentFactory.getComponentDefinitions()) {
                            Class<?> componentClass = forNameWithoutThrown(componentDefinition.getComponentClassName());
                            if (paramClass.isAssignableFrom(componentClass)) {
                                if (componentName != null)
                                    throw new RuntimeException("Too many autowire candidates TODO: make normal message");

                                componentName = componentDefinition.getComponentName();
                            }
                        }
                    }

                    if (componentName == null)
                        throw new RuntimeException("No autowire candidates were found TODO: make normal message");

                    // Если имя не найдено в objects - ошибка
                    componentNames.add(componentName);
                }
            }
            definition.setConstructorArgumentComponentNames(componentNames.toArray(new String[0]));
        }
    }
}
