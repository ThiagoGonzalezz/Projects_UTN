package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.dtos.inputs.colaboradores.HumanoACrearDTO;
import vianditasONG.dtos.inputs.colaboradores.PersonaJuridicaACrearDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.rubros.IRubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class ConverterPersonaJuridicaACrearDTO {
    @Builder.Default
    private ILocalidadesRepositorio repoLocalidades = ServiceLocator.getService(LocalidadesRepositorio.class);
    @Builder.Default
    private IRubrosPersonaJuridicaRepositorio repoRubros = ServiceLocator.getService(RubrosPersonaJuridicaRepositorio.class);

    public PersonaJuridica convertToPersonaJuridica(PersonaJuridicaACrearDTO personaJuridicaDTO) throws IOException {

        PersonaJuridica personaJuridica = PersonaJuridica.builder()
                .razonSocial(personaJuridicaDTO.getRazonSocial())
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.valueOf(personaJuridicaDTO.getTipoDeOrganizacion()))
                .fechaDeSolicitud(LocalDateTime.now())
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .build();

        RubroPersonaJuridica rubro = this.repoRubros.buscarPorId(personaJuridicaDTO.getRubroId()).orElseThrow(BadRequestException::new);

        personaJuridica.setRubro(rubro);

        this.procesarDireccion(personaJuridicaDTO, personaJuridica);

        this.procesarFormasDeColaboracion(personaJuridicaDTO, personaJuridica);

        this.procesarContactos(personaJuridicaDTO, personaJuridica);

        return personaJuridica;
    }

    private void procesarDireccion(PersonaJuridicaACrearDTO personaJuridicaDTO, PersonaJuridica personaJuridica) throws IOException {
        if (personaJuridicaDTO.getLocalidadId()!= null &&
                personaJuridicaDTO.getCalle() != null &&
                personaJuridicaDTO.getAltura() != null) {

            Direccion direccion = Direccion.builder()
                    .localidad(this.repoLocalidades.buscarPorId(personaJuridicaDTO.getLocalidadId()).orElseThrow(BadRequestException::new))
                    .calle(personaJuridicaDTO.getCalle())
                    .altura(personaJuridicaDTO.getAltura())
                    .build();

            personaJuridica.setDireccion(direccion);

        }
    }

    private void procesarFormasDeColaboracion(PersonaJuridicaACrearDTO personaJuridicaDTO, PersonaJuridica personaJuridica){
        for(String formaDeColab: personaJuridicaDTO.getFormasDeColaborarIds()){
            personaJuridica.agregarFormasDeColab(FormaDeColaboracion.valueOf(formaDeColab));
        }
    }

    private void procesarContactos(PersonaJuridicaACrearDTO personaJuridicaDTO, PersonaJuridica personaJuridica){
        for(ContactoDTO contactoDTO: personaJuridicaDTO.getContactos()){
            personaJuridica.agregarContacto(Contacto.builder()
                    .tipoDeContacto(TipoDeContacto.valueOf(contactoDTO.getTipoContacto()))
                    .contacto(contactoDTO.getDetalleContacto())
                    .build()
            );
        }
    }
}
