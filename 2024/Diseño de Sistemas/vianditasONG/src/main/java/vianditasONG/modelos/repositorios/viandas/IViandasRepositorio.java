package vianditasONG.modelos.repositorios.viandas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;

import java.util.List;
import java.util.Optional;

public interface IViandasRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Vianda> buscarPorId(Long id);
    List<Vianda> buscarTodos();
    void eliminarLogico(IPersistente persistente);

}
