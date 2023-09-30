package edu.vsu.putinpa.infrastructure.util.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.*;

class ReflectionUtilTest {

    class A {
        private Object f1;
    }

    class B extends A{
        private Object f2;
    }

    @Test
    void getAllDeclaredFieldsFromClassHierarchyTest() {
        List<Field> fields = getAllDeclaredFieldsFromClassHierarchy(B.class);
        Set<String> actual = fields.stream().map(Field::getName).collect(Collectors.toSet());
        assertEquals(Set.of("f1", "f2"), actual);
    }
}