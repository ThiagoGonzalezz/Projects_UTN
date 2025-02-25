package vianditasONG.modelos.servicios.seguridad.estandares;

import lombok.Builder;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

@Builder
public class EstandarDeRotacion implements Estandar{

    @Override
    public ResultadoValidacion cumpleEstandar(String password) {
        //toDo
        return new ResultadoValidacion(true);
    }
}
