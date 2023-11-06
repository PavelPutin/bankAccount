package edu.vsu.putinpa.infrastructure.orm;

import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.di.api.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class OrmConnection {
    @Property("{orm.datasource.url}")
    private String url;

    @Property("{orm.datasource.username}")
    private String username;

    @Property("{orm.datasource.password}")
    private String password;
    @Property("{orm.datasource.driver}")
    private String driverClassName;
    private Connection connection;

    public OrmConnection() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find driver class %s.".formatted(driverClassName), e);
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to database.", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
