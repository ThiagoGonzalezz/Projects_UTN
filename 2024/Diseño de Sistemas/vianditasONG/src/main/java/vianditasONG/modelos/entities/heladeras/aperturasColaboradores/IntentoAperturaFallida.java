package vianditasONG.modelos.entities.heladeras.aperturasColaboradores;

import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "intento_apertura_fallida")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentoAperturaFallida  extends Persistente {
    @Column(name = "fechaYHora", columnDefinition = "DATETIME")
    private LocalDateTime fechaYHora;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladera_id")
    private Heladera heladera;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="tarjetaColaborador_id")
    private TarjetaColaborador tarjetaColaborador;

}
