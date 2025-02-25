package vianditasONG.utils;

import io.javalin.http.Context;

import java.io.IOException;

public interface ICrudViewsHandler {
    void index(Context context);
    void show(Context context);
    void create(Context context);
    void save(Context context) throws IOException;
    void edit(Context context) throws IOException;
    void update(Context context) throws IOException;
    void delete(Context context);
}