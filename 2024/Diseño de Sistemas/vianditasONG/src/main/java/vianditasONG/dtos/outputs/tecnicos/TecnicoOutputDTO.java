package vianditasONG.dtos.outputs.tecnicos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TecnicoOutputDTO {
    private String cuil;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private String tipoDeDocumento;
    private String numeroDeDocumento;
    private String tipoContacto;
    private String contacto;
    private Integer areaDeCoberturaKM;
    private String areaDeCoberturaDireccion;
}
