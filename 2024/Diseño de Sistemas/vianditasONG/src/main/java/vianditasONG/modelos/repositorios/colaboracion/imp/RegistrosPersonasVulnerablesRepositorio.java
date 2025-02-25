package vianditasONG.modelos.repositorios.colaboracion.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.colaboracion.IRegistrosPersonasVulnerablesRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class RegistrosPersonasVulnerablesRepositorio extends RepositorioGenerico implements IRegistrosPersonasVulnerablesRepositorio {
    public Optional<RegistroPersonasVulnerables> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(RegistroPersonasVulnerables.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<RegistroPersonasVulnerables> buscarTodos(){
        return entityManager()
                .createQuery("from " + RegistroPersonasVulnerables.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<RegistroPersonasVulnerables> buscarTodosPorHumanoId(Long humanoId) {
        return entityManager()
                .createQuery("from " + RegistroPersonasVulnerables.class.getName() + " d where d.colaborador.id = :humanoId")
                .setParameter("humanoId", humanoId)
                .getResultList();
    }
}
