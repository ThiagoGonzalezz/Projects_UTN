package vianditasONG.modelos.repositorios.rubros.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.rubros.IRubrosOfertasRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class RubrosOfertasRepositorio extends RepositorioGenerico implements IRubrosOfertasRepositorio {

    @Override
    public Optional<RubroOferta> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(RubroOferta.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RubroOferta> buscarTodos() {
        return entityManager()
                .createQuery("from " + RubroOferta.class.getName())
                .getResultList();
    }
}

