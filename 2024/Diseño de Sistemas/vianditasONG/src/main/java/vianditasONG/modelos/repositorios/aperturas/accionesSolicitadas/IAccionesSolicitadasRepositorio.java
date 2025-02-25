package vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;

import java.util.List;
import java.util.Optional;

public interface IAccionesSolicitadasRepositorio {
    Optional<AccionSolicitada> buscar(Long id);
    List<AccionSolicitada> buscarTodos();
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
}
