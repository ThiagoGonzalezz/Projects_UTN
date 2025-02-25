package vianditasONG.modelos.entities.datosGenerales;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuntoGeografico {
    @Column(name="latitud")
    private double latitud;
    @Column(name="longitud")
    private double longitud;
}
