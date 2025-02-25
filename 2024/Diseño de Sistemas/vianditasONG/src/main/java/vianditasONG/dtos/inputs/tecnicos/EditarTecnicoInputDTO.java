package vianditasONG.dtos.inputs.tecnicos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EditarTecnicoInputDTO {
    private Long tecnicoId;
    private String cuil;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private String tipoDeDocumento;
    private String numeroDeDocumento;
    private String tipoDeContacto;
    private String contacto;
    private Integer areaDeCoberturaKM;
    private String  provincia;
    private String localidad;
    private String calle;
    private Integer altura;
}

