package vianditasONG.dtos.outputs.colaboraciones;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@lombok.Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeladeraACargoDTO {
    private String nombre;
    private Long heladeraId;
    private String direccion;
    private String estado;
    private String fechaColocacion;

}
