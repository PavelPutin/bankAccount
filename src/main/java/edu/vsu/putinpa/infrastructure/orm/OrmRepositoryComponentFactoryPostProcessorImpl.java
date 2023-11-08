package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.ConfigurableListableComponentFactory;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.ComponentFactoryPostProcessor;
import edu.vsu.putinpa.infrastructure.di.api.IComponentDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.forNameWithoutThrown;

@Component
public class OrmRepositoryComponentFactoryPostProcessorImpl implements ComponentFactoryPostProcessor {
    @Override
    public void postProcessComponentFactory(ConfigurableListableComponentFactory componentFactory) {
        for (IComponentDefinition definition : componentFactory.getComponentDefinitions()) {
            Class<?> componentClass = forNameWithoutThrown(definition.getComponentClassName());
            if (OrmRepository.class.isAssignableFrom(componentClass) && componentClass.isInterface()) {
                ClassLoader loader = definition.getClass().getClassLoader();
                Class<?>[] componentDefinitionInterface = {IComponentDefinition.class};
                InvocationHandler repHandler = new RepositoryComponentDefinitionHandler(definition);

                IComponentDefinition proxy = (IComponentDefinition) Proxy.newProxyInstance(
                        loader, componentDefinitionInterface, repHandler
                );

                componentFactory.registryComponentDefinition(proxy);
            }
        }
    }

    static class RepositoryComponentDefinitionHandler implements InvocationHandler {
        private IComponentDefinition originComponentDefinition;

        public RepositoryComponentDefinitionHandler(IComponentDefinition originComponentDefinition) {
            this.originComponentDefinition = originComponentDefinition;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("createComponent")) {
                ConfigurableListableComponentFactory factory = (ConfigurableListableComponentFactory) args[0];

                Class<?> componentClass = forNameWithoutThrown(originComponentDefinition.getComponentClassName());
                ClassLoader loader = componentClass.getClassLoader();
                Class<?>[] repositoryInterface = {componentClass};

                if (factory.getComponent("orm$OrmConnectionWrapper") == null) {
                    IComponentDefinition connectionDefinition = factory.getComponentDefinition("orm$OrmConnectionWrapper");
                    connectionDefinition.createComponent(factory);
                }
                OrmConnectionWrapper connectionWrapper = factory.getComponent("orm$OrmConnectionWrapper", OrmConnectionWrapper.class);

                InvocationHandler handler = new OrmRepositoryHandler(connectionWrapper);

                Object repository = Proxy.newProxyInstance(loader, repositoryInterface, handler);

                String name = originComponentDefinition.getComponentName();
                factory.registryComponent(name, repository);

                return null;
            } else {
                return method.invoke(originComponentDefinition, args);
            }
        }
    }
}
