package vianditasONG.modelos.entities.colaboradores.infoColaboradores;

import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rubro_persona_juridica")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RubroPersonaJuridica extends Persistente {
    @Column(name = "descripcion", columnDefinition = "VARCHAR(50)")
    private String descripcion;
}