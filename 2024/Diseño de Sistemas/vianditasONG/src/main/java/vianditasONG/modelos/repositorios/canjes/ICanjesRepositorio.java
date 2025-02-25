package vianditasONG.modelos.repositorios.canjes;

import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;

import java.util.List;
import java.util.Optional;

public interface ICanjesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Canje> buscarPorId(Long id);
    List<Canje> buscarTodos();
    List<Canje> buscarTodosPorHumanoId(Long humanoId);
    List<Canje> buscarTodosPorPersonaJuridicaId(Long humanoId);
    void eliminarLogico(IPersistente persistente);
}
