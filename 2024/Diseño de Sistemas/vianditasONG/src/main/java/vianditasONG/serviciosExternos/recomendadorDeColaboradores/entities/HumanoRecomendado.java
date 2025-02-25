package vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
public class HumanoRecomendado {

    public Long id;
    public String nroDocumento;
    public String tipoDocumento;
    public String nombre;
    public String apellido;

    public List<MedioDeConctactoRecomendado> mediosDeContacto;
    public Double puntos;

    public Integer donaciones;
}
