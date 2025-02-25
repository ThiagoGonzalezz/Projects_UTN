package vianditasONG.middlewares;

import io.javalin.Javalin;
import io.javalin.http.Context;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;

public class AuthMiddleware implements IMiddleware{

    @Override
    public void setMiddleware(Javalin app) {
        app.beforeMatched(ctx -> {
            var userRole = getUserRoleType(ctx);
            if (!ctx.routeRoles().isEmpty() && !ctx.routeRoles().contains(userRole)) {
                throw new AccessDeniedException();
            }
        });
    }

    private static Rol getUserRoleType(Context context) {
        return context.sessionAttribute("tipo-rol") != null?
               Rol.valueOf(context.sessionAttribute("tipo-rol")) : null;
    }
}
