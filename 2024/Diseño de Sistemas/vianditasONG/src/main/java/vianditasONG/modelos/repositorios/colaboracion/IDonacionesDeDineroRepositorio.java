package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;

import java.util.List;
import java.util.Optional;

public interface IDonacionesDeDineroRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<DonacionDeDinero> buscarPorId(Long id);
    List<DonacionDeDinero> buscarTodos();
    List<DonacionDeDinero> buscarTodosPorHumanoId(Long humanoId);
    List<DonacionDeDinero> buscarTodosPorPersonaJuridicaId(Long humanoId);
    void eliminarLogico(IPersistente persistente);

}
