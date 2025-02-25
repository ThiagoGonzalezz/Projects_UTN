package vianditasONG.dtos.outputs;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReporteOutputDTO {
    private String fecha;
    private String tipoReporte;
    private String nombre;
    private String rutaPDF;
    private String leyenda;
}
