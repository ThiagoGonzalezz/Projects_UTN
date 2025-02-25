package vianditasONG.modelos.repositorios.tarjetas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.RegistroUsoTarjeta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.tarjetas.IRegistroUsoTarjeta;

import java.util.List;
import java.util.Optional;

@Builder
public class RegistroUsoTarjetaRepositorio extends RepositorioGenerico implements IRegistroUsoTarjeta {
    @Override
    public Optional<RegistroUsoTarjeta> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(RegistroUsoTarjeta.class, id));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroUsoTarjeta> buscarTodos() {
        return entityManager()
                .createQuery("from " + RegistroUsoTarjetaRepositorio.class.getName())
                .getResultList();
    }
}
