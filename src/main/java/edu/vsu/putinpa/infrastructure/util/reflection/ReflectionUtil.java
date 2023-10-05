package edu.vsu.putinpa.infrastructure.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static List<Field> getAllDeclaredFieldsFromClassHierarchy(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllDeclaredFieldsFromClassHierarchy(clazz.getSuperclass()));
        Field[] fields = clazz.getDeclaredFields();
        result.addAll(Arrays.asList(fields));
        return result;
    }

    public static Class<?> forNameWithoutThrown(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
