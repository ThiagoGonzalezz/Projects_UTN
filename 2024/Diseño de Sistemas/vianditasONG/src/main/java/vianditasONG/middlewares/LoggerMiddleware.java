package vianditasONG.middlewares;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerMiddleware implements IMiddleware{
    private static final Logger logger = LoggerFactory.getLogger(LoggerMiddleware.class);

    @Override
    public void setMiddleware(Javalin app) {
        app.before(this::logRequest);
        app.after(this::logResponse);
    }

    private void logRequest(Context ctx) {
        logger.info("Incoming request - Method: {}, Path: {}, IP: {}",
                ctx.method(), ctx.path(), ctx.ip());
        ctx.attribute("tiempoComienzo", System.currentTimeMillis());
    }

    private void logResponse(Context ctx) {
        long startTime = ctx.attribute("tiempoComienzo");
        long duration = System.currentTimeMillis() - startTime;

        logger.info("Completed request - Method: {}, Path: {}, Status: {}, Duration: {} ms",
                ctx.method(), ctx.path(), ctx.status(), duration);
    }
}