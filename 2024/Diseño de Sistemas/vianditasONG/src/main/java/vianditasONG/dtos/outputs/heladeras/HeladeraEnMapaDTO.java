package vianditasONG.dtos.outputs.heladeras;

import lombok.Builder;

@Builder
public class HeladeraEnMapaDTO {
    private String nombre;
    private String direccion;
    private String estado;
    private boolean activa;
    private int viandasAlmacenadas;
    private int capacidadRestante;
    private double latitud;
    private double longitud;
    private int id;
}
