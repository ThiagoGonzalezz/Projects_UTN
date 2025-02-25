package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PersonaVulnerableInputDTO {
    private String nombre;
    private String apellido;
    private String fechaDeNacimiento;
    private String tipoDeDocumento;
    private String numeroDocumento;
    private String provincia;
    private String localidad;
    private String calle;
    private String altura;
    private String codigoDeTarjeta;
    // private List<PersonaVulnerableInputDTO> hijos;TODO:@Luca

}
