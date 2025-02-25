package vianditasONG.dtos.inputs;

import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import lombok.Getter;

@Getter
public class PersonaVulnerableInputDTO {

    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeRegistro;
    private TipoDeDocumento tipoDeDocumento;
    private String numeroDocumento;
    private Integer habilitadoPorIDHumano;
    //Si es adulto o posee vivienda se deduce de estos datos

}
