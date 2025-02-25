package vianditasONG.modelos.repositorios.formularios.imp;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.modelos.entities.formulario.Pregunta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.formularios.IPreguntasRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;


public class PreguntasRepositorio extends RepositorioGenerico implements IPreguntasRepositorio, WithSimplePersistenceUnit {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Pregunta> buscarPorId(Long id) {
        Pregunta pregunta =  entityManager().find(Pregunta.class, id);
        if (pregunta != null && pregunta.getActivo()) {
            return Optional.of(pregunta);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pregunta> buscarTodos() {
        return entityManager()
                .createQuery("from " + Pregunta.class.getName())
                .getResultList();
    }

    public Long generarId() {
        Long maxId = (Long) entityManager().createQuery("SELECT MAX(e.id) FROM Pregunta e").getSingleResult();
        return maxId == null ? 1L : maxId + 1;
    }
}