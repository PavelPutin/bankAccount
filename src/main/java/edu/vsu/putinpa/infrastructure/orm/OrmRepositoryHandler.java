package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.orm.api.Column;
import edu.vsu.putinpa.infrastructure.orm.api.JoinColumn;
import edu.vsu.putinpa.infrastructure.orm.api.ManyToOne;
import edu.vsu.putinpa.infrastructure.orm.api.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getAllDeclaredFieldsFromClassHierarchy;
import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getAllDeclaredNonStaticFieldsFromClassHierarchy;

public class OrmRepositoryHandler implements InvocationHandler {
    private OrmConnectionWrapper ormConnectionWrapper;
    private Class<?> entityClass;
    private Class<?> entityIdClass;

    public OrmRepositoryHandler(OrmConnectionWrapper ormConnectionWrapper, Class<?> entityClass, Class<?> entityIdClass) {
        this.ormConnectionWrapper = ormConnectionWrapper;
        this.entityClass = entityClass;
        this.entityIdClass = entityIdClass;

        System.out.println("created repository for " + entityClass.getName() + " for table " + entityClass.getAnnotation(Table.class).value());
        System.out.println("with id class " + entityIdClass.getName());
        System.out.println("=== fields ===");

        for (Field f : getAllDeclaredNonStaticFieldsFromClassHierarchy(entityClass)) {
            String name = f.getName();
            String column = Optional.ofNullable(f.getAnnotation(Column.class)).map(Column::value).orElse("-");
            String manyToOne = Optional.ofNullable(f.getAnnotation(ManyToOne.class))
                    .map(ManyToOne::tableName)
                    .orElse("-");
            String joinColumnName = Optional.ofNullable(f.getAnnotation(JoinColumn.class)).map(JoinColumn::name).orElse("-");
            String joinColumnRef = Optional.ofNullable(f.getAnnotation(JoinColumn.class)).map(JoinColumn::referencedColumnName).orElse("-");

            System.out.printf("%s, %s, %s, %s, %s%n", name, column, manyToOne, joinColumnName, joinColumnRef);
        }
        System.out.println();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("find")) {

        } else if (method.getName().startsWith("save")) {

        } else {
            System.out.println("unknown method type: " + method.getName());
        }
        return null;
    }
}
