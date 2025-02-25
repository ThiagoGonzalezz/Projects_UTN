package vianditasONG.dtos.outputs.formulario;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormularioEstadisticaDTO {
    private long id;
    private String tipoForm;
    private String nombre;
    private int pregTotales;
    private int pregObliTotales;
    private int pregOpcioTotales;
}
