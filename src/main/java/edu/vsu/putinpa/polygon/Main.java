package edu.vsu.putinpa.polygon;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.infrastructure.orm.api.*;

import javax.xml.transform.Result;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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

        Client creator = new Client();
        creator.setUUID(UUID.fromString("604fcde4-437a-4f9e-b3ad-f55d63decf78"));
        creator.setName("test");
        creator.setPassword("qwerty");
        creator.setWhenCreated(Timestamp.valueOf("2023-11-28 16:00:00.715301").toInstant());

        Account account = new Account("account1", "ru", creator);

        PreparedStatement insert = connection.prepareStatement(
                "insert into account(id, name, whenopened, whenclosed, balance, currency_id, creator_id)" +
                        "values(?, ?, ?, ?, ?, ?, ?)"
        );
        insert.setObject(1, account.getUuid());
        insert.setString(2, account.getName());
        insert.setTimestamp(3, Timestamp.from(account.getWhenOpened()));
        Timestamp whenClosedOpt = account.getWhenClosed() == null ? null : Timestamp.from(account.getWhenClosed());
        insert.setTimestamp(4, whenClosedOpt);
        insert.setBigDecimal(5, account.getBalance().value());
        insert.setString(6, account.getBalance().currency());
        insert.setObject(7, account.getCreator().getUuid());

        insert.execute();
        connection.commit();

        Statement statement = connection.createStatement();
        statement.execute("select * from account account1");
        ResultSet result = statement.getResultSet();

        List<Account> accountList = new ArrayList<>();
        while (result.next()) {
            Account account1 = new Account();

            for (Field field : getAllDeclaredNonStaticFieldsFromClassHierarchy(Account.class)) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String columnName = column.value();
                    Object value;
                    if (type.equals(Instant.class)) {
                        Timestamp time = result.getTimestamp(columnName);
                        value = time == null ? null : time.toInstant();
                    } else {
                        value = result.getObject(columnName, type);
                    }
                    field.set(account1, value);
                } else {
                    ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                    JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                    if (manyToOne != null && joinColumn != null) {
                        String columnName = joinColumn.name();
                        Object foreignKey = result.getObject(columnName);

                        String referencedColumnName = joinColumn.referencedColumnName();
                        String referencedTableName = type.getAnnotation(Table.class).value();

                        PreparedStatement select = connection.prepareStatement("select * from %s where %s=?;".formatted(referencedTableName, referencedColumnName));
                        select.setObject(1, foreignKey);

                        boolean hasResults = select.execute();

                        Client client = null;
                        if (hasResults) {
                            ResultSet clientResult = select.getResultSet();
                            clientResult.next();
                        }
                    } else {
                        Agregated agregated = field.getAnnotation(Agregated.class);
                        if (agregated != null) {

                        }
                    }
                }
            }

           /* UUID id = result.getObject("id", UUID.class);
            String name = result.getString("name");
            Instant whenOpened = result.getTimestamp("whenopened").toInstant();
            Timestamp whenClosedParsedOpt = result.getTimestamp("whenclosed");
            Instant whenClosed = whenClosedParsedOpt == null ? null : whenClosedParsedOpt.toInstant();

            BigDecimal value = result.getBigDecimal("balance");
            String currency = result.getString("currency_id");
            Money balance = new Money(currency, value);

            UUID creatorId = result.getObject("creator_id", UUID.class);
            PreparedStatement select = connection.prepareStatement("select * from client where id=?;");
            select.setObject(1, creatorId);
            boolean hasResults = select.execute();

            Client client = null;
            if (hasResults) {
                ResultSet clientResult = select.getResultSet();
                clientResult.next();

                String creatorName = clientResult.getString("name");
                String creatorPassword = clientResult.getString("password");
                Instant creatorWhenCreated = clientResult.getTimestamp("whencreated").toInstant();

                client = new Client();
                client.setUUID(creatorId);
                client.setName(creatorName);
                client.setPassword(creatorPassword);
                client.setWhenCreated(creatorWhenCreated);
            }

            account1.setUUID(id);
            account1.setName(name);
            account1.setWhenOpened(whenOpened);
            account1.setWhenClosed(whenClosed);
            account1.setBalance(balance);
            account1.setCreator(client);

            accountList.add(account1);*/
        }

        accountList.forEach(Main::printAccount);
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
