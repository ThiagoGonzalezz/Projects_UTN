package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.PuntosInsuficientesException;
import vianditasONG.exceptions.TarjetaYaUtilizadaException;

public class TarjetaYaUtilizadaHandler implements IHandler{

    @Override
    public void setHandle(Javalin app) {
        app.exception(TarjetaYaUtilizadaException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400TarjetaYaUtilizada.hbs");
        });
    }

}