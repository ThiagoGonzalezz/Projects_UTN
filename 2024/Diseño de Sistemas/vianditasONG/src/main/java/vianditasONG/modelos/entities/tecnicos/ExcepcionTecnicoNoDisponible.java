package vianditasONG.modelos.entities.tecnicos;

import vianditasONG.utils.excepciones.IExcepcionPersonalizada;

import java.util.Arrays;

public class ExcepcionTecnicoNoDisponible extends RuntimeException implements IExcepcionPersonalizada {
    public ExcepcionTecnicoNoDisponible (String mensaje) {
        super(mensaje);
    }

    @Override
    public String getMensajePersonalizado() {
        return super.getMessage();
    }

    @Override
    public String getStackTracePersonalizado() {
        return Arrays.toString(super.getStackTrace());
    }
}
