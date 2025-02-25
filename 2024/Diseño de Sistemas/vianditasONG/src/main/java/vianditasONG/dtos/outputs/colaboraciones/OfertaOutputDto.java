package vianditasONG.dtos.outputs.colaboraciones;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;

import javax.persistence.*;

@Builder
@Getter
public class OfertaOutputDto {

    private Long id;
    private String rubro;
    private String nombre;
    private Integer puntosNecesarios;
    private String imagen;
    private String ofertante;

}
