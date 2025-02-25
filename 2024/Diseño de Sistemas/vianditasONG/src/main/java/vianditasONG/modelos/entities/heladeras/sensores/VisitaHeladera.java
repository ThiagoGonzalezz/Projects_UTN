package vianditasONG.modelos.entities.heladeras.sensores;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visita_heladera")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitaHeladera extends Persistente {


    @Column(name = "fecha", nullable = false, columnDefinition= "DATETIME")
    private LocalDateTime fecha;

    @Column(name = "foto", nullable = true, columnDefinition= "TEXT")
    private String foto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne 
    @JoinColumn(name = "tecnico_id", columnDefinition="VARCHAR(11)")
    private Tecnico tecnico;

    @Builder.Default
    @Column(name = "soluciono_incidente", nullable = false, columnDefinition="BIT")
    private Boolean solucionoElIncidente = false;
}
