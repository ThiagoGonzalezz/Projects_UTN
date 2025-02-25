package vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AperturaFehaciente;

import java.util.List;
import java.util.Optional;

public interface IAperturasFehacientesRepositorio {

    Optional<AperturaFehaciente> buscar(Long id);
    List<AperturaFehaciente> buscarTodos();
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);

}
