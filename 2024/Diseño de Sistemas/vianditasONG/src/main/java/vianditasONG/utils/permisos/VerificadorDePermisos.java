/*package vianditasONG.utils.permisos;

import vianditasONG.utils.usuarioRolesYPermisos.Permiso;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.repositorios.permisos.IPermisosRepositorio;

import java.util.Optional;

public class VerificadorDePermisos implements IVerificadorDePermisos {

    private IPermisosRepositorio permisosRepositorio;

    public VerificadorDePermisos(IPermisosRepositorio permisosRepositorio){
        this.permisosRepositorio = permisosRepositorio;
    }

    public void verificarSiUsuarioPuede(String accion, Usuario usuario){

        Optional<Permiso> permisoBuscado = this.permisosRepositorio.buscarPorDescripcion(accion);

        if(permisoBuscado.isEmpty())
            throw new RuntimeException("No existe un permiso con el nombre " + accion);
        Permiso permiso = permisoBuscado.get();

        if(!usuario.tienePermiso(permiso))
            throw new SinPermisoSuficienteException(("Usted no tiene permiso: " + accion));

    }

}*/ //TODO: BORRAR ESTO. Esta lógica la vamos a usar en lo de middlewares
