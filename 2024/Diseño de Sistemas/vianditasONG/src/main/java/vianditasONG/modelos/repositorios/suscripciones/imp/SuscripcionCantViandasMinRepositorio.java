package vianditasONG.modelos.repositorios.suscripciones.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMinRepositorio;

import javax.persistence.Access;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
public class SuscripcionCantViandasMinRepositorio extends RepositorioGenerico implements ISuscripcionCantViandasMinRepositorio {

    public Optional<SuscripcionCantViandasMin> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(SuscripcionCantViandasMin.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<SuscripcionCantViandasMin> buscarTodos(){
        return entityManager()
                .createQuery("from " + SuscripcionCantViandasMin.class.getName())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SuscripcionCantViandasMin> buscarPorIdHumano(Long humanoId) {
        try {
            return entityManager()
                    .createNativeQuery("SELECT * FROM suscripcion_cant_viandas_min WHERE humano_id = :humanoId", SuscripcionCantViandasMin.class)
                    .setParameter("humanoId", humanoId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
