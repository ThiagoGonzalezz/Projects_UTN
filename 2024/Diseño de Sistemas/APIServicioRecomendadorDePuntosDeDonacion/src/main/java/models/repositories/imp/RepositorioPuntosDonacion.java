package models.repositories.imp;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.repositories.IRepositorioPuntosDonacion;

import java.util.List;

@Builder
public class RepositorioPuntosDonacion implements WithSimplePersistenceUnit, IRepositorioPuntosDonacion {

    @Override
    @SuppressWarnings("unchecked")
    public List<PuntoDeDonacion> buscarTodos() {
        return entityManager()
                .createQuery("from " + PuntoDeDonacion.class.getName())
                .getResultList();
    }

    @Override
    public void guardar(PuntoDeDonacion puntoDeDonacion){
        entityManager().persist(puntoDeDonacion);
    }
    @Override
    public void actualizar(PuntoDeDonacion puntoDeDonacion){
        entityManager().merge(puntoDeDonacion);
    }
    @Override
    public void eliminarLogico(PuntoDeDonacion puntoDeDonacion){
        puntoDeDonacion.setActivo(false);
    }

}
