package vianditasONG.middlewares;

import io.javalin.Javalin;

public interface IMiddleware {
    void setMiddleware(Javalin app);
}
