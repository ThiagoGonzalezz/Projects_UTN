package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;

import java.util.List;
import java.util.Optional;

public interface IHacerseCargoDeHeladerasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<HacerseCargoDeHeladera> buscarPorId(Long id);
    List<HacerseCargoDeHeladera> buscarTodos();
    void eliminarLogico(IPersistente persistente);

    List<HacerseCargoDeHeladera> buscarTodosPorPersonaJuridicaId(Long pjId);
}
