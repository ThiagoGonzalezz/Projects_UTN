package vianditasONG.exceptions.login;

import vianditasONG.exceptions.ServerErrorException;

public class HumanoConUsuarioInexistenteException extends ServerErrorException {
    public HumanoConUsuarioInexistenteException() {
        super("El humano con el usuario indicado no existe.");
    }

    public HumanoConUsuarioInexistenteException(String message) {
        super(message);
    }
}

