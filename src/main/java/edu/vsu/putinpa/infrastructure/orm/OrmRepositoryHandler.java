package edu.vsu.putinpa.infrastructure.orm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OrmRepositoryHandler implements InvocationHandler {
    private OrmConnectionWrapper ormConnectionWrapper;
    private Class<?> entityClass;
    private Class<?> entityIdClass;

    public OrmRepositoryHandler(OrmConnectionWrapper ormConnectionWrapper, Class<?> entityClass, Class<?> entityIdClass) {
        this.ormConnectionWrapper = ormConnectionWrapper;
        this.entityClass = entityClass;
        this.entityIdClass = entityIdClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("work with " + ormConnectionWrapper.getUrl());
        if (method.getName().startsWith("find")) {
            System.out.println("find method");
        } else if (method.getName().startsWith("save")) {
            System.out.println("save method");
        } else {
            System.out.println("unknown method type: " + method.getName());
        }
        return null;
    }
}
