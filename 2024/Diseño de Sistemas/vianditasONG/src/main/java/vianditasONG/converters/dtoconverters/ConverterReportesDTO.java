package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.ReporteOutputDTO;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.modelos.servicios.reportes.TipoReporte;

@Builder
public class ConverterReportesDTO {
    public ReporteOutputDTO convertToDTO(Reporte reporte) {
        return ReporteOutputDTO.builder()
                .tipoReporte(String.valueOf(reporte.getTipoReporte()))
                .rutaPDF(reporte.getRutaPdf())
                .fecha(String.valueOf(reporte.getFecha()))
                .nombre(reporte.getNombre())
                .leyenda(getLeyendaPorTipo(reporte.getTipoReporte()))
                .build();
    }

    private String getLeyendaPorTipo(TipoReporte tipoReporte) {
        return switch (tipoReporte) {
            case VIANDAS_POR_COLABORADOR -> "Viandas donadas por colaborador";
            case FALLAS_POR_HELADERA -> "Fallas por heladera";
            case VIANDAS_DON_RET_POR_HELADERA -> "Viandas donadas/retiradas por heladera";
            default -> "Tipo de reporte desconocido";
        };
    }
}
