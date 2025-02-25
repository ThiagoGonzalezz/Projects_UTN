package vianditasONG.exceptions.login;

public class UserNotFoundException extends LoginErrorException {
    public UserNotFoundException() {
        super("No fue posible encontrar el usuario ingresado");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}