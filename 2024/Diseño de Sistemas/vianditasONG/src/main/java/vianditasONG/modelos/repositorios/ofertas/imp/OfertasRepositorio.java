package vianditasONG.modelos.repositorios.ofertas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.ofertas.IOfertasRepositorio;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Builder
public class OfertasRepositorio extends RepositorioGenerico implements IOfertasRepositorio {

    @Override
    public Optional<Oferta> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Oferta.class, id));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Oferta> buscarTodos() {
        return entityManager()
                .createQuery("from " + Oferta.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Oferta> buscarTodosActivos() {
        return entityManager()
                .createQuery("from " + Oferta.class.getName() + " o where o.activo = true")
                .getResultList();
    }


    @Override
    public void eliminarPorId(Long id) {

        Oferta oferta = buscarPorId(id).orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada"));

        entityManager().remove(oferta);

    }

    @SuppressWarnings("unchecked")
    public List<Oferta> buscarTodosPorPersonaJuridicaId(Long personaJuridicaId) {
        return entityManager()
                .createQuery("from " + Oferta.class.getName() + " d where d.ofertante.id = :pjId")
                .setParameter("pjId", personaJuridicaId)
                .getResultList();
    }

}

