package vianditasONG.utils.permisos;

public class SinPermisoSuficienteException extends RuntimeException{

    public SinPermisoSuficienteException() {
    }

    public SinPermisoSuficienteException(String message) {
        super(message);
    }

}
