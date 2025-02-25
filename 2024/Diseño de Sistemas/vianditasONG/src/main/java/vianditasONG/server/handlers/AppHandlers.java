package vianditasONG.server.handlers;

import io.javalin.Javalin;

import java.util.Arrays;

public class AppHandlers {
    private IHandler[] handlers = new IHandler[]{
            new AccessDeniedHandler(),
            new LoginErrorsHandler(),
            new ColaboradorInhabilitadoHandler(),
            new BadRequestHandler(),
            new ServerErrorHandler(),
            new PuntosInsuficientesHandler(),
            new TarjetaYaUtilizadaHandler(),
            new DistribuirViandasHeladerasDupHandler(),
            new CantViandasErroneasHandler(),
            new TarjetaInexistenteHandler(),
            new ImposibilidadSuscripcionExceptionHandler()
    };

    public static void applyHandlers(Javalin app) {
        Arrays.stream(new AppHandlers().handlers).toList().forEach(handler -> handler.setHandle(app));
    }
}