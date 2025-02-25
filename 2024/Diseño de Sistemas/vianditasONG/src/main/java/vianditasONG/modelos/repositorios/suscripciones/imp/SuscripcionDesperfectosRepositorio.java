package vianditasONG.modelos.repositorios.suscripciones.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
public class SuscripcionDesperfectosRepositorio extends RepositorioGenerico implements ISuscripcionDesperfectosRepositorio {

    public Optional<SuscripcionDesperfectos> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(SuscripcionDesperfectos.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SuscripcionDesperfectos> buscarPorIdHumano(Long humanoId) {
        try {
            return entityManager()
                    .createNativeQuery("SELECT * FROM suscripcion_desperfectos WHERE humano_id = :humanoId", SuscripcionDesperfectos.class)
                    .setParameter("humanoId", humanoId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<SuscripcionDesperfectos> buscarTodos(){
        return entityManager()
                .createQuery("from " + SuscripcionDesperfectos.class.getName())
                .getResultList();
    }

}
