package vianditasONG.modelos.servicios.seguridad.estandares;

import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

public interface Estandar {
    public ResultadoValidacion cumpleEstandar(String password);
}
