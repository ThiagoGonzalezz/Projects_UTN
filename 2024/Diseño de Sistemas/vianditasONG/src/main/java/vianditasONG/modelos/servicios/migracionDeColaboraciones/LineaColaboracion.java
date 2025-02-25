package vianditasONG.modelos.servicios.migracionDeColaboraciones;

import lombok.Getter;

@Getter
public class LineaColaboracion {
    private final String tipoDoc;
    private final String documento;
    private final String nombre;
    private final String apellido;
    private final String mail;
    private final String fechaColaboracion;
    private final String formaDeColaboracion;
    private final String cantidad;

    public LineaColaboracion(String tipoDoc, String documento, String nombre, String apellido, String mail, String fechaColaboracion, String formaDeColaboracion, String cantidad) {
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.fechaColaboracion = fechaColaboracion;
        this.formaDeColaboracion = formaDeColaboracion;
        this.cantidad = cantidad;
    }
}

