package vianditasONG.modelos.repositorios.alertas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.incidentes.Alerta;

import java.util.List;
import java.util.Optional;

public interface IAlertasRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Alerta> buscarPorId(Long id);
    List<Alerta> buscarTodos();
    void eliminarLogico(IPersistente persistente);

}
