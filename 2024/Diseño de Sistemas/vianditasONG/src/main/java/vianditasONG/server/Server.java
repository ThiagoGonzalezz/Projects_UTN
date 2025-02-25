package vianditasONG.server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import org.eclipse.paho.client.mqttv3.MqttException;
import vianditasONG.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import vianditasONG.config.ServiceLocator;
import vianditasONG.middlewares.AppMiddlewares;
import vianditasONG.middlewares.AuthMiddleware;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotInitializer;
import vianditasONG.server.handlers.AppHandlers;
import vianditasONG.utils.Initializer;
import vianditasONG.utils.JavalinRenderer;
import vianditasONG.utils.mqtt.ConfiguradorDeReceptores;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init(){
        if (app == null) {
            Integer port = Integer.parseInt(Config.getInstancia().obtenerDelConfig("server_port"));
            app = Javalin.create(config()).start(port);

            AppMiddlewares.applyMiddlewares(app);
            AppHandlers.applyHandlers(app);
            Router router = new Router();
            router.init(app);

            if (Boolean.parseBoolean(Config.getInstancia().obtenerDelConfig("dev_mode"))) {
                Initializer.init();
            }

            (ServiceLocator.getService(BotInitializer.class)).iniciarBot();
            ConfiguradorDeReceptores.initReceptores(
                    ServiceLocator.getService(SensoresDeMovimientoRepositorio.class),
                    ServiceLocator.getService(SensoresDeTemperaturaRepositorio.class)
            );

        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                try {
                    Files.createDirectories(uploadDir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads";
                staticFiles.directory = uploadDir.toString();
                staticFiles.location = Location.EXTERNAL;
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                handlebars.registerHelper("orEquals", (value, options) -> {
                    if (value == null) {
                        return options.inverse();
                    }

                    for (Object param : options.params) {
                        if (value.equals(param)) {
                            return options.fn();
                        }
                    }

                    return options.inverse();
                });

                // Registrar el helper concat
                handlebars.registerHelper("concat", new Helper<Object>() {
                    @Override
                    public Object apply(Object context, Options options) throws IOException {
                        StringBuilder result = new StringBuilder();

                        // Concatenar todos los parámetros
                        for (Object param : options.params) {
                            if (param != null) {
                                if (result.length() > 0) {
                                    result.append(" "); // Agregar espacio entre los parámetros
                                }
                                result.append(param.toString());
                            }
                        }

                        return result.toString();
                    }
                });

                Template template = null;
                try {
                    template = handlebars.compile(
                            "templates/" + path.replace(".hbs", ""));
                    return template.apply(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    return "No se encuentra la página indicada...";
                }
            }));
        };
    }
}