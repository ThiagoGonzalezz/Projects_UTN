package vianditasONG.controllers.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.migracionDeColaboraciones.ImportadorDeColaboraciones;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Builder
public class ImportadorColaboracionesController {

    @Builder.Default
    private ImportadorDeColaboraciones importadorDeColaboraciones = ServiceLocator.getService(ImportadorDeColaboraciones.class);

    public void create(Context context) {
        context.render("admin/importador_de_colaboraciones.hbs");

    }


    public void save(Context context) throws IOException {
        //toDo: @Thiago chequearPermisos

        UploadedFile csvImportacion = context.uploadedFile("csv-importacion");

        // Asegúrate de que el archivo ha sido subido
        if (csvImportacion == null) {
            context.status(400).result("No se recibió el archivo.");
            return;
        }


        InputStream inputStream = csvImportacion.content();
        this.importadorDeColaboraciones.importarCSV(inputStream);


        context.status(200).result("CSV procesado con éxito!");
    }
}
