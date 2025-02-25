package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;

import java.util.List;
import java.util.Optional;

public interface IDistribucionesDeViandasRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<DistribucionDeVianda> buscarPorId(Long id);
    List<DistribucionDeVianda> buscarTodos();
    List<DistribucionDeVianda> buscarTodosPorHumanoId(Long humanoId);
    void eliminarLogico(IPersistente persistente);

}
