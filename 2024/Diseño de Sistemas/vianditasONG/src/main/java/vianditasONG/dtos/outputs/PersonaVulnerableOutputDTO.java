package vianditasONG.dtos.outputs;

import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import lombok.Builder;

@Builder
public class PersonaVulnerableOutputDTO {

    private Integer id;
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeRegistro;
    private TipoDeDocumento tipoDeDocumento;
    private String numeroDocumento;
    private Integer habilitadoPorIDHumano;

}
