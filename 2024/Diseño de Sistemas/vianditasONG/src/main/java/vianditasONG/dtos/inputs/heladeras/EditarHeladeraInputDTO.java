package vianditasONG.dtos.inputs.heladeras;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditarHeladeraInputDTO {
    private Long heladeraId;
    private String nombreHeladera;
    private String modeloId;
    private Integer cantidadViandas;
    private String estadoHeladera;
    private Float temperatura;
    private String nombrePuntoEstrategico;
    private String calle;
    private String altura;
    private String localidad;


}

//ToDo: agregar funcionalidad para mostrar visitas, suscripciones y heladeras cercanas