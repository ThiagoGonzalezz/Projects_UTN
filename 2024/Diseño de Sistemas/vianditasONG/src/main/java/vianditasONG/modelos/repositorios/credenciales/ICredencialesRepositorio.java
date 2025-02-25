package vianditasONG.modelos.repositorios.credenciales;

import vianditasONG.utils.IPersistente;
import vianditasONG.utils.creadorDeCredenciales.Credencial;

import java.util.List;
import java.util.Optional;


public interface ICredencialesRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Credencial> buscarPorId(String id);
    List<Credencial> buscarTodos();
}
