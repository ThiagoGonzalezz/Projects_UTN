package vianditasONG.modelos.servicios.seguridad;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;
import vianditasONG.modelos.servicios.seguridad.validaciones.Validacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
public class ValidadorCreacionPassword {
    @Builder.Default
    private List<Validacion> validaciones = new ArrayList<>();

    public void agregarValidaciones(Validacion... validaciones) {
        Collections.addAll(this.validaciones, validaciones);
    }

    public ResultadoValidacion validarCreacionContrasenia(String password) {
        ResultadoValidacion resultadoValidacion = new ResultadoValidacion(true);

        List<ResultadoValidacion> validacionesFallidas = validaciones.stream()
                .map(v -> v.cumpleValidacion(password))
                .filter(r -> !r.esValido())
                .toList();

        if (!validacionesFallidas.isEmpty()) {
            resultadoValidacion.setValido(false);
            resultadoValidacion.setMensajeError(this.elaborarMensajeDeError(validacionesFallidas));
        }
        return resultadoValidacion;

    }

    private String elaborarMensajeDeError(List<ResultadoValidacion> resultadosFallidos) {
        StringBuilder mensajeError = new StringBuilder("La contraseña debe ");

        int totalMensajes = resultadosFallidos.size();
        for (int i = 0; i < totalMensajes; i++) {
            String mensaje = resultadosFallidos.get(i).getMensajeError();

            if (i == 0) {
                mensajeError.append(mensaje);
            } else if (i == totalMensajes - 1) {
                mensajeError.append(" y ").append(mensaje);
            } else {
                mensajeError.append(" , ").append(mensaje);
            }
        }

        return mensajeError.toString();
    }
}

