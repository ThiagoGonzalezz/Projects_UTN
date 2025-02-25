package vianditasONG.modelos.repositorios.reportes;

import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.utils.IPersistente;

import java.util.List;
import java.util.Optional;

public interface IReportesRepositorio {
    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<Reporte> buscarPorId(Long id);
    List<Reporte> buscarTodos();
    void eliminarLogico(IPersistente persistente);
    void eliminarTodosLogico();
}
