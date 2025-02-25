package vianditasONG.exceptions.login;

public class LoginErrorException extends RuntimeException {
    public LoginErrorException() {
        super("Se produjo un error en el inicio de sesión.");
    }

    public LoginErrorException(String message) {
        super(message);
    }
}