package vianditasONG.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class Persistente implements IPersistente{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "activo")
    private Boolean activo = Boolean.TRUE;

    public boolean isActivo() {
        return activo;
    }
}
