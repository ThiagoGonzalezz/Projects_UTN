package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

@Builder
public class EstandarMinusculas implements Estandar {
    @Builder.Default
    private String mensajeError = Config.getInstancia().obtenerDelConfig("mensajeDeErrorEstMinusculas");

    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if(!password.chars().anyMatch(c -> Character.isLowerCase(c))){
            resultado.setValido(false);
            resultado.setMensajeError(mensajeError);
        }

        return resultado;
    }

}
