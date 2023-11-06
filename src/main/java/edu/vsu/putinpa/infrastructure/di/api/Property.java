package edu.vsu.putinpa.infrastructure.di.api;

import java.lang.annotation.*;

/**
 * Аннотация, связывающая поле с параметром из файла application.properties
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Property {
    String value();
}
