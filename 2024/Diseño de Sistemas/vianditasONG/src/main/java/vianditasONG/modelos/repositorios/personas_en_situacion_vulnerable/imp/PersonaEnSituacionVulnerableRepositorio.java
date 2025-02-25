package vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.IPersonasEnSituacionVulnerableRepositorio;

import java.util.List;
import java.util.Optional;

@Builder
public class PersonaEnSituacionVulnerableRepositorio extends RepositorioGenerico implements IPersonasEnSituacionVulnerableRepositorio {

    @Override
    public Optional<PersonaEnSituacionVulnerable> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(PersonaEnSituacionVulnerable.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonaEnSituacionVulnerable> buscarTodos() {
        return entityManager()
                .createQuery("from " + PersonaEnSituacionVulnerable.class.getName())
                .getResultList();
    }
}
