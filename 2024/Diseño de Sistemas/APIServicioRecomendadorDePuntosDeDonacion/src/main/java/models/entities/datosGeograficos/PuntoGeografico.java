package models.entities.datosGeograficos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PuntoGeografico {
    @Column(name = "latitud", columnDefinition = "DECIMAL(15,12)")
    private double latitud;
    @Column(name = "longitud", columnDefinition = "DECIMAL(15,12)")
    private double longitud;
}
