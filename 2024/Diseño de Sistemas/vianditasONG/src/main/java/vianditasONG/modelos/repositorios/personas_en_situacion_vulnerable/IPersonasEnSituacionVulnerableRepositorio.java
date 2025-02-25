package vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;

import java.util.List;
import java.util.Optional;

public interface IPersonasEnSituacionVulnerableRepositorio {
    void guardar(Object objeto);

    void actualizar(Object objeto);

    Optional<PersonaEnSituacionVulnerable> buscarPorId(Long id);

    List<PersonaEnSituacionVulnerable> buscarTodos();

    void eliminarLogico(IPersistente persistente);
}