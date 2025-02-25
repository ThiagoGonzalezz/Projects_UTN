package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class DonacionesDeViandasRepositorio extends RepositorioGenerico implements IDonacionesDeViandasRepositorio {
    public Optional<DonacionDeVianda> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(DonacionDeVianda.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<DonacionDeVianda> buscarTodos() {
        return entityManager()
                .createQuery("from " + DonacionDeVianda.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<DonacionDeVianda> buscarTodosPorHumanoId(Long humanoId) {
        return entityManager()
                .createQuery("from " + DonacionDeVianda.class.getName() + " d where d.colaborador.id = :humanoId and d.activo = true")
                .setParameter("humanoId", humanoId)
                .getResultList();
    }

}
