package vianditasONG.modelos.repositorios.sensores.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeMovimientoRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class SensoresDeMovimientoRepositorio extends RepositorioGenerico implements ISensoresDeMovimientoRepositorio {

    @Override
    public Optional<SensorDeMovimiento> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(SensorDeMovimiento.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SensorDeMovimiento> buscarTodos() {
        return entityManager()
                .createQuery("from " + SensorDeMovimiento.class.getName())
                .getResultList();
    }
}
