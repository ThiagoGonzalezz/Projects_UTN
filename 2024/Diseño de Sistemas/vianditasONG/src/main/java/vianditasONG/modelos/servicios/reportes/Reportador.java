package vianditasONG.modelos.servicios.reportes;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Reportador implements WithSimplePersistenceUnit {
    private final GeneradorDeEstadisticas generadorDeEstadisticas;
    private final ReportesRepositorio reportesRepositorio;
    public Reportador(GeneradorDeEstadisticas generadorDeEstadisticas, ReportesRepositorio reportesRepositorio) {
        this.generadorDeEstadisticas = generadorDeEstadisticas;
        this.reportesRepositorio = reportesRepositorio;
    }
    public void generarReporteIncidentesPorHeladera() {
        List<Object[]> resultados = generadorDeEstadisticas.contarIncidentesPorHeladeraSemanaActual();
        List<String> detalles = resultados.stream()
                .map(result -> "Heladera: " + result[0] + ", Incidentes: " + result[1])
                .collect(Collectors.toList());

        Reporte reporte = Reporte.builder()
                .fecha(LocalDate.now())
                .tipoReporte(TipoReporte.FALLAS_POR_HELADERA)
                .nombre("Reporte de Incidentes por Heladera")
                .detalles(detalles)
                .build();

        try {
            String rutaPdf = GeneradorDePDF.generarPDF(reporte);
            reporte.setRutaPdf(rutaPdf);
            withTransaction(() -> {
                reportesRepositorio.guardar(reporte);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteDonacionesPorColaborador() {
        List<Object[]> resultados = generadorDeEstadisticas.contarDonacionesPorColaborador();
        List<String> detalles = resultados.stream()
                .map(result -> "Colaborador: " + result[0] + ", Donaciones: " + result[1])
                .collect(Collectors.toList());

        Reporte reporte = Reporte.builder()
                .fecha(LocalDate.now())
                .tipoReporte(TipoReporte.VIANDAS_POR_COLABORADOR)
                .nombre("Reporte de Donaciones por Colaborador")
                .detalles(detalles)
                .build();

        try {
            String rutaPdf = GeneradorDePDF.generarPDF(reporte);
            reporte.setRutaPdf(rutaPdf);
            withTransaction(() -> {
                reportesRepositorio.guardar(reporte);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteViandasPorHeladera() {
        List<Object[]> resultados = generadorDeEstadisticas.contarViandasPorHeladeraSemanaActual();
        List<String> detalles = resultados.stream()
                .map(result -> "Heladera: " + result[0] + ", Viandas Colocadas: " + result[1] + ", Viandas Retiradas: " + result[2])
                .collect(Collectors.toList());

        Reporte reporte = Reporte.builder()
                .fecha(LocalDate.now())
                .tipoReporte(TipoReporte.VIANDAS_DON_RET_POR_HELADERA)
                .nombre("Reporte de Viandas por Heladera")
                .detalles(detalles)
                .build();

        try {
            String rutaPdf = GeneradorDePDF.generarPDF(reporte);
            reporte.setRutaPdf(rutaPdf);
            withTransaction(() -> {
                reportesRepositorio.guardar(reporte);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desactivarReportesViejos() {
        reportesRepositorio.eliminarTodosLogico();
    }

}
