package vianditasONG.utils.usuarioRolesYPermisos;

import vianditasONG.utils.Persistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="permiso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso extends Persistente {
    @Column(name="descripcion", columnDefinition = "VARCHAR(150)")
    private String descripcion;
}
