package vianditasONG.modelos.repositorios.credenciales.imp;


import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import vianditasONG.modelos.repositorios.credenciales.ICredencialesRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class CredencialesRepositorio extends RepositorioGenerico implements ICredencialesRepositorio {
    @SuppressWarnings("unchecked")
    @Override
    public Optional<Credencial> buscarPorId(String id) {
        return Optional.ofNullable(entityManager().find(Credencial.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Credencial> buscarTodos() {
        return entityManager()
                .createQuery("from " + Credencial.class.getName())
                .getResultList();
    }
}

