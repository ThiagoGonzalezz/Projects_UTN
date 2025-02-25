package vianditasONG.modelos.repositorios.incidentes;

import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.utils.IPersistente;

import java.util.List;
import java.util.Optional;

public interface IVisitaHeladeraRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<VisitaHeladera> buscarPorId(Long id);
    List<VisitaHeladera> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
