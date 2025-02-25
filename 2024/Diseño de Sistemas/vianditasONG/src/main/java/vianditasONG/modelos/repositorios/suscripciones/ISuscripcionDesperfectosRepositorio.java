package vianditasONG.modelos.repositorios.suscripciones;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;

import java.util.List;
import java.util.Optional;

public interface ISuscripcionDesperfectosRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<SuscripcionDesperfectos> buscarPorId(Long id);
    List<SuscripcionDesperfectos> buscarPorIdHumano(Long humanoId);
    List<SuscripcionDesperfectos> buscarTodos();
    void eliminarLogico(IPersistente persistente);

}
