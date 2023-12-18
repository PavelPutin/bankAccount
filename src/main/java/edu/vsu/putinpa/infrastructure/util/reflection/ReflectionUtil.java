package edu.vsu.putinpa.infrastructure.util.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.lang.model.util.Types;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;

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

    public static List<Field> getAllDeclaredNonStaticFieldsFromClassHierarchy(Class<?> clazz) {
        return getAllDeclaredFieldsFromClassHierarchy(clazz).stream()
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .toList();
    }

    public static Class<?> forNameWithoutThrown(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream;
        try {
            stream = Thread.currentThread().getContextClassLoader()
                    .getResources(packageName.replaceAll("[.]", "/"))
                    .nextElement().openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        ClassLoader.getSystemClassloader().getResourceAsStream("edu/vsu/putinpa")
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
//        var config = new ConfigurationBuilder()
//                .forPackages(packageName)
//                .setScanners(new SubTypesScanner())
//                .filterInputsBy(new FilterBuilder().includePackage(packageName));
//
//        var reflections = new Reflections(config);
//        return reflections.getSubTypesOf(Object.class);
    }

    public static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] getGenericTypesFromOneImplementedInterface(Class<?> target) {
        Type type = target.getGenericInterfaces()[0];
        String interfaceName = type.getTypeName();
        String[] splited = interfaceName.substring(interfaceName.indexOf('<') + 1, interfaceName.indexOf('>')).split(", ");
        Class<?>[] result = new Class[splited.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = forNameWithoutThrown(splited[i]);
        }
        return result;
    }

    public static <T> Constructor<T> getCanonicalConstructor(Class<T> cls)
            throws NoSuchMethodException {
        Class<?>[] paramTypes =
                Arrays.stream(cls.getRecordComponents())
                        .map(RecordComponent::getType)
                        .toArray(Class<?>[]::new);
        return cls.getDeclaredConstructor(paramTypes);
    }
}
