package vianditasONG.modelos.entities.formulario;


import lombok.*;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.utils.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="respuesta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta extends Persistente {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="pregunta_id")
    @Getter @Setter
    private Pregunta preguntaAsociada;
    @Column(name = "respuestaLibre", columnDefinition = "TEXT")
    @Setter @Getter
    private String respuestaLibre;
    @Builder.Default
    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "opcion_seleccionada",
            joinColumns =  @JoinColumn(name = "respuesta_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "opcion_id", referencedColumnName = "id")
    )
    private List<Opcion> opcionesSeleccionadas = new ArrayList<>();

    public void agregarOpciones(Opcion ... opciones) {
        Collections.addAll(this.opcionesSeleccionadas, opciones);
    }
}
