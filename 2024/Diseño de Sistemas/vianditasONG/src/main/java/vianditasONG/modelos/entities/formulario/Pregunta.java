package vianditasONG.modelos.entities.formulario;

import lombok.*;
import vianditasONG.utils.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="pregunta")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pregunta extends Persistente {
    @Column(name = "enunciado", columnDefinition = "TEXT")
    private String enunciado;
    @Column(name = "placeholder", columnDefinition = "TEXT")
    private String placeholder;
    @Column(name = "esEsencial", columnDefinition = "BIT")
    private Boolean esEsencial;
    @Column(name = "tipoEntrada", columnDefinition = "VARCHAR(50)")
    @Setter
    private TipoEntradaPregunta tipoEntrada;
    @Enumerated(EnumType.STRING)
    private TipoPregunta tipo;
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "pregunta_id", columnDefinition = "BIGINT")
    private List<Opcion> opciones = new ArrayList<>();
    @Builder.Default
    @Column(name = "esEstatica", columnDefinition = "BIT")
    private Boolean esEstatica = true;
    public void agregarOpciones(Opcion ... opciones){
        Collections.addAll(this.opciones, opciones);
    }
}

