package vianditasONG.modelos.entities.heladeras.aperturasColaboradores;

import org.hibernate.annotations.Cascade;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "accion_solicitada")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccionSolicitada extends Persistente{

    @Enumerated
    private TipoDeAccion tipoDeAccion;

    @Column(name = "fecha_hora_solicitud", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaYHoraDeSolicitud;

    @Column(name = "fecha_caducidad_solicitud", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaDeCaducidadDeSolicitud;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tarjeta_colaborador_id")
    private TarjetaColaborador tarjetaColaborador;

    @Enumerated(EnumType.STRING)
    @Column(name = "formaDeColaboracion")
    private FormaDeColaboracion formaDeColaboracion;

    @Column(name = "colaboracionId")
    private Long colaboracionId;

    @ManyToMany
    @JoinTable(
            name = "accion_solicitada_heladeras",
            joinColumns = @JoinColumn(name = "accion_solicitada_id"),
            inverseJoinColumns = @JoinColumn(name = "heladera_id")
    )
    @Builder.Default
    private List<Heladera> heladerasSolicitadas= new ArrayList<>();

    public void agregarHeladeraSolicitada(Heladera heladera){
        this.heladerasSolicitadas.add(heladera);
    }

}
