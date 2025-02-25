package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.ColaboradorOutputDTO;
import vianditasONG.dtos.outputs.colaboradores.ContactoDto;
import vianditasONG.dtos.outputs.colaboradores.humanos.SolicitudHumanoDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Builder
public class ConverterSolicitudHumanoDTO {
    @Builder.Default
    private DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SolicitudHumanoDTO ConvertToDTO(Humano humano){
        SolicitudHumanoDTO solicitudDTO = SolicitudHumanoDTO.builder()
                .idColab(humano.getId())
                .fechaSolicitud(humano.getFechaDeSolicitud().format(formatoFechas))
                .nombre(humano.getNombre())
                .apellido(humano.getApellido())
                .tiposColaboracion(humano.getFormasDeColaboracion().stream().map(this::formaDeColabToString).collect(Collectors.joining(", ")))
                .build();

        for(Contacto contacto: humano.getContactos()){
            ContactoDto contactoDto = ContactoDto.builder()
                            .tipoContacto(this.tipoContactoToString(contacto.getTipoDeContacto()))
                            .detalleContacto(contacto.getContacto())
                            .build();

            solicitudDTO.agregarContactos(contactoDto);
        }

        if(humano.getDireccion() != null){
            solicitudDTO.setProvincia(this.provinciaToString(humano.getDireccion().getLocalidad().getProvincia()));
            solicitudDTO.setDireccion(humano.getDireccion().getCalle() + " " + humano.getDireccion().getAltura());
            solicitudDTO.setLocalidad(humano.getDireccion().getLocalidad().getNombre());
        }

        if(humano.getFechaNacimiento() != null) {
        solicitudDTO.setFechaNacimiento(humano.getFechaNacimiento().format(formatoFechas));
        }


        return solicitudDTO;
    }

    private String formaDeColabToString(FormaDeColaboracion formaDeColab){
        return switch (formaDeColab){
            case DONACION_DINERO -> "Donación de Dinero";
            case DONACION_VIANDAS -> "Donación de Viandas";
            case ENTREGA_TARJETAS -> "Entrega de Tarjetas";
            case DISTRIBUCION_VIANDAS -> "Distribución de Viandas";
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
