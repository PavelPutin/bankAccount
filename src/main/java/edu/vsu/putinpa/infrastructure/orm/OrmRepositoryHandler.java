package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.application.model.EntityWithUUID;
import edu.vsu.putinpa.infrastructure.orm.api.*;

import javax.swing.text.html.Option;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.*;

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
            Query query = method.getAnnotation(Query.class);
            if (query == null) {
                throw new RuntimeException("Method " + method.getName() + " of repository for " + entityClass.getName() + " must be annotated with @Query.");
            }
            String sql = query.value();
            Connection connection = ormConnectionWrapper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 1; args != null && i <= args.length; i++) {
                Object value = null;
                if (args[i - 1].getClass().isAnnotationPresent(Table.class)) {
                    value = ((EntityWithUUID) args[i - 1]).getUuid();
                } else {
                    value = args[i - 1];
                }
                preparedStatement.setObject(i, value);
            }

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (method.getReturnType().equals(Optional.class)) {
                boolean hasResult = resultSet.next();
                return Optional.ofNullable(hasResult ? relationToObject(entityClass, resultSet) : null);
            } else if (method.getReturnType().equals(List.class)) {
                List<Object> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(relationToObject(entityClass, resultSet));
                }
                return result;
            } else {
                return null;
            }

        } else if (method.getName().startsWith("save")) {
            InsertMappingBy insertMappingBy = entityClass.getAnnotation(InsertMappingBy.class);
            if (insertMappingBy == null) {
                throw new RuntimeException("Insert mapping must be declared for " + entityClass.getName() + ".");
            }

            Function<Object, InsertMappingInfo> insertMapper = (Function<Object, InsertMappingInfo>) insertMappingBy.value().getConstructor().newInstance();
            InsertMappingInfo insertMappingInfo = insertMapper.apply(args[0]);

            Connection connection = ormConnectionWrapper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertMappingInfo.sql());

            int i = 1;
            for (Object value : insertMappingInfo.values()) {
                preparedStatement.setObject(i++, value);
            }
            preparedStatement.executeUpdate();
            return args[0];
        } else {
            System.out.println("unknown method type: " + method.getName());
        }
        return null;
    }

    private <T> T relationToObject(Class<T> clazz, ResultSet result) throws Exception {
        if (clazz.isRecord()) {
            Constructor<T> constructor = getCanonicalConstructor(clazz);
            List<Object> parameters = new ArrayList<>();
            for (RecordComponent recordComponent : clazz.getRecordComponents()) {
                parameters.add(processMember(result, recordComponent, recordComponent.getType()));
            }
            return constructor.newInstance(parameters.toArray(new Object[0]));
        } else {
            T object = clazz.getConstructor().newInstance();
            for (Field field : getAllDeclaredNonStaticFieldsFromClassHierarchy(clazz)) {
                field.setAccessible(true);
                Object value = processMember(result, field, field.getType());
                field.set(object, value);
            }
            return object;
        }
    }

    private Object processMember(ResultSet result, AnnotatedElement member, Class<?> type) throws Exception {
        Column column = member.getAnnotation(Column.class);
        if (column != null) {
            String columnName = column.value();
            if (type.equals(Instant.class)) {
                Timestamp time = result.getTimestamp(columnName);
                return time == null ? null : time.toInstant();
            } else if (type.isEnum()) {
                String value = result.getString(columnName);
                return Enum.valueOf((Class<? extends Enum>) type, value);
            } else {
                return result.getObject(columnName, type);
            }
        }

        ManyToOne manyToOne = member.getAnnotation(ManyToOne.class);
        JoinColumn joinColumn = member.getAnnotation(JoinColumn.class);
        if (manyToOne != null && joinColumn != null) {
            String columnName = joinColumn.name();
            Object foreignKey = result.getObject(columnName);

            String referencedColumnName = joinColumn.referencedColumnName();
            Table referenced = type.getAnnotation(Table.class);
            String referencedTableName = referenced == null ? manyToOne.tableName() : referenced.value();

            PreparedStatement select = ormConnectionWrapper.getConnection().prepareStatement("select * from %s where %s=?;".formatted(referencedTableName, referencedColumnName));
            select.setObject(1, foreignKey);

            boolean hasResults = select.execute();

            if (hasResults) {
                ResultSet foreignResult = select.getResultSet();
                foreignResult.next();
                return relationToObject(type, foreignResult);
            }
            return null;
        }

        Agregated agregated = member.getAnnotation(Agregated.class);
        if (agregated != null) {
            return relationToObject(type, result);
        }

        return null;
    }
}
