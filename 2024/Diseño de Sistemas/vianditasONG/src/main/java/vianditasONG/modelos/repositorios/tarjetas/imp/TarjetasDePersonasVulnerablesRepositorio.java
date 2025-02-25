package vianditasONG.modelos.repositorios.tarjetas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasDePersonasVulnerablesRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class TarjetasDePersonasVulnerablesRepositorio extends RepositorioGenerico implements ITarjetasDePersonasVulnerablesRepositorio {

    @Override
    public Optional<TarjetaDePersonaVulnerable> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(TarjetaDePersonaVulnerable.class, id));
    }

    @Override
    public Optional<TarjetaDePersonaVulnerable> buscarPorCodigo(String codigo){
        return entityManager()
                .createQuery("from " + TarjetaDePersonaVulnerable.class.getName()+ " where codigo = :codigo", TarjetaDePersonaVulnerable.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TarjetaDePersonaVulnerable> buscarTodos() {
        return entityManager()
                .createQuery("from " + TarjetaDePersonaVulnerable.class.getName())
                .getResultList();
    }
}

