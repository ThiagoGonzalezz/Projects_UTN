package vianditasONG.dtos.inputs.heladeras;




import lombok.*;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SumarHeladeraInputDTO {
    private String nombreHeladera;
    private String modeloId;
    private String nombrePuntoEstrategico;
    private String calle;
    private String altura;
    private String localidad;
    private Long encargado;
}


