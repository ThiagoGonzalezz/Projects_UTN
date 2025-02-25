package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.DistribuirViandasHeladerasDupException;

public class DistribuirViandasHeladerasDupHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(DistribuirViandasHeladerasDupException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400HeladerasDup.hbs");
        });
    }
}
