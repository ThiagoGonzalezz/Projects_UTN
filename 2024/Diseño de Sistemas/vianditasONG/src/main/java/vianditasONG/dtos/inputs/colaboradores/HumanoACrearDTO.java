package vianditasONG.dtos.inputs.colaboradores;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter @Setter
public class HumanoACrearDTO {
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private Long localidadId;
    private String calle;
    private String altura;
    @Builder.Default
    private List<String> formasDeColaborarIds = new ArrayList<>();
    @Builder.Default
    private List<ContactoDTO> contactos = new ArrayList<>();

    public void agregarFormasDeColaborar(String ... formasDeColaborar){
        Collections.addAll(this.formasDeColaborarIds, formasDeColaborar);
    }

    public void agregarContactos(ContactoDTO ... contactosDTOS){
        Collections.addAll(this.contactos, contactosDTOS);
    }
}
