package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

@Builder
public class EstandarMayusculas implements Estandar {
    @Builder.Default
    private String mensajeError = Config.getInstancia().obtenerDelConfig("mensajeDeErrorEstMayusculas");

    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if(!password.chars().anyMatch(c -> Character.isUpperCase(c))){
            resultado.setValido(false);
            resultado.setMensajeError(mensajeError);
        }

        return resultado;
    }
}
