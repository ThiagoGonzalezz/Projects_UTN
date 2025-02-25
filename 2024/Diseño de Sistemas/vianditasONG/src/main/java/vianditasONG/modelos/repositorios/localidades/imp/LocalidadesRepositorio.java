package vianditasONG.modelos.repositorios.localidades.imp;

import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class LocalidadesRepositorio extends RepositorioGenerico implements ILocalidadesRepositorio {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Localidad> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Localidad.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Localidad> buscarTodosPorProvincia(Provincia provincia) {

        return  entityManager()
                .createQuery("from " + Localidad.class.getName() + " where provincia = :provincia and activo = 1")
                .setParameter("provincia",provincia)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Localidad> buscarTodos() {
        return entityManager()
                .createQuery("from " + Localidad.class.getName() + " where activo = 1")
                .getResultList();
    }

}