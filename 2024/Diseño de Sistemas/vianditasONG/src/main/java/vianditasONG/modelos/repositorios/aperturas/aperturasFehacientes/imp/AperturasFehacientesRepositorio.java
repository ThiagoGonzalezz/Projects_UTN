package vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.imp;

import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AperturaFehaciente;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.IAperturasFehacientesRepositorio;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class AperturasFehacientesRepositorio extends RepositorioGenerico implements IAperturasFehacientesRepositorio {

    public Optional<AperturaFehaciente> buscar(Long id) {
        return Optional.ofNullable(entityManager().find(AperturaFehaciente.class, id));
    }

    @Override
    public List<AperturaFehaciente> buscarTodos() {
        return entityManager()
                .createQuery("from " + AperturaFehaciente.class.getName(), AperturaFehaciente.class)
                .getResultList();
    }

}
