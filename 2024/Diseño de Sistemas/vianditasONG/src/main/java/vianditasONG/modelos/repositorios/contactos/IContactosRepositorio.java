package vianditasONG.modelos.repositorios.contactos;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

import java.util.List;
import java.util.Optional;

public interface IContactosRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Contacto> buscarPorId(Long id);
    List<Contacto> buscarTodos();
    List<Contacto> buscarPorIdHumano(Long idHumano);
    Optional<Long> buscarTecnicoId(String idChat);
}
