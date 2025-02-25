package vianditasONG.modelos.repositorios.incidentes;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.modelos.repositorios.RepositorioGenerico;

import java.util.List;
import java.util.Optional;

@Builder
public class VisitaHeladeraRepositorio extends RepositorioGenerico implements IVisitaHeladeraRepositorio{

    @Override
    public Optional<VisitaHeladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(VisitaHeladera.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VisitaHeladera> buscarTodos() {
        return entityManager()
                .createQuery("from " + VisitaHeladera.class.getName())
                .getResultList();
    }
}
