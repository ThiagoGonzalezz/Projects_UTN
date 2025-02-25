package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.login.ColaboradorNoDadoDeAltaException;

public class ColaboradorInhabilitadoHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(ColaboradorNoDadoDeAltaException.class, (e, context) -> {
            context.render("errores/403NoDadoDeAlta.hbs").status(403);
        });
    }
}
