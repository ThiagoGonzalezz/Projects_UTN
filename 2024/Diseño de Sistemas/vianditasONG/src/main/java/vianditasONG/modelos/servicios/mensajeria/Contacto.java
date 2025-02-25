package vianditasONG.modelos.servicios.mensajeria;


import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="contacto")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacto extends Persistente {
    @Enumerated(EnumType.STRING)
    @Column(name="tipoDeContacto")
    private TipoDeContacto tipoDeContacto;
    @Column(name="valorContacto", columnDefinition = "VARCHAR(50)")
    private String contacto;
}
