package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.ServerErrorException;

public class ServerErrorHandler implements IHandler{

    @Override
    public void setHandle(Javalin app) {
        app.exception(ServerErrorException.class, (e, context) -> {
            context.status(500);
            context.render("errores/500.hbs");
        });
    }
}
