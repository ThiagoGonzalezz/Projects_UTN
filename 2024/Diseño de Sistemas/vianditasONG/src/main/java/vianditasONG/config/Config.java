package vianditasONG.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();
    private static Config instancia = null;

    private Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("configs/config.properties")) {
            if (input == null) {
                System.out.println("No se pudo encontrar config.properties");
                return;
            }
            properties.load(input);
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
