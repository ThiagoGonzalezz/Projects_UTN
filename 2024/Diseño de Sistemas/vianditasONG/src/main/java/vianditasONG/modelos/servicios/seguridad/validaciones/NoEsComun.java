package vianditasONG.modelos.servicios.seguridad.validaciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static vianditasONG.modelos.servicios.seguridad.config.Config.getInstancia;

@Builder(builderClassName = "BuilderConLogica")
@AllArgsConstructor
public class NoEsComun implements Validacion {

    private Set<String> contraseniasComunes;

    private String mensajeError;

    public static class BuilderConLogica {

        public NoEsComun build() {
            NoEsComun instancia = new NoEsComun(new HashSet<>(),
                    Config.getInstancia().obtenerDelConfig("mensajeDeErrorContraseniaComun"));

            instancia.cargarContraseniasComunes();
            return instancia;
        }
    }
    private void cargarContraseniasComunes() {
        String filePath = "seguridad/contraseniasComunes.txt"; // Ruta en el classpath

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo " + filePath + " en el classpath.");
            }

            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
                String linea;
                while ((linea = buffer.readLine()) != null) {
                    contraseniasComunes.add(linea.trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las contraseñas comunes desde el archivo: " + filePath, e);
        }
    }


    @Override
    public ResultadoValidacion cumpleValidacion(String password) {
        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if (contraseniasComunes.contains(password)){
            resultado.setValido(false);
            resultado.setMensajeError(mensajeError);
        }

        return  resultado;
    }
}

