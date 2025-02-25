package vianditasONG.modelos.repositorios.formularios;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.formulario.Opcion;

import java.util.List;
import java.util.Optional;

public interface IOpcionesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Opcion> buscarPorId(Long id);
    List<Opcion> buscarTodos();
}
