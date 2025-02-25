package vianditasONG.modelos.entities.heladeras.aperturasColaboradores;

import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apertura_fehaciente")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AperturaFehaciente extends Persistente {

    @Column(name = "fecha_hora", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaYHora;

    @ManyToOne
    @JoinColumn(name = "heladera_id", columnDefinition = "BIGINT")
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name = "accion_solicitada_id", columnDefinition = "BIGINT")
    private AccionSolicitada accionSolicitada;

    @OneToOne
    @JoinColumn(name="tarjetaColaborador_id")
    private TarjetaColaborador tarjetaColaborador;
}
