package vianditasONG.modelos.entities.colaboraciones;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.heladeras.Heladera;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "hacerse_cargo_de_heladera")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HacerseCargoDeHeladera extends Persistente {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "personaJuridica_id")
    private PersonaJuridica colaborador;
    @OneToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;
    @Column(name = "puntosAcumulados")
    private Double puntosAcumulados;
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    public Integer diasActivos(){
        return this.heladera.diasActivaSinCalcular();
    }
    public boolean estaActiva(){return this.heladera.getEstaActiva();}

    public void sumarPuntos(Double puntos) {

        puntosAcumulados = puntosAcumulados + puntos;

    }

}
