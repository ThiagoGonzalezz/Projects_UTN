package vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.IntentoAperturaFallida;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.IAperturasFallidasRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class AperturasFallidasRepositorio extends RepositorioGenerico implements IAperturasFallidasRepositorio {


    @Override
    public Optional<IntentoAperturaFallida> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(IntentoAperturaFallida.class, id));

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IntentoAperturaFallida> buscarTodos() {
        return entityManager()
                .createQuery("from " + IntentoAperturaFallida.class.getName())
                .getResultList();
    }
}
