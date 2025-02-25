package vianditasONG.modelos.repositorios.personas_juridicas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class PersonasJuridicasRepositorio extends RepositorioGenerico implements IPersonasJuridicasRepositorio {

    @Override
    public Optional<PersonaJuridica> buscarPorId(Long id) {
        PersonaJuridica persona = entityManager().find(PersonaJuridica.class, id);
        if (persona != null && persona.getActivo()) {
            return Optional.of(persona);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonaJuridica> buscarTodos() {
     return entityManager()
                .createQuery("from " + PersonaJuridica.class.getName()  + " where activo = 1")
                .getResultList();
    }

    @Override
    public Optional<PersonaJuridica> buscarPorUsuario(Long userId) {
        return entityManager()
                .createQuery("from " + PersonaJuridica.class.getName()+ " where usuario_id = :userId and activo = 1", PersonaJuridica.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> obtenerEstadisticasHeladerasActivas() {
        return entityManager()
                .createQuery("SELECT h.colaborador.id, COUNT(h.heladera.id) AS cantHeladerasActivas, " +
                        "SUM(h.heladera.mesesActiva) AS totalMesesActiva, h, heladera " +
                        "FROM HacerseCargoDeHeladera h " +
                        "JOIN h.heladera heladera " +
                        "WHERE heladera.estadoHeladera = 0 " +
                        "GROUP BY h.colaborador.id, h, heladera")
                .getResultList();
    }

}

