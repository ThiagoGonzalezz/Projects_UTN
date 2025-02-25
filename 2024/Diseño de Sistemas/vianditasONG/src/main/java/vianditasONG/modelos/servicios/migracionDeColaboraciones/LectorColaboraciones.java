package vianditasONG.modelos.servicios.migracionDeColaboraciones;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LectorColaboraciones {
    public List<LineaColaboracion> leerArchivoCSV(InputStream inputStream) throws IOException {
        // Usar BufferedReader para leer el InputStream
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<LineaColaboracion> lineas = new ArrayList<>();
            String linea;
            boolean primeraLinea = true;

            while ((linea = bufferReader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Ignorar la primera línea (encabezados)
                    continue;
                }

                String[] datos = linea.split(";");

                // Asegúrate de que los datos tengan la longitud esperada
                if (datos.length < 8) {
                    // Maneja el error según sea necesario, por ejemplo, lanzar una excepción o registrar un error
                    continue; // O lanzar una excepción si prefieres
                }

                LineaColaboracion nuevaLinea = new LineaColaboracion(
                        datos[0], // tipoDoc
                        datos[1], // documento
                        datos[2], // nombre
                        datos[3], // apellido
                        datos[4], // mail
                        datos[5], // fechaColaboracion
                        datos[6], // formaColaboracion
                        datos[7]  // cantidad
                );

                lineas.add(nuevaLinea);
            }
            return lineas;
        }
    }
}