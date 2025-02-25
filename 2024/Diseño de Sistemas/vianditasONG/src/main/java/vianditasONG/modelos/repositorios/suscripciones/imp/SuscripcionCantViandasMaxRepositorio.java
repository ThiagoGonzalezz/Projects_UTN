package vianditasONG.modelos.repositorios.suscripciones.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMaxRepositorio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
public class SuscripcionCantViandasMaxRepositorio extends RepositorioGenerico implements ISuscripcionCantViandasMaxRepositorio {

    public Optional<SuscripcionCantViandasMax> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(SuscripcionCantViandasMax.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<SuscripcionCantViandasMax> buscarTodos(){
        return entityManager()
                .createQuery("from " + SuscripcionCantViandasMax.class.getName())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SuscripcionCantViandasMax> buscarPorIdHumano(Long humanoId) {
        try {
            return entityManager()
                    .createNativeQuery("SELECT * FROM suscripcion_cant_viandas_max WHERE humano_id = :humanoId", SuscripcionCantViandasMax.class)
                    .setParameter("humanoId", humanoId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
