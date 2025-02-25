package vianditasONG.modelos.repositorios.aperturas.aperturasFallidas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.IntentoAperturaFallida;

import java.util.List;
import java.util.Optional;

public interface IAperturasFallidasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<IntentoAperturaFallida> buscarPorId(Long id);
    List<IntentoAperturaFallida> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
