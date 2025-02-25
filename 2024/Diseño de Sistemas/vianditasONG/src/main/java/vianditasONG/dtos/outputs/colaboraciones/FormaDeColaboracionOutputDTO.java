package vianditasONG.dtos.outputs.colaboraciones;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormaDeColaboracionOutputDTO {
    private String imagen;
    private String nombre;
    private String descripcion;
    private Boolean activada;
    private String href;
    private String stringEnum;
}
