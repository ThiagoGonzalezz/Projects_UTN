package vianditasONG.services.exceptions;

public class AccionSolicitadaNoEncontradaException extends RuntimeException {

    public AccionSolicitadaNoEncontradaException(){

    }
    public AccionSolicitadaNoEncontradaException(String message){
        super(message);
    }

}
