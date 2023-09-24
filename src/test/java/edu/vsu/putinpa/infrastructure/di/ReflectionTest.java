package edu.vsu.putinpa.infrastructure.di;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {
    @Test
    public void classNameTest() {
        String a = "a";
        Class<?> aClass = a.getClass();
        assertEquals(aClass.getName(), "java.lang.String");
    }
}
