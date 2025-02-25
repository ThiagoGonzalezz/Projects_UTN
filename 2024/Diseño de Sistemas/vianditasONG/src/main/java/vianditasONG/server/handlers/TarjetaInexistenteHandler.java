package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.TarjetaInexistenteException;
import vianditasONG.exceptions.TarjetaYaUtilizadaException;

public class TarjetaInexistenteHandler implements IHandler{

    @Override
    public void setHandle(Javalin app) {
        app.exception(TarjetaInexistenteException.class, (e, context) -> {
            context.status(400);
            context.render("errores/400TarjetaInexistente.hbs");
        });
    }

}
