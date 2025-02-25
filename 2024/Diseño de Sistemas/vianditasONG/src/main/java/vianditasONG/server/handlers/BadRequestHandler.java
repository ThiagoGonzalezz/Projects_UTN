package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.login.ColaboradorNoDadoDeAltaException;

public class BadRequestHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(BadRequestException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400.hbs");
        });
    }
}
