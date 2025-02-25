package vianditasONG.modelos.repositorios.modelos.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.modelos.IModelosRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class ModelosRepositorio extends RepositorioGenerico implements IModelosRepositorio {


    @Override
    public Optional<Modelo> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Modelo.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Modelo> buscarTodos() {
        return entityManager()
                .createQuery("from " + Modelo.class.getName())
                .getResultList();
    }

    public Optional<Modelo> buscarPorDescripcion(String descripcion) {
        List<Modelo> modelos = entityManager()
                .createQuery("FROM Modelo WHERE descripcion = :descripcion", Modelo.class)
                .setParameter("descripcion", descripcion)
                .getResultList();

        // Si encuentras al menos un modelo, devuelve el primero como Optional
        return modelos.stream().findFirst();
    }


}
