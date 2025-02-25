package vianditasONG.dtos.inputs.tecnicos;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

@Builder
@Getter
public class SumarTecnicoInputDTO {
    private String cuil;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private String tipoDeDocumento;
    private String numeroDeDocumento;
    private String tipoContacto;
    private String contacto;
    private Integer areaDeCoberturaKM;
    private String  provincia;
    private String localidad;
    private String calle;
    private Integer altura;
}

