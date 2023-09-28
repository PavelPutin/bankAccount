package edu.vsu.putinpa.infrastructure.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    public static Object getValueWithGetter(Field field, Object obj) {
        String name = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        try {
            Method getter  = obj.getClass().getMethod(name);
            return getter.invoke(obj);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
