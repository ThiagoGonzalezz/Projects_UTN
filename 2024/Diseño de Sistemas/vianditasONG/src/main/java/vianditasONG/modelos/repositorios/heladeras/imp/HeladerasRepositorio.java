package vianditasONG.modelos.repositorios.heladeras.imp;

import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import lombok.Builder;
import vianditasONG.utils.IPersistente;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class HeladerasRepositorio extends RepositorioGenerico implements IHeladerasRepositorio {

    @SuppressWarnings("unchecked")
    public Optional<Heladera> buscar(Long id) {
        return Optional.ofNullable(entityManager().find(Heladera.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Heladera> buscarTodos() {

        List<Heladera> heladeras = entityManager()
                .createQuery("from " + Heladera.class.getName(), Heladera.class)
                .getResultList();


        return heladeras.stream()
                .filter(Heladera::isActivo)  // Filtra las que tienen el campo 'activo' en true
                .collect(Collectors.toList());
    }


}