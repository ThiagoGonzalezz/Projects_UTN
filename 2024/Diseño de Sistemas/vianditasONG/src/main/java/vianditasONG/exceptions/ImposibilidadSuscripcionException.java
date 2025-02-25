package vianditasONG.exceptions;

public class ImposibilidadSuscripcionException extends RuntimeException{
    public ImposibilidadSuscripcionException() {
        super("La heladera a la que quieres suscribirte se encuentra fuera de tu rango");
    }
}