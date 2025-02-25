package vianditasONG.modelos.entities.formulario;

import lombok.*;
import vianditasONG.utils.Persistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "formulario_respondido")
public class FormularioRespondido extends Persistente {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_id", columnDefinition = "BIGINT")
    private Formulario formularioAsociado;
    @Column(name = "fechaRealizacion", columnDefinition = "DATE")
    private LocalDate fechaRealizacion;
    @Builder.Default
    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formularioRespondido_id", columnDefinition = "BIGINT")
    private List<Respuesta> listaRespuestas = new ArrayList<>();

    public void agregarRespuestas(Respuesta ... respuestas) {
        Collections.addAll(this.listaRespuestas, respuestas);
    }

}
