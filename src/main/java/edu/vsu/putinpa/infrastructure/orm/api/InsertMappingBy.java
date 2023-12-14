package edu.vsu.putinpa.infrastructure.orm.api;

import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.function.Function;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertMappingBy {
    Class<? extends Function<?, InsertMappingInfo>> value();
}
