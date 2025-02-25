package vianditasONG.exceptions.signUp;

import vianditasONG.exceptions.ServerErrorException;

public class HumanoVinculadoInexistenteException extends ServerErrorException {
    public HumanoVinculadoInexistenteException() {
        super("El humano buscado no existe.");
    }

    public HumanoVinculadoInexistenteException(String message) {
        super(message);
    }
}
