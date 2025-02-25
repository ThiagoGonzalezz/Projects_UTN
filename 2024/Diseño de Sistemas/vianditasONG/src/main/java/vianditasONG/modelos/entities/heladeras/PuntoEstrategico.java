package vianditasONG.modelos.entities.heladeras;

import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuntoEstrategico{
    @Embedded
    private PuntoGeografico puntoGeografico;
    @Embedded
    public Direccion direccion;
    @Column(name="nombrePuntoEstrategico", columnDefinition = "VARCHAR(50)")
    private String nombre;
}

