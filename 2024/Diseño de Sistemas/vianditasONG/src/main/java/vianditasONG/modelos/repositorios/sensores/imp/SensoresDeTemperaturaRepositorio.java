package vianditasONG.modelos.repositorios.sensores.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeTemperaturaRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class SensoresDeTemperaturaRepositorio extends RepositorioGenerico implements ISensoresDeTemperaturaRepositorio {

    @Override
    public Optional<SensorDeTemperatura> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(SensorDeTemperatura.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SensorDeTemperatura> buscarTodos() {
        return entityManager()
                .createQuery("from " + SensorDeTemperatura.class.getName())
                .getResultList();
    }
}
