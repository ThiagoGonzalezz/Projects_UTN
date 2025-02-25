package vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas;

import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "distribucion_de_vianda")
public class DistribucionDeVianda extends Persistente {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladeraOrigen_id", columnDefinition = "BIGINT")
    private Heladera heladeraOrigen;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladeraDestino_id", columnDefinition = "BIGINT")
    private Heladera heladeraDestino;
    @Column(name = "cantidadDeViandas", columnDefinition = "INTEGER")
    private Integer cantidadDeViandas;
    @Enumerated(value = EnumType.STRING)
    private MotivoDeDistribucion motivoDeDistribucion;
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_respondido_id", columnDefinition = "BIGINT")
    private FormularioRespondido formularioRespondido;

}
