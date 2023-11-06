package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class Environment {
    private Properties properties;

    public Environment() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(rootPath);
        String appConfigPath = rootPath + "application.properties";
        appConfigPath = URLDecoder.decode(appConfigPath, StandardCharsets.UTF_8).substring(1);
        System.out.println(appConfigPath);

        this.properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find application.properties in root of classpath", e);
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
