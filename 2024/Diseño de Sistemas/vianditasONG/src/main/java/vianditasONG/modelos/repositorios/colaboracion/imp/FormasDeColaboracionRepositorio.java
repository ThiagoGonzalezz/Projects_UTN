package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IFormasDeColaboracionRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class FormasDeColaboracionRepositorio extends RepositorioGenerico implements IFormasDeColaboracionRepositorio {

    @Override
    public Optional<FormaDeColaboracion> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(FormaDeColaboracion.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FormaDeColaboracion> buscarTodos() {
        return entityManager()
                .createQuery("from " + FormaDeColaboracion.class.getName())
                .getResultList();
    }
}
