package vianditasONG.exceptions;

public class DistribuirViandasHeladerasDupException extends RuntimeException{
    public DistribuirViandasHeladerasDupException() {
        super("Las heladeras seleccionadas para redistribuir viandas deben ser distintas entre sí.");
    }
}