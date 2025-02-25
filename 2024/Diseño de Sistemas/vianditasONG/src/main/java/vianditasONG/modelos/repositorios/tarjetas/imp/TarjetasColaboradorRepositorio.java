package vianditasONG.modelos.repositorios.tarjetas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasColaboradorRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class TarjetasColaboradorRepositorio extends RepositorioGenerico implements ITarjetasColaboradorRepositorio {
    public Optional<TarjetaColaborador> buscarPorCodigo(String codigo){
        return Optional.ofNullable(entityManager().find(TarjetaColaborador.class, codigo));
    }
    @SuppressWarnings("unchecked")
    public Optional<TarjetaColaborador> buscarPorHumano(Humano humano){
        return entityManager()
                .createQuery("from " + TarjetaColaborador.class.getName()+ " where humano_id = :humano_id", TarjetaColaborador.class)
                .setParameter("humano_id", humano.getId())
                .getResultList()
                .stream()
                .findFirst();
    }


    @SuppressWarnings("unchecked")
    public List<DistribucionDeVianda> buscarTodos(){
        return entityManager()
                .createQuery("from " + TarjetaColaborador.class.getName())
                .getResultList();
    }
}
