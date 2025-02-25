package vianditasONG.modelos.repositorios.formularios.imp;

import vianditasONG.modelos.entities.formulario.Opcion;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.formularios.IOpcionesRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class OpcionesRepositorio extends RepositorioGenerico implements IOpcionesRepositorio {
    @SuppressWarnings("unchecked")
    @Override
    public Optional<Opcion> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Opcion.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Opcion> buscarTodos() {
        return entityManager()
                .createQuery("from " + Opcion.class.getName())
                .getResultList();
    }
}
