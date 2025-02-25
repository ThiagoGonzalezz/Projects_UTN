package vianditasONG.modelos.entities.tecnicos;

import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaDeCobertura {
    @Embedded
    private PuntoGeografico puntoGeografico;
    @Column(name="radioEnKmCobertura", columnDefinition = "INTEGER(11)")
    private Integer radioEnKM;
}
