package vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class MedioDeConctactoRecomendado {

    public String tipoMedioDeContacto;
    public String detalle;

}
