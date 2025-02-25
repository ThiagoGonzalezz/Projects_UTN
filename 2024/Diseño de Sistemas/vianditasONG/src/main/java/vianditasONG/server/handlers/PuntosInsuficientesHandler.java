package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.PuntosInsuficientesException;
import vianditasONG.exceptions.login.LoginErrorException;

public class PuntosInsuficientesHandler implements IHandler{

    @Override
    public void setHandle(Javalin app) {
        app.exception(PuntosInsuficientesException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400PuntosInsuficientes.hbs");
        });
    }

}
