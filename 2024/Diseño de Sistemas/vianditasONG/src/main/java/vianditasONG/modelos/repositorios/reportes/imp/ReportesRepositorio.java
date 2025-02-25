package vianditasONG.modelos.repositorios.reportes.imp;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.reportes.IReportesRepositorio;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.utils.IPersistente;

import java.util.List;
import java.util.Optional;

@Builder
public class ReportesRepositorio extends RepositorioGenerico implements IReportesRepositorio, WithSimplePersistenceUnit {
    @Override
    public Optional<Reporte> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Reporte.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Reporte> buscarTodos() {
        return entityManager()
                .createQuery("from " + Reporte.class.getName())
                .getResultList();
    }

    @Override
    public void eliminarTodosLogico() {
        List<Reporte> reportes = this.buscarTodos();
        reportes.forEach(reporte -> reporte.setActivo(false));
        reportes.forEach(this::actualizar);
    }


}
