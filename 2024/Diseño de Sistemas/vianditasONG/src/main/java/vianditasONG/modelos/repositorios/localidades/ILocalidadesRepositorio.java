package vianditasONG.modelos.repositorios.localidades;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;

import java.util.List;
import java.util.Optional;

public interface ILocalidadesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);
    Optional<Localidad> buscarPorId(Long id);
    List<Localidad> buscarTodosPorProvincia(Provincia provincia);
    List<Localidad> buscarTodos();
}

