package vianditasONG.modelos.entities.colaboradores;

import vianditasONG.utils.excepciones.IExcepcionPersonalizada;

import java.util.Arrays;

public class ExcepcionDireccionNula extends RuntimeException implements IExcepcionPersonalizada {
    public ExcepcionDireccionNula(String mensaje) {
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
