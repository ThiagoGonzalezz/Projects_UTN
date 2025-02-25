package vianditasONG.dtos.inputs.colaboradores;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
public class PersonaJuridicaACrearDTO {
    private String razonSocial;
    private Long rubroId;
    private String tipoDeOrganizacion;
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
