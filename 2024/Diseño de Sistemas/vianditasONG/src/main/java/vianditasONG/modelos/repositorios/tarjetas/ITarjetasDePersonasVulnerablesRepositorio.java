package vianditasONG.modelos.repositorios.tarjetas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;

import java.util.List;
import java.util.Optional;

public interface ITarjetasDePersonasVulnerablesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<TarjetaDePersonaVulnerable> buscarPorId(Long id);

    Optional<TarjetaDePersonaVulnerable> buscarPorCodigo(String codigo);
    List<TarjetaDePersonaVulnerable> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
