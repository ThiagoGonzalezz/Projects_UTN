package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.ColaboradorOutputDTO;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;

import java.time.LocalDate;

@Builder
public class ConverterColaboradorDTO {

    public ColaboradorOutputDTO ConvertToDTO(Humano humano){
        ColaboradorOutputDTO colabDto = ColaboradorOutputDTO.builder()
                .id(humano.getId())
                //.usuario(humano.getUsuario().getNombreDeUsuario())
                .nombre(humano.getNombre()+" "+ humano.getApellido())
                //.fecha_registro(String.valueOf(humano.getFechaDeSolicitud()))
                .puntos(humano.getPuntos())
                .build();

        if(humano.getUsuario() != null){
            colabDto.setUsuario(humano.getUsuario().getNombreDeUsuario());
        }

        if(humano.getFechaDeSolicitud() != null){
            colabDto.setFecha_registro(String.valueOf(humano.getFechaDeSolicitud()));
        }

        return colabDto;
    }

}
