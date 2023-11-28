package edu.vsu.putinpa.polygon;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.orm.api.Column;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getAllDeclaredNonStaticFieldsFromClassHierarchy;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find driver class %s.".formatted("org.postgresql.Driver"), e);
        }

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bank_account",
                "postgres",
                "postgres");
        connection.setAutoCommit(false);

//        Statement statement = connection.createStatement();
//        statement.execute("delete from client;");
//        connection.commit();

        Client client = new Client("test5", "qwerty");

        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into client(id, name, password, whencreated) values(?, ?, ?, ?);"
        );
        preparedStatement.setObject(1, client.getUuid());
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getPassword());
        preparedStatement.setTimestamp(4, Timestamp.from(client.getWhenCreated()));

        preparedStatement.execute();
        connection.commit();

        PreparedStatement select = connection.prepareStatement("select * from client;");
        select.execute();
        ResultSet result = select.getResultSet();
        connection.commit();

        List<Client> clientList = new ArrayList<>();
        while (result.next()) {
            Client client1 = new Client();
            for (Field field : getAllDeclaredNonStaticFieldsFromClassHierarchy(Client.class)) {
                Class<?> type = field.getType();
                String column = field.getAnnotation(Column.class).value();
                Object value;
                if (type.equals(String.class)) {
                    value = result.getString(column);
                } else if (type.equals(Instant.class)) {
                    value = result.getTimestamp(column).toInstant();
                } else {
                    value = result.getObject(column);
                }

                field.setAccessible(true);
                field.set(client1, value);
            }
            clientList.add(client1);
        }

        clientList.forEach(c -> System.out.printf("id: %s; name: %s; password: %s; whenCreated %s%n", c.getUuid(), c.getName(), c.getPassword(), c.getWhenCreated()));
    }
}
