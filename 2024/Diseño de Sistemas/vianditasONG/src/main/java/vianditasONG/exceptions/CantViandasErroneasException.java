package vianditasONG.exceptions;

public class CantViandasErroneasException extends RuntimeException{
    public CantViandasErroneasException() {
        super("La cantidad de viandas seleccionadas para redistribuir no contempla la capacidad de las heladeras.");
    }

}