package vianditasONG.modelos.repositorios.falla_tecnica;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;

import java.util.List;
import java.util.Optional;

public interface IFallasTecnicasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<FallaTecnica> buscarPorId(Long id);
    List<FallaTecnica> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}

