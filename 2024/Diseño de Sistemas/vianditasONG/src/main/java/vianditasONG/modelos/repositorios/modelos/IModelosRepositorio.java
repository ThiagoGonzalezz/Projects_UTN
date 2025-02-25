package vianditasONG.modelos.repositorios.modelos;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.Modelo;

import java.util.List;
import java.util.Optional;

public interface IModelosRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Modelo> buscarPorId(Long id);
    List<Modelo> buscarTodos();
}


