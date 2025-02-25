package vianditasONG.modelos.repositorios.suscripciones;

import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ISuscripcionCantViandasMinRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<SuscripcionCantViandasMin> buscarPorId(Long id);
    List<SuscripcionCantViandasMin> buscarTodos();
    void eliminarLogico(IPersistente persistente);
    List<SuscripcionCantViandasMin> buscarPorIdHumano(Long humanoId);

}
