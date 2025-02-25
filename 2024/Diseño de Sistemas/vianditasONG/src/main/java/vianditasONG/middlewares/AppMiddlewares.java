package vianditasONG.middlewares;

import io.javalin.Javalin;

import java.util.Arrays;

public class AppMiddlewares {
    private IMiddleware[] middlewares = new IMiddleware[]{
            new AuthMiddleware(),
            new LoggerMiddleware()
    };

    public static void applyMiddlewares(Javalin app) {
        Arrays.stream(new AppMiddlewares().middlewares).toList().forEach(middleware -> middleware.setMiddleware(app));
    }

}
