package vianditasONG.modelos.entities.personaVulnerable.tarjetas;

import vianditasONG.modelos.entities.heladeras.Heladera;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@Table(name = "registro_uso_tarjeta")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsoTarjeta {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;
    @Column(name = "fechaUso", columnDefinition = "DATETIME")
    private LocalDateTime fechaUso;
    @Column(name = "cantidadViandas")
    private Integer cantidadViandas;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjetaDePersonaVulnerable_id")
    private TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable;

}
