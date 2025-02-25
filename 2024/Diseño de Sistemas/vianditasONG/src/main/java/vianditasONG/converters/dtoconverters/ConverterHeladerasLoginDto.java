package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;

@Builder
public class ConverterHeladerasLoginDto {
    public HeladeraEnMapaDTO convertToDTO(Heladera heladera){
        return HeladeraEnMapaDTO.builder()
                .activa(heladera.getActivo())
                .nombre(heladera.getNombre())
                .direccion(heladera.direccionCompleta())
                .estado(this.estadoHeladeraToEstadoHeladeraDTO(heladera.getEstadoHeladera()))
                .viandasAlmacenadas(heladera.getCantidadViandas())
                .capacidadRestante(heladera.cantViandasRestantes())
                .latitud(heladera.getPuntoEstrategico().getPuntoGeografico().getLatitud())
                .longitud(heladera.getPuntoEstrategico().getPuntoGeografico().getLongitud())
                .id(Math.toIntExact(heladera.getId()))
                .build();
    }

    private String estadoHeladeraToEstadoHeladeraDTO(EstadoHeladera estado) {
        return switch (estado) {
            case DESCONECTADA -> "Desconectada";
            case TEMPERATURA_INADECUADA -> "Temperatura inadecuada";
            case BLOQUEADA_POR_FRAUDE -> "Bloqueada por fraude";
            case CON_FALLA_TECNICA -> "Falla tecnica";
            case ACTIVA -> "Activa";
            default -> "Estado desconocido";
        };
    }
}
