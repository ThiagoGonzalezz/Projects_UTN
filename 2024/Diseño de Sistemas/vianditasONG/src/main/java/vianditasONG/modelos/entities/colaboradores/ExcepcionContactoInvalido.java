package vianditasONG.modelos.entities.colaboradores;

import vianditasONG.utils.excepciones.IExcepcionPersonalizada;

import java.util.Arrays;

public class ExcepcionContactoInvalido extends RuntimeException implements IExcepcionPersonalizada {

    public ExcepcionContactoInvalido(String mensaje) {
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
