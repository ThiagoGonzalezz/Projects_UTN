package vianditasONG.controllers;

import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterReportesDTO;
import vianditasONG.dtos.outputs.ReporteOutputDTO;
import vianditasONG.modelos.repositorios.reportes.IReportesRepositorio;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.utils.ICrudViewsHandler;
import vianditasONG.utils.Persistente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class ReportesController implements ICrudViewsHandler {

    @Builder.Default
    private IReportesRepositorio reportesRepositorio = ServiceLocator.getService(ReportesRepositorio.class);
    @Builder.Default
    private ConverterReportesDTO converterReportesDTO = ServiceLocator.getService(ConverterReportesDTO.class);

    @Override
    public void create(Context context) {
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void index(Context context) {

        List<Reporte> reportes = this.reportesRepositorio.buscarTodos().stream().filter(Persistente::isActivo).toList();

        if (reportes == null || reportes.isEmpty()) {
            throw new IllegalStateException("La lista de reportes está vacía o es nula");
        }

        List<ReporteOutputDTO> reportesDTO = reportes
                .stream()
                .map(converterReportesDTO::convertToDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("reportes", reportesDTO);

        context.render("admin/visualizacion_reportes.hbs", model);
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
