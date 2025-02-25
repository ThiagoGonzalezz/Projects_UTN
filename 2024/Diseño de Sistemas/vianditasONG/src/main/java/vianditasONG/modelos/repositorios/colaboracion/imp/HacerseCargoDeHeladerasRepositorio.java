package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IHacerseCargoDeHeladerasRepositorio;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Builder
public class HacerseCargoDeHeladerasRepositorio extends RepositorioGenerico implements IHacerseCargoDeHeladerasRepositorio {

    @Override
    public Optional<HacerseCargoDeHeladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(HacerseCargoDeHeladera.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HacerseCargoDeHeladera> buscarTodos() {

        return entityManager()
                .createQuery("from " + HacerseCargoDeHeladera.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public Optional<HacerseCargoDeHeladera> buscarPorIdHeladera(Long heladeraId) {
        try {
            HacerseCargoDeHeladera resultado = (HacerseCargoDeHeladera) entityManager()
                    .createQuery("from " + HacerseCargoDeHeladera.class.getName() + " h where h.heladera.id = :heladeraId")
                    .setParameter("heladeraId", heladeraId)
                    .getSingleResult();
            return Optional.of(resultado);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public List<HacerseCargoDeHeladera> buscarTodosPorPersonaJuridicaId(Long personaJuridicaId) {
        return entityManager()
                .createQuery("from " + HacerseCargoDeHeladera.class.getName() + " d where d.colaborador.id = :pjId")
                .setParameter("pjId", personaJuridicaId)
                .getResultList();
    }
}