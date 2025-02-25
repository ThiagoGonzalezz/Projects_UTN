package vianditasONG.modelos.repositorios.tecnicos.imp;

import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.tecnicos.ITecnicosRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class TecnicosRepositorio extends RepositorioGenerico implements ITecnicosRepositorio {
    @SuppressWarnings("unchecked")
    @Override
    public Optional<Tecnico> buscarPorCuil(String cuil) {
        List<Tecnico> tecnicos = entityManager()
                .createQuery("FROM Tecnico WHERE cuil = :cuil", Tecnico.class)
                .setParameter("cuil", cuil)
                .getResultList();

        // Devuelve el primer técnico encontrado como Optional
        return tecnicos.stream().findFirst();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Tecnico> buscarTodos() {
        return entityManager()
                .createQuery("from " + Tecnico.class.getName())
                .getResultList();
    }



    @SuppressWarnings("unchecked")
    @Override
    public Optional<Tecnico> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Tecnico.class, id));
    }
}
