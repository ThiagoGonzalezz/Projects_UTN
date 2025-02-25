package vianditasONG.exceptions.login;

public class WrongPasswordException extends LoginErrorException{
    public WrongPasswordException() {
        super("La contraseña ingresada es incorrecta");
    }

    public WrongPasswordException(String message) {
        super(message);
    }

}
