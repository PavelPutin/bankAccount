package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.InitMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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
                OrmRepository<?, ?> repository = (OrmRepository<?, ?>) Proxy.newProxyInstance(OrmRepository.class.getClassLoader(),
                        new Class[] {OrmRepository.class},
                        new GetHandler());
                context.getComponentFactory().registryComponent(repository.getClass().getName(), repository);
            }
        }
    }

    static class GetHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().contains("find")) {
                System.out.println("get method");
            } else if (method.getName().contains("save")) {
                System.out.println("save method");
            } else {
                System.out.println("not get method");
            }
            return null;
        }
    }
}
