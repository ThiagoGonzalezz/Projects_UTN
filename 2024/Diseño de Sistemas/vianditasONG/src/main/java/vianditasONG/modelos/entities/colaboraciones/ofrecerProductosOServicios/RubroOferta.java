package vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios;

import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rubro_oferta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RubroOferta extends Persistente {
    @Column(name = "descripcion", columnDefinition = "VARCHAR(50)")
    private String descripcion;
}
