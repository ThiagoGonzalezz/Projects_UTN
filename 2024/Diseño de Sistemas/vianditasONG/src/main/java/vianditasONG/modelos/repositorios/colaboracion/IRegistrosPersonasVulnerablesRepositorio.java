package vianditasONG.modelos.repositorios.colaboracion;

import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;

import java.util.List;
import java.util.Optional;

public interface IRegistrosPersonasVulnerablesRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<RegistroPersonasVulnerables> buscarPorId(Long id);
    List<RegistroPersonasVulnerables> buscarTodos();
    List<RegistroPersonasVulnerables> buscarTodosPorHumanoId(Long humanoId);
    void eliminarLogico(IPersistente persistente);
}
