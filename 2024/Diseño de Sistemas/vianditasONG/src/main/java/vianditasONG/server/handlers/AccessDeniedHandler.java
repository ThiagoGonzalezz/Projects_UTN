package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.AccessDeniedException;

public class AccessDeniedHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(AccessDeniedException.class, (e, context) -> {
            context.status(401);
            context.render("errores/401.hbs");
        });
    }
}