package vianditasONG.modelos.repositorios.motivos_de_distribucion.imp;

import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.MotivoDeDistribucion;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.motivos_de_distribucion.IMotivosDeDistribucionRepositorio;

import java.util.List;
import java.util.Optional;

public class MotivosDeDistribucionRepositorio extends RepositorioGenerico implements IMotivosDeDistribucionRepositorio {

    @Override
    public Optional<MotivoDeDistribucion> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(MotivoDeDistribucion.class, id));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MotivoDeDistribucion> buscarTodos() {
        return entityManager()
                .createQuery("from " + MotivoDeDistribucion.class.getName())
                .getResultList();
    }

}
