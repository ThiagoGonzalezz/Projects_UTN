package vianditasONG.dtos.outputs.colaboradores.humanos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vianditasONG.dtos.outputs.colaboradores.ContactoDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Builder
public class SolicitudHumanoDTO {
    private Long idColab;
    private String fechaSolicitud;
    private String nombre;
    private String apellido;
    @Setter
    private String fechaNacimiento;
    @Setter
    private String provincia;
    @Setter
    private String localidad;
    @Setter
    private String direccion;
    @Builder.Default
    private List<ContactoDto> contactos = new ArrayList<>();
    private String tiposColaboracion;

    public void agregarContactos(ContactoDto ... contactos){
        Collections.addAll(this.contactos, contactos);
    }
}