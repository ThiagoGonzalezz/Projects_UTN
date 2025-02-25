package vianditasONG.modelos.repositorios.canjes.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.canjes.ICanjesRepositorio;

import javax.persistence.Basic;
import java.util.List;
import java.util.Optional;

@Builder
public class CanjesRepositorio extends RepositorioGenerico implements ICanjesRepositorio {

    @Override
    public Optional<Canje> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Canje.class, id));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Canje> buscarTodos() {
        return entityManager()
                .createQuery("from " + Canje.class.getName())
                .getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Canje> buscarTodosPorHumanoId(Long humanoId) {
        return entityManager()
                .createQuery("from " + Canje.class.getName() + " d where d.canjeadorHumano.id = :humanoId")
                .setParameter("humanoId", humanoId)
                .getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Canje> buscarTodosPorPersonaJuridicaId(Long personaJuridicaId) {
        return entityManager()
                .createQuery("from " + Canje.class.getName() + " d where d.canjeadorPersonaJuridica.id = :pjId")
                .setParameter("pjId", personaJuridicaId)
                .getResultList();
    }

}
