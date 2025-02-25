package vianditasONG.modelos.repositorios.formularios.imp;

import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRespondidosRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class FormulariosRespondidosRepositorio extends RepositorioGenerico implements IFormulariosRespondidosRepositorio {
    @SuppressWarnings("unchecked")
    @Override
    public Optional<FormularioRespondido> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(FormularioRespondido.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FormularioRespondido> buscarTodos() {
        return entityManager()
                .createQuery("from " + FormularioRespondido.class.getName())
                .getResultList();
    }
}
