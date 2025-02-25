package vianditasONG.modelos.repositorios.rubros;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;

import java.util.List;
import java.util.Optional;

public interface IRubrosPersonaJuridicaRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<RubroPersonaJuridica> buscarPorId(Long id);
    List<RubroPersonaJuridica> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
