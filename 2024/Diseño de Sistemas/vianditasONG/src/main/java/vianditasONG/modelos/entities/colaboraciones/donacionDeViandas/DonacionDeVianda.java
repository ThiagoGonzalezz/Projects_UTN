package vianditasONG.modelos.entities.colaboraciones.donacionDeViandas;

import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Persistente {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "donacionDeVianda_id")
    private List<Vianda> viandas = new ArrayList<>();
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_respondido_id", columnDefinition = "BIGINT")
    private FormularioRespondido formularioRespondido;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id", columnDefinition = "BIGINT")
    //@Column(name = "heladera_id", columnDefinition = "BIGINT")
    private Heladera heladera;

    public Integer cantViandas() {
        return viandas.size();
    }

    public void agregarViandas(Vianda ... viandas) {
        Collections.addAll(this.viandas, viandas);
    }

}
