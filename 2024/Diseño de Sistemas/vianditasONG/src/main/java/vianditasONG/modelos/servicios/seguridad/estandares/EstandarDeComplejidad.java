package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.config.Config;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;
import vianditasONG.modelos.servicios.seguridad.validaciones.Validacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
public class EstandarDeComplejidad implements Estandar{
    @Builder.Default
    private List<Estandar> subestandares = new ArrayList<>();

    public void agregarEstandares(Estandar ... estandares){
        Collections.addAll(this.subestandares, estandares);
    }
    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        ResultadoValidacion resultadoValidacion = new ResultadoValidacion(true);

        List<ResultadoValidacion> resultadosFallidos = subestandares.
                stream()
                .map(s -> s.cumpleEstandar(password))
                .filter(r -> !r.esValido())
                .toList();

        if (!resultadosFallidos.isEmpty()) {
            resultadoValidacion.setValido(false);
            resultadoValidacion.setMensajeError(this.elaborarMensajeResultado(resultadosFallidos));
        }

        return resultadoValidacion;
    }

    private String elaborarMensajeResultado(List<ResultadoValidacion> resultados) {
        StringBuilder mensajeError = new StringBuilder(Config.getInstancia().obtenerDelConfig("mensajeDeErrorBaseEstComplejidad") + " ");

        int totalMensajes = resultados.size();
        for (int i = 0; i < totalMensajes; i++) {
            String mensaje = resultados.get(i).getMensajeError();

            if (i == 0) {
                mensajeError.append(mensaje);
            } else if (i == totalMensajes - 1) {
                mensajeError.append(" y ").append(mensaje);
            } else {
                mensajeError.append(", ").append(mensaje);
            }
        }

        return mensajeError.toString();
    }
}
