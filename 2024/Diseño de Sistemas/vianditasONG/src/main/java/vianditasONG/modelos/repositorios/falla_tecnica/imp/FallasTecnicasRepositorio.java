package vianditasONG.modelos.repositorios.falla_tecnica.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.falla_tecnica.IFallasTecnicasRepositorio;

import java.util.List;
import java.util.Optional;
@Builder
public class FallasTecnicasRepositorio extends RepositorioGenerico implements IFallasTecnicasRepositorio {

    private List<FallaTecnica> fallas;

    public Optional<FallaTecnica> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(FallaTecnica.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<FallaTecnica> buscarTodos(){
        return entityManager()
                .createQuery("from " + FallaTecnica.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<FallaTecnica> buscarFallasPorHumano(Long usuarioId) {
        return entityManager()
                .createQuery("from " + FallaTecnica.class.getName() + " f where f.colaborador.id = :usuarioId")
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

}

