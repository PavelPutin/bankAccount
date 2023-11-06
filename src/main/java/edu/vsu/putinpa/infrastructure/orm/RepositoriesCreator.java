package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.InitMethod;

import java.util.Set;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.findAllClassesUsingClassLoader;

@Component
public class RepositoriesCreator {
    private OrmConnection connection;
    private AnnotationContext context;

    @AutoInjected
    public RepositoriesCreator(OrmConnection connection, AnnotationContext context) {
        this.connection = connection;
        this.context = context;
    }

    @InitMethod
    public void init() {
        Set<Class<?>> classes = findAllClassesUsingClassLoader(context.getSourcePackageName());
        for (Class<?> clazz : classes) {
            if (OrmRepository.class.isAssignableFrom(clazz) && clazz.isInterface() && !clazz.equals(OrmRepository.class)) {
                System.out.println(clazz.getName());
            }
        }
    }
}
