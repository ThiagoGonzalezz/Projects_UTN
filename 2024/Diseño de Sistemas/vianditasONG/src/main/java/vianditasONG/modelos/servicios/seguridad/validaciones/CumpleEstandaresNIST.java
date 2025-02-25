package vianditasONG.modelos.servicios.seguridad.validaciones;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.estandares.Estandar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class CumpleEstandaresNIST implements Validacion {

    @Builder.Default
    private List<Estandar> estandares=new ArrayList<>();
    @Builder.Default
    private List<String> mensajesError = new ArrayList<>();

    public void agregarEstandaresNist(Estandar ... estandares) {
        Collections.addAll(this.estandares, estandares);
    }

    @Override
    public ResultadoValidacion cumpleValidacion(String password) {

        List<ResultadoValidacion> resultadosFallidos = estandares
                .stream()
                .map(e -> e.cumpleEstandar(password))
                .filter(r -> !r.esValido())
                .toList();

        ResultadoValidacion resultado = new ResultadoValidacion(true);

        if(!resultadosFallidos.isEmpty()){
            resultado.setValido(false);
            resultado.setMensajeError(this.elaborarMensajeError(resultadosFallidos));
        }

        return resultado;
    }


    private String elaborarMensajeError(List<ResultadoValidacion> resultadosFallidos) {
       List<String> mensajesResultados = resultadosFallidos.stream()
               .map(r -> r.getMensajeError())
               .collect(Collectors.toList());

       return String.join(", ", mensajesResultados);
    }

}
