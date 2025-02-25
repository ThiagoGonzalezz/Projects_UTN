package vianditasONG.exceptions.signUp;

import vianditasONG.exceptions.ServerErrorException;

public class PersonaJuridicaVinculadaInexistenteException extends ServerErrorException {
    public PersonaJuridicaVinculadaInexistenteException() {
        super("La persona buscada no existe.");
    }

    public PersonaJuridicaVinculadaInexistenteException(String message) {
        super(message);
    }
}
