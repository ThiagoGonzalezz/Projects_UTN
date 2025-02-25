package vianditasONG.modelos.repositorios.formularios;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.formulario.Formulario;

import java.util.List;
import java.util.Optional;

public interface IFormulariosRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Formulario> buscarPorId(Long id);
    Optional<Formulario> buscarPorNombre(String nombre);
    List<Formulario> buscarTodos();
}

