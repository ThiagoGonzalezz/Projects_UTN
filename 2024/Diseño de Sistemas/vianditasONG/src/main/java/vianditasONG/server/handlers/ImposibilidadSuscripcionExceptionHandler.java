package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.ImposibilidadSuscripcionException;

public class ImposibilidadSuscripcionExceptionHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(ImposibilidadSuscripcionException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400SuscripcionException.hbs");
        });
    }
}
