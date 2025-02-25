package vianditasONG.modelos.repositorios.formularios.imp;

import vianditasONG.modelos.entities.formulario.Respuesta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.formularios.IRespuestasRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class RespuestasRepositorio extends RepositorioGenerico implements IRespuestasRepositorio {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Respuesta> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Respuesta.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Respuesta> buscarTodos() {
        return entityManager()
                .createQuery("from " + Respuesta.class.getName())
                .getResultList();
    }
}

