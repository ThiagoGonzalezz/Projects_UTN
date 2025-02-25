package vianditasONG.modelos.repositorios.sensores;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;

import java.util.List;
import java.util.Optional;

public interface ISensoresDeTemperaturaRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<SensorDeTemperatura> buscarPorId(Long id);
    List<SensorDeTemperatura> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
