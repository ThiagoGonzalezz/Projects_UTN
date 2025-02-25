package vianditasONG.modelos.entities.heladeras;

import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "modelo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Modelo extends Persistente {

    @Column(name = "temp_maxima_grados", nullable = false, columnDefinition = "FLOAT")
    private Float tempMaximaEnGradosCelsius;

    @Column(name = "temp_minima_grados", nullable = false, columnDefinition = "FLOAT")
    private Float tempMinimaEnGradosCelsius;

    @Column(name = "capacidad_maxima_viandas", nullable = false, columnDefinition = "INTEGER")
    private Integer capacidadMaximaViandas;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
