package vianditasONG.exceptions;

public class TarjetaYaUtilizadaException extends RuntimeException{

    public TarjetaYaUtilizadaException() {
        super("Tarjeta ya utilizada");
    }

}