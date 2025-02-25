package vianditasONG.server.handlers;

import io.javalin.Javalin;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.exceptions.login.LoginErrorException;

public class LoginErrorsHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(LoginErrorException.class, (e, context) -> {
            context.status(401);
        });
    }
}
