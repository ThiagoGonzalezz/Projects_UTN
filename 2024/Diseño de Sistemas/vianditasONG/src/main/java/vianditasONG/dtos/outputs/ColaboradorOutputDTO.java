package vianditasONG.dtos.outputs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class ColaboradorOutputDTO {
    private Long id;
    @Setter
    private String usuario;
    private String nombre;
    @Setter
    private String fecha_registro;
    private Double puntos;
}
