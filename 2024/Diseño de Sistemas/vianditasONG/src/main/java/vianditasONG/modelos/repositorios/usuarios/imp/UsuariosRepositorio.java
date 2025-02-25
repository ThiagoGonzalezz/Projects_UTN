package vianditasONG.modelos.repositorios.usuarios.imp;

import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.usuarios.IUsuariosRepositorio;
import lombok.Builder;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Builder
public class UsuariosRepositorio extends RepositorioGenerico implements IUsuariosRepositorio {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Usuario.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Usuario> buscarPorNombreDeUsuario(String nombreDeUsuario) {
        try {
            Usuario usuario = (Usuario) entityManager()
                    .createQuery("from " + Usuario.class.getName() + " where nombreUsuario = :name and activo = 1")
                    .setParameter("name", nombreDeUsuario)
                    .getSingleResult();
            return Optional.of(usuario);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Usuario> buscarTodos() {
        return entityManager()
                .createQuery("from " + Usuario.class.getName() + "where activo = 1 ")
                .getResultList();
    }

}

