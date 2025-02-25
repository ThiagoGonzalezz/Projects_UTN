package vianditasONG.modelos.repositorios.tarjetas;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.RegistroUsoTarjeta;

import java.util.List;
import java.util.Optional;

public interface IRegistroUsoTarjeta {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<RegistroUsoTarjeta> buscarPorId(Long id);
    List<RegistroUsoTarjeta> buscarTodos();
    void eliminarLogico(IPersistente persistente);
}
