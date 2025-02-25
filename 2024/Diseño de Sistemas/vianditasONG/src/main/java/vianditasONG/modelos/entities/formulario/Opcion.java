package vianditasONG.modelos.entities.formulario;

import vianditasONG.utils.Persistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "opcion")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Opcion extends Persistente {
    @Getter
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
