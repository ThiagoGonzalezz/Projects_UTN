package vianditasONG.modelos.entities.datosGenerales.direccion;

import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localidad extends Persistente {
    @Setter
    @Getter
    @Column(name="nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name="provincia")
    private Provincia provincia;


}

