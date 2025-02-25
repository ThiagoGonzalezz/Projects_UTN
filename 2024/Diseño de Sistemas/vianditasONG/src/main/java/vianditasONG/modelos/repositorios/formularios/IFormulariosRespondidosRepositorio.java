package vianditasONG.modelos.repositorios.formularios;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;

import java.util.List;
import java.util.Optional;

public interface IFormulariosRespondidosRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<FormularioRespondido> buscarPorId(Long id);
    List<FormularioRespondido> buscarTodos();
}
