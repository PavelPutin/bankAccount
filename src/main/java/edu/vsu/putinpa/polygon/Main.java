package edu.vsu.putinpa.polygon;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.infrastructure.orm.api.*;

import javax.xml.transform.Result;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getAllDeclaredNonStaticFieldsFromClassHierarchy;
import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getCanonicalConstructor;

public class Main {
    public static Connection connection;
    public static void main(String[] args) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find driver class %s.".formatted("org.postgresql.Driver"), e);
        }

        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bank_account",
                "postgres",
                "postgres");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.execute("select * from journal_operation");
        ResultSet result = statement.getResultSet();

        List<JournalOperation> accountList = new ArrayList<>();
        while (result.next()) {
            JournalOperation account1 = relationToObject(JournalOperation.class, result);
            accountList.add(account1);
        }
    }

    private static <T> T relationToObject(Class<T> clazz, ResultSet result) throws Exception {
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

    private static Object processMember(ResultSet result, AnnotatedElement member, Class<?> type) throws Exception {
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

            PreparedStatement select = connection.prepareStatement("select * from %s where %s=?;".formatted(referencedTableName, referencedColumnName));
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

    private static void printAccount(Account account) {
        String value = """
                id: %s;
                name: %s;
                whenOpened: %s;
                whenClosed: %s;
                balance:
                    value: %s;
                    currency: %s
                creator:
                    id: %s;
                    name: %s;
                    password: %s;
                    whenCreated: %s""".formatted(
                account.getUuid().toString(),
                account.getName(),
                account.getWhenOpened(),
                account.getWhenClosed(),
                account.getBalance().value(),
                account.getBalance().currency(),
                account.getCreator().getUuid().toString(),
                account.getCreator().getName(),
                account.getCreator().getPassword(),
                account.getCreator().getWhenCreated()
        );
        System.out.println(value);
    }
}
