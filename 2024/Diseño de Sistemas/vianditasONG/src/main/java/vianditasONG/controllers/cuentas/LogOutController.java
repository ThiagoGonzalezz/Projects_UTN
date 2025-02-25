package vianditasONG.controllers.cuentas;


import io.javalin.http.Context;
import lombok.Builder;

@Builder
public class LogOutController {

    public void logout(Context context) {
        context.sessionAttribute("nombre-usuario", null);
        context.sessionAttribute("usuario-id", null);
        context.sessionAttribute("tipo-rol", null);
        context.sessionAttribute("persona-juridica-id", null);
        context.sessionAttribute("humano-id", null);

        context.req().getSession().invalidate();

        context.redirect("/login");
    }
}
