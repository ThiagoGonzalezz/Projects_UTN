package vianditasONG.modelos.repositorios.formularios.imp;

import org.hibernate.mapping.Formula;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

public class FormulariosRepositorio extends RepositorioGenerico implements IFormulariosRepositorio {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Formulario> buscarPorId(Long id) {
        Formulario formulario = entityManager().find(Formulario.class, id);
        if (formulario != null && formulario.getActivo()) {
            return Optional.of(formulario);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Formulario> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("from " + Formulario.class.getName()+ " where nombre = :nombre and activo = 1", Formulario.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Formulario> buscarTodos() {
        return entityManager()
                .createQuery("from " + Formulario.class.getName() + " where activo = 1")
                .getResultList();
    }

}

