package vianditasONG.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("Se produjo al procesar la solicitud. La consulta es incorrecta");
    }

    public BadRequestException(String message) {
        super(message);
    }
}

