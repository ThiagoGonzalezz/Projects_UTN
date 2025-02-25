package vianditasONG.modelos.repositorios.formularios;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.formulario.Respuesta;

import java.util.List;
import java.util.Optional;

public interface IRespuestasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Respuesta> buscarPorId(Long id);
    List<Respuesta> buscarTodos();
}

