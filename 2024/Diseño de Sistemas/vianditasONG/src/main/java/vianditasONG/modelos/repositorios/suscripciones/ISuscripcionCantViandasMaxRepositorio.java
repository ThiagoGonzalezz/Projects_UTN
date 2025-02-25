package vianditasONG.modelos.repositorios.suscripciones;

import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ISuscripcionCantViandasMaxRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<SuscripcionCantViandasMax> buscarPorId(Long id);
    List<SuscripcionCantViandasMax> buscarTodos();
    void eliminarLogico(IPersistente persistente);
    List<SuscripcionCantViandasMax> buscarPorIdHumano(Long humanoId);

}
