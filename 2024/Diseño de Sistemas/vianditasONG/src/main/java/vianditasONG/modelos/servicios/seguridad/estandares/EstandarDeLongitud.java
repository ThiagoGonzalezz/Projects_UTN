package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

@Builder
public class EstandarDeLongitud implements Estandar{

    @Builder.Default
    private int longitudMinima = Integer.parseInt(Config.getInstancia().obtenerDelConfig("longitudMinima"));
    @Builder.Default
    private int longitudMaxima = Integer.parseInt(Config.getInstancia().obtenerDelConfig("longitudMaxima"));
    @Builder.Default
    private String mensajeError = (Config.getInstancia().obtenerDelConfig("mensajeDeErrorEstLongitud"));
    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if(password.length() < longitudMinima || password.length() > longitudMaxima){
            resultado.setValido(false);
            resultado.setMensajeError(mensajeError);
        }

        return resultado;
    }
}
