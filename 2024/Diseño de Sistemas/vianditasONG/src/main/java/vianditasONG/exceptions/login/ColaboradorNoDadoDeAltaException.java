package vianditasONG.exceptions.login;

public class ColaboradorNoDadoDeAltaException extends RuntimeException{
    public ColaboradorNoDadoDeAltaException() {
        super("El colabarador no se encuentro dado de alta aún.");
    }

    public ColaboradorNoDadoDeAltaException(String message) {
        super(message);
    }
}

