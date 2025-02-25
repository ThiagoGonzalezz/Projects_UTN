package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.CantViandasErroneasException;


public class CantViandasErroneasHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(CantViandasErroneasException.class, (e, context) -> {
            context.status(409);
            context.render("errores/409CantViandasErroneas.hbs");
        });
    }
}
