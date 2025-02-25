package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();
    private static Config instancia = null;

    private Config() {
        try {
            properties.load(new FileInputStream("src/main/java/config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstancia() {
        if (instancia == null) {
            instancia = new Config();
        }
        return instancia;
    }

    public String obtenerDelConfig(String property) {
        return properties.getProperty(property);
    }
}
