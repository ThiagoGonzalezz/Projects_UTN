package vianditasONG.modelos.entities.personaVulnerable.tarjetas;

import vianditasONG.utils.excepciones.IExcepcionPersonalizada;

import java.util.Arrays;

public class ExcepcionUsosTarjeta extends RuntimeException implements IExcepcionPersonalizada {

    public ExcepcionUsosTarjeta(String mensaje) {
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
