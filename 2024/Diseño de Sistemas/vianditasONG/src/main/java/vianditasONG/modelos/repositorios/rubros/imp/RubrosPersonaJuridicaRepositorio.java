package vianditasONG.modelos.repositorios.rubros.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.rubros.IRubrosPersonaJuridicaRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class RubrosPersonaJuridicaRepositorio extends RepositorioGenerico implements IRubrosPersonaJuridicaRepositorio {
    @Override
    public Optional<RubroPersonaJuridica> buscarPorId(Long id) {
        RubroPersonaJuridica rubro = entityManager().find(RubroPersonaJuridica.class, id);
        if (rubro != null && rubro.getActivo()) {
            return Optional.of(rubro);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RubroPersonaJuridica> buscarTodos() {
        return entityManager()
                .createQuery("from " + RubroPersonaJuridica.class.getName() + " where activo = 1")
                .getResultList();
    }
}
