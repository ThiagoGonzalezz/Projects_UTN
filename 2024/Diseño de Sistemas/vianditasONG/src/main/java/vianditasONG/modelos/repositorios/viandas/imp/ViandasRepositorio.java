package vianditasONG.modelos.repositorios.viandas.imp;

import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.viandas.IViandasRepositorio;

import java.util.List;
import java.util.Optional;

public class ViandasRepositorio extends RepositorioGenerico implements IViandasRepositorio {

    public Optional<Vianda> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(Vianda.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<Vianda> buscarTodos(){
        return entityManager()
                .createQuery("from " + Vianda.class.getName())
                .getResultList();
    }

}
