package vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp;

import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.IAccionesSolicitadasRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
@Builder
public class AccionesSolicitadasRepositorio extends RepositorioGenerico implements IAccionesSolicitadasRepositorio {

    @Override
    public Optional<AccionSolicitada> buscar(Long id) {
        return Optional.ofNullable(entityManager().find(AccionSolicitada.class, id));
    }

    @Override
    public List<AccionSolicitada> buscarTodos() {
        return entityManager()
                .createQuery("from " + AccionSolicitada.class.getName(), AccionSolicitada.class)
                .getResultList();
    }

}
