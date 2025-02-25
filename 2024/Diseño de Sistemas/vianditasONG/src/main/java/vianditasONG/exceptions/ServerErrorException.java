package vianditasONG.exceptions;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException() {
        super("Se produjo un error en el servidor.");
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
