package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;

import java.util.List;
import java.util.Optional;

public interface IDonacionesDeViandasRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<DonacionDeVianda> buscarPorId(Long id);
    List<DonacionDeVianda> buscarTodos();
    List<DonacionDeVianda> buscarTodosPorHumanoId(Long humanoId);
    void eliminarLogico(IPersistente persistente);
}
