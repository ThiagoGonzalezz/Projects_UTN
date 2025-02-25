package vianditasONG.modelos.repositorios.formularios;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.formulario.Pregunta;

import java.util.List;
import java.util.Optional;

public interface IPreguntasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Pregunta> buscarPorId(Long id);
    List<Pregunta> buscarTodos();
}

