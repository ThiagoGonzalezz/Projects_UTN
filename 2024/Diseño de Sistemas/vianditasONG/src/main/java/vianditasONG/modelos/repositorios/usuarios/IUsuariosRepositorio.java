package vianditasONG.modelos.repositorios.usuarios;

import vianditasONG.utils.IPersistente;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuariosRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorNombreDeUsuario(String nombreDeUsuario);
    List<Usuario> buscarTodos();
}
