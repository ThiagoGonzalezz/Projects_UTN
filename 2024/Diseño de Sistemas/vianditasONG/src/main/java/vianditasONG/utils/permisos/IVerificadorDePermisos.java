package vianditasONG.utils.permisos;

import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IVerificadorDePermisos {
    public void verificarSiUsuarioPuede(String accion, Usuario usuario);
}
