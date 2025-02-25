package vianditasONG.modelos.repositorios.incidentes;

import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.utils.IPersistente;

import java.util.List;
import java.util.Optional;

public interface IIncidentesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Incidente> buscarPorId(Long id);
    List<Incidente> buscarTodos();
    List<Incidente> buscarPorHeladeraId(Long heladeraId);
    void eliminarLogico(IPersistente persistente);
}

