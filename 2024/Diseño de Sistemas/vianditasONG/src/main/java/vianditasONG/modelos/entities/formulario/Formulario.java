package vianditasONG.modelos.entities.formulario;

import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="formulario")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formulario extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Builder.Default
    @Getter
    @Column(name="esEstablecido")
    private Boolean esEstablecido = true;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_id")
    private List<Pregunta> preguntas = new ArrayList<>();

    public void agregarPregunta(Pregunta pregunta){
        preguntas.add(pregunta);
    }

    public void sacarPregunta(Pregunta pregunta){
        preguntas.add(pregunta);
    }
}
