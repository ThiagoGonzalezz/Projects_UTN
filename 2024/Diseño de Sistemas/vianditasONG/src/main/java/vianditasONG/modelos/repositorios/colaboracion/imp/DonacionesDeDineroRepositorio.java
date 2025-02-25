package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeDineroRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class DonacionesDeDineroRepositorio extends RepositorioGenerico implements IDonacionesDeDineroRepositorio {
    public Optional<DonacionDeDinero> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(DonacionDeDinero.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<DonacionDeDinero> buscarTodos(){
        return entityManager()
                .createQuery("from " + DonacionDeDinero.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<DonacionDeDinero> buscarTodosPorHumanoId(Long humanoId) {
        return entityManager()
                .createQuery("from " + DonacionDeDinero.class.getName() + " d where d.humano.id = :humanoId")
                .setParameter("humanoId", humanoId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<DonacionDeDinero> buscarTodosPorPersonaJuridicaId(Long personaJuridicaId) {
        return entityManager()
                .createQuery("from " + DonacionDeDinero.class.getName() + " d where d.personaJuridica.id = :pjId")
                .setParameter("pjId", personaJuridicaId)
                .getResultList();
    }
}
