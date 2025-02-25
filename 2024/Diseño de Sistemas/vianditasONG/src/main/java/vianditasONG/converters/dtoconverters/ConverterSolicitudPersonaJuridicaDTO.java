package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.colaboradores.ContactoDto;
import vianditasONG.dtos.outputs.colaboradores.humanos.SolicitudHumanoDTO;
import vianditasONG.dtos.outputs.colaboradores.personasJuridicas.SolicitudPersonaJuridicaDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Builder
public class ConverterSolicitudPersonaJuridicaDTO {
    @Builder.Default
    private DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public SolicitudPersonaJuridicaDTO ConvertToDTO(PersonaJuridica pj){
        SolicitudPersonaJuridicaDTO solicitudDTO = SolicitudPersonaJuridicaDTO.builder()
                .idColab(pj.getId())
                .fechaSolicitud(this.formatoFechas.format(pj.getFechaDeSolicitud()))
                .razonSocial(pj.getRazonSocial())
                .rubro(pj.getRubro().getDescripcion())
                .tipoOrganizacion(this.tipoOrganizacionToString(pj.getTipoDeOrganizacion()))
                .tiposColaboracion(pj.getFormasDeColaboracion().stream().map(this::formaDeColabToString).collect(Collectors.joining(", ")))
                .build();

        for(Contacto contacto: pj.getContactos()){
            ContactoDto contactoDto = ContactoDto.builder()
                    .tipoContacto(this.tipoContactoToString(contacto.getTipoDeContacto()))
                    .detalleContacto(contacto.getContacto())
                    .build();

            solicitudDTO.agregarContactos(contactoDto);
        }

        if(pj.getDireccion() != null){
            solicitudDTO.setProvincia(this.provinciaToString(pj.getDireccion().getLocalidad().getProvincia()));
            solicitudDTO.setDireccion(pj.getDireccion().getCalle() + " " + pj.getDireccion().getAltura());
            solicitudDTO.setLocalidad(pj.getDireccion().getLocalidad().getNombre());
        }

        return solicitudDTO;
    }

    private String formaDeColabToString(FormaDeColaboracion formaDeColab){
        return switch (formaDeColab){
            case DONACION_DINERO -> "Donación de Dinero";
            case HACERSE_CARGO_DE_HELADERAS -> "Hacerse cargo de Heladeras";
            case OFERTA_PRODUCTOS_Y_SERVICIOS -> "Ofrecer Productos y Servicios";
            default -> "Error";
        };
    }

    private String tipoContactoToString(TipoDeContacto tipoDeContacto){
        return switch (tipoDeContacto){
            case CORREO -> "Mail";
            case TELEGRAM -> "Telegram";
            case WHATSAPP -> "Whatsapp";
        };
    }

    private String tipoOrganizacionToString(PersonaJuridica.TipoDeOrganizacion tipoOrg){
        return switch (tipoOrg){
            case ONG -> "ONG";
            case EMPRESA -> "Empresa";
            case INSTITUCION -> "Institucion";
            case GUBERNAMENTAL -> "Gubernamental";
        };
    }

    private String provinciaToString(Provincia provincia) {
        return switch (provincia) {
            case BUENOS_AIRES -> "Buenos Aires";
            case CABA -> "CABA";
            case CATAMARCA -> "Catamarca";
            case CHACO -> "Chaco";
            case CHUBUT -> "Chubut";
            case CORDOBA -> "Córdoba";
            case CORRIENTES -> "Corrientes";
            case ENTRE_RIOS -> "Entre Ríos";
            case FORMOSA -> "Formosa";
            case JUJUY -> "Jujuy";
            case LA_PAMPA -> "La Pampa";
            case LA_RIOJA -> "La Rioja";
            case MENDOZA -> "Mendoza";
            case MISIONES -> "Misiones";
            case NEUQUEN -> "Neuquén";
            case RIO_NEGRO -> "Río Negro";
            case SALTA -> "Salta";
            case SAN_JUAN -> "San Juan";
            case SAN_LUIS -> "San Luis";
            case SANTA_CRUZ -> "Santa Cruz";
            case SANTA_FE -> "Santa Fe";
            case SANTIAGO_DEL_ESTERO -> "Santiago del Estero";
            case TIERRA_DEL_FUEGO -> "Tierra del Fuego";
            case TUCUMAN -> "Tucumán";
            default -> "Provincia desconocida";
        };
    }
}

