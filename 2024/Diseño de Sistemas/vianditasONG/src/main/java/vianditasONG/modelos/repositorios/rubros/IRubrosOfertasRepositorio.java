package vianditasONG.modelos.repositorios.rubros;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;

import java.util.List;
import java.util.Optional;

public interface IRubrosOfertasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<RubroOferta> buscarPorId(Long id);
    List<RubroOferta> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}

