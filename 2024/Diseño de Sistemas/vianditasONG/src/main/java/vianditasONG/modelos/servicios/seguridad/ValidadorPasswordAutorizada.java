package vianditasONG.modelos.servicios.seguridad;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.exceptions.login.WrongPasswordException;
import vianditasONG.modelos.servicios.seguridad.encriptadores.Encriptador;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorMD5;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

import java.util.Objects;

@Builder
public class ValidadorPasswordAutorizada {
    @Builder.Default
    private Encriptador encriptador = ServiceLocator.getService(EncriptadorMD5.class);

    public void validarPasswordAutorizada(Usuario usuario, String password) {
        String hashPasswordIngresada = this.encriptador.encriptar(password);

        if(!hashPasswordIngresada.equals(usuario.getHashContrasenia())){
            throw new WrongPasswordException();
        }
    }
}
