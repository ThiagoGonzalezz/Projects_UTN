package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.inputs.colaboraciones.SuscripcionDTO;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

@Builder
public class ConverterSuscripcionDTO {
    public SuscripcionDTO convertToDTO(SuscripcionDesperfectos suscripcionDesperfectos){
        return SuscripcionDTO.builder()
                .idSuscripcion(suscripcionDesperfectos.getId())
                .tipoSuscripcion("DESPERFECTOS")
                .heladeraId(suscripcionDesperfectos.getHeladera().nombre())
                .contacto(suscripcionDesperfectos.getContacto().getContacto())
                .build();
    }
    public SuscripcionDTO convertToDTO(SuscripcionCantViandasMax suscripcionCantViandasMax){
        return SuscripcionDTO.builder()
                .idSuscripcion(suscripcionCantViandasMax.getId())
                .tipoSuscripcion("VIANDAS MÁXIMAS")
                .cantViandasMax(suscripcionCantViandasMax.getCantViandasMax())
                .heladeraId(suscripcionCantViandasMax.getHeladera().nombre())
                .contacto(suscripcionCantViandasMax.getContacto().getContacto())
                .build();
    }
    public SuscripcionDTO convertToDTO(SuscripcionCantViandasMin suscripcionCantViandasMin){
        return SuscripcionDTO.builder()
                .idSuscripcion(suscripcionCantViandasMin.getId())
                .tipoSuscripcion("VIANDAS MÍNIMAS")
                .cantViandasMin(suscripcionCantViandasMin.getCantViandasMin())
                .heladeraId(suscripcionCantViandasMin.getHeladera().nombre())
                .contacto(suscripcionCantViandasMin.getContacto().getContacto())
                .build();
    }
}
