package vianditasONG.modelos.repositorios.incidentes;

import lombok.Builder;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.repositorios.RepositorioGenerico;

import java.util.List;
import java.util.Optional;

@Builder
public class IncidentesRepositorio extends RepositorioGenerico implements IIncidentesRepositorio {

    public Optional<Incidente> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Incidente.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> buscarTodos() {
        return entityManager()
                .createQuery("from " + Incidente.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> buscarPorHeladeraId(Long heladeraId) {
        return entityManager()
                .createQuery("from " + Incidente.class.getName() + " i where i.heladeraAsociada.id = :heladeraId")
                .setParameter("heladeraId", heladeraId)
                .getResultList();
    }
}

