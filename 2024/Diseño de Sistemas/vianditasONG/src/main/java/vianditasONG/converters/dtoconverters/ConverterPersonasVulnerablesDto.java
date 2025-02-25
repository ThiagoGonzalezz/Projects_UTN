package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.PersonaVulnerableInputDTO;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasDePersonasVulnerablesRepositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Builder
@Getter
public class ConverterPersonasVulnerablesDto {

    @Builder.Default
    private ILocalidadesRepositorio localidadesRepositorio = ServiceLocator.getService(LocalidadesRepositorio.class);

    public PersonaEnSituacionVulnerable convertToPersonaVulnerable(PersonaVulnerableInputDTO personaVulnerableInputDTO){

        Optional<Localidad> localidad = Optional.empty();
        Direccion direccion = null;
        Optional<TipoDeDocumento> tipoDeDocumento = Optional.of(TipoDeDocumento.NINGUNO);

        if(personaVulnerableInputDTO.getTipoDeDocumento() != null)
            tipoDeDocumento = Optional.of(TipoDeDocumento.valueOf(personaVulnerableInputDTO.getTipoDeDocumento()));

        if(personaVulnerableInputDTO.getLocalidad() != null)
        {
            localidad = localidadesRepositorio.buscarPorId(Long.valueOf(personaVulnerableInputDTO.getLocalidad()));
            direccion = Direccion.builder()
                    .localidad(localidad.get())
                    .calle(personaVulnerableInputDTO.getCalle())
                    .altura(personaVulnerableInputDTO.getAltura())
                    .build();
        }



        return PersonaEnSituacionVulnerable.builder()
                .nombre(personaVulnerableInputDTO.getNombre())
                .apellido(personaVulnerableInputDTO.getApellido())
                .fechaDeNacimiento(LocalDate.parse(personaVulnerableInputDTO.getFechaDeNacimiento()))
                .tipoDeDocumento(tipoDeDocumento.get())
                .numeroDocumento(personaVulnerableInputDTO.getNumeroDocumento())
                .fechaDeRegistro(LocalDateTime.now())
                .direccion(direccion)
                .esAdulto(esAdulto(LocalDate.parse(personaVulnerableInputDTO.getFechaDeNacimiento())))
                .poseeVivienda(localidad.isPresent())
                .build();
    }

    private Boolean esAdulto(LocalDate fechaDeNacimiento)
    {
        return Period.between(fechaDeNacimiento, LocalDate.now()).getYears() > 18;
    }

}
