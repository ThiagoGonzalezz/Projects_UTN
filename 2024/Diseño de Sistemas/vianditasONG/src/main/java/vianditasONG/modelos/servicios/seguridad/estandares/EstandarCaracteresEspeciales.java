package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

@Builder
public class EstandarCaracteresEspeciales implements Estandar {
    @Builder.Default
    private String mensajeError = Config.getInstancia().obtenerDelConfig("mensajeDeErrorEstCaracteresEspeciales");
    @Builder.Default
    private String caracteresEspeciales = Config.getInstancia().obtenerDelConfig("caracteresEspeciales");
    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if(!password.chars().anyMatch(c -> caracteresEspeciales.indexOf(c) != -1)){
            resultado.setValido(false);
            resultado.setMensajeError(mensajeError);
        }

        return resultado;
    }

}

