package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IDistribucionesDeViandasRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class DistribucionesDeViandasRepositorio extends RepositorioGenerico implements IDistribucionesDeViandasRepositorio {

    public Optional<DistribucionDeVianda> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(DistribucionDeVianda.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<DistribucionDeVianda> buscarTodos(){
        return entityManager()
                .createQuery("from " + DistribucionDeVianda.class.getName())
                .getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<DistribucionDeVianda> buscarTodosPorHumanoId(Long humanoId) {
        return entityManager()
                .createQuery("from " + DistribucionDeVianda.class.getName() + " d where d.colaborador.id = :humanoId and d.activo = true")
                .setParameter("humanoId", humanoId)
                .getResultList();
    }

}
