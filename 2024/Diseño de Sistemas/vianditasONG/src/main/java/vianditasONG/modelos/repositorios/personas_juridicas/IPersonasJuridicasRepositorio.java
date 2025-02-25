package vianditasONG.modelos.repositorios.personas_juridicas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;

import java.util.List;
import java.util.Optional;

public interface IPersonasJuridicasRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<PersonaJuridica> buscarPorId(Long id);
    List<PersonaJuridica> buscarTodos();
    void eliminarLogico(IPersistente persistente);

    Optional<PersonaJuridica> buscarPorUsuario(Long userId);
    List<Object[]> obtenerEstadisticasHeladerasActivas();
}