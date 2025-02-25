package vianditasONG.modelos.servicios.reportes;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;

public class ReportadorMain implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        GeneradorDeEstadisticas generadorDeEstadisticas = new GeneradorDeEstadisticas();
        ReportesRepositorio reportesRepositorio = ReportesRepositorio.builder().build();
        Reportador reportador = new Reportador(generadorDeEstadisticas, reportesRepositorio);

        reportador.desactivarReportesViejos();
        reportador.generarReporteIncidentesPorHeladera();
        reportador.generarReporteDonacionesPorColaborador();
        reportador.generarReporteViandasPorHeladera();

        System.out.println("Reportes generados exitosamente.");
    }
}
