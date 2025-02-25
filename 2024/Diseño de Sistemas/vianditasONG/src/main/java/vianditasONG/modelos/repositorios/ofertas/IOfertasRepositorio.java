package vianditasONG.modelos.repositorios.ofertas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;

import java.util.List;
import java.util.Optional;

public interface IOfertasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Oferta> buscarPorId(Long id);
    List<Oferta> buscarTodos();
    List<Oferta> buscarTodosActivos();
    void eliminarLogico(IPersistente persistente);
    void eliminarPorId(Long id);

    List<Oferta> buscarTodosPorPersonaJuridicaId(Long personaJuridicaId);

}

