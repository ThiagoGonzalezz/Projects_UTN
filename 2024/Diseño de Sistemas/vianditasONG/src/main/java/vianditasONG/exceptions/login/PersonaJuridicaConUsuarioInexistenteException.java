package vianditasONG.exceptions.login;

import vianditasONG.exceptions.ServerErrorException;

public class PersonaJuridicaConUsuarioInexistenteException extends ServerErrorException {
    public PersonaJuridicaConUsuarioInexistenteException() {
        super("La persona juridica con el usuario indicado no existe.");
    }

    public PersonaJuridicaConUsuarioInexistenteException(String message) {
        super(message);
    }
}
