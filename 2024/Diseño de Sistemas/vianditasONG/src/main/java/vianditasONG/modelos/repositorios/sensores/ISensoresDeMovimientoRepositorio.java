package vianditasONG.modelos.repositorios.sensores;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;

import java.util.List;
import java.util.Optional;

public interface ISensoresDeMovimientoRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<SensorDeMovimiento> buscarPorId(Long id);
    List<SensorDeMovimiento> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
