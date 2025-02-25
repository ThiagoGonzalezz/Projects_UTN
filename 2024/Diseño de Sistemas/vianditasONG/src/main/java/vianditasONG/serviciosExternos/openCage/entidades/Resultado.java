package vianditasONG.serviciosExternos.openCage.entidades;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Resultado {
    private Geometry geometry;
    @Getter
    private String formatted;

}
