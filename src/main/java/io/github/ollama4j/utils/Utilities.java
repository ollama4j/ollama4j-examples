package io.github.ollama4j.utils;

import com.sun.tools.javac.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
    public static String getFromEnvVar(String key) {
        String val = System.getenv(key);
        if (val == null) {
            System.out.println("Environment variable " + key + " not found!");
        }
        return val;
    }

    public static String getFromConfig(String key) {
        Properties properties = new Properties();
        String host = "http://localhost:11434/";
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                host = properties.getProperty(key, host);
            } else {
                System.out.println("config.properties not found. Using default host: " + host);
            }
        } catch (IOException ex) {
            System.out.println("Error reading config.properties. Using default host: " + host);
            ex.printStackTrace();
        }
        return host;
    }
}
