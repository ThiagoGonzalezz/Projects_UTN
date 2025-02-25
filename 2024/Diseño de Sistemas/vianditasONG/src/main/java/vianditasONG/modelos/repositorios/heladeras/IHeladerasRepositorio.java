package vianditasONG.modelos.repositorios.heladeras;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.Heladera;

import java.util.List;
import java.util.Optional;

public interface IHeladerasRepositorio {
    Optional<Heladera> buscar(Long id);
    List<Heladera> buscarTodos();
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
}
