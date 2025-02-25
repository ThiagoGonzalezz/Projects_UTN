package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;

import java.util.List;
import java.util.Optional;

public interface IFormasDeColaboracionRepositorio{

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<FormaDeColaboracion> buscarPorId(Long id);
    List<FormaDeColaboracion> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}

