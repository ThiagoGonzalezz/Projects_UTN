package vianditasONG.modelos.repositorios.contactos.imp;

import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.contactos.IContactosRepositorio;
import lombok.Builder;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
public class ContactosRepositorio extends RepositorioGenerico implements IContactosRepositorio {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Contacto> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Contacto.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contacto> buscarTodos() {
        return entityManager()
                .createQuery("from " + Contacto.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contacto> buscarPorIdHumano(Long humanoId) {
        try {
            return entityManager()
                    .createNativeQuery("SELECT * FROM contacto WHERE humano_id = :humanoId", Contacto.class)
                    .setParameter("humanoId", humanoId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<Long> buscarTecnicoId(String chatId) {
        try {
            Object result = entityManager()
                    .createNativeQuery("SELECT tecnico_id FROM contacto WHERE valorContacto = :chatId")
                    .setParameter("chatId", chatId)
                    .getSingleResult();

            if (result != null) {
                Long tecnicoId = ((Number) result).longValue();
                return Optional.of(tecnicoId);
            }
            return Optional.empty();
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar el técnico", e);
        }
    }

}

