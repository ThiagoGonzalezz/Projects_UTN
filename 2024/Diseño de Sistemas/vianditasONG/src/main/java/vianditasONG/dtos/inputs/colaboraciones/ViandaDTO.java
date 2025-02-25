package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ViandaDTO {
    private String comida;
    private Integer calorias;
    private String fechaDeCaducidad;
    private Double pesoEnGramos;
    private Integer idHeladeraSeleccionada;
}
