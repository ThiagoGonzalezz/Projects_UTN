package models.entities.puntosDeDonacion;

import lombok.*;
import models.converters.DayOfWeekAttributteConverter;
import models.entities.datosGeograficos.PuntoGeografico;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="punto_de_donacion")
public class PuntoDeDonacion {
    @Id
    @GeneratedValue
    private Long Id;
    @Builder.Default
    @Setter
    @Column(name = "activo", columnDefinition = "BIT")
    private boolean activo = true;
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Embedded
    private PuntoGeografico puntoGeografico;
    @Setter
    @Column(name = "horaApertura")
    private LocalTime horaApertura;
    @Setter
    @Column(name = "horaCierre")
    private LocalTime horaCierre;
    @Builder.Default
    @Setter
    @ElementCollection
    @CollectionTable(name = "dia_abierto_por_punto_donacion", joinColumns = @JoinColumn(name = "puntoDeDonacion_id", referencedColumnName = "id"))
    @Convert(converter = DayOfWeekAttributteConverter.class)
    @Column(name = "diasAbierto")
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> diasAbierto = new ArrayList<>();

}
