package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.dtos.inputs.colaboradores.HumanoACrearDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public class ConverterHumanoACrearDTO {
    @Builder.Default
    private ILocalidadesRepositorio repoLocalidades = ServiceLocator.getService(LocalidadesRepositorio.class);

    public Humano convertToHumano(HumanoACrearDTO humanoDTO) throws IOException {
        LocalDate fechaNac = null;

        if(!humanoDTO.getFechaNacimiento().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fechaNac = LocalDate.parse(humanoDTO.getFechaNacimiento(), formatter);
        }

        Humano humano = Humano.builder()
                .nombre(humanoDTO.getNombre())
                .apellido(humanoDTO.getApellido())
                .fechaDeSolicitud(LocalDate.now())
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaNacimiento(fechaNac)
                .build();

        this.procesarDireccion(humanoDTO, humano);

        this.procesarFormasDeColaboracion(humanoDTO, humano);

        this.procesarContactos(humanoDTO, humano);

        return humano;
    }

    private void procesarDireccion(HumanoACrearDTO humanoDTO, Humano humano) throws IOException {
        if (humanoDTO.getLocalidadId()!= null &&
                humanoDTO.getCalle() != null &&
                humanoDTO.getAltura() != null) {

            Direccion direccion = Direccion.builder()
                    .localidad(this.repoLocalidades.buscarPorId(humanoDTO.getLocalidadId()).orElseThrow(BadRequestException::new))
                    .calle(humanoDTO.getCalle())
                    .altura(humanoDTO.getAltura())
                    .build();

            humano.setDireccion(direccion);

        }
    }

    private void procesarFormasDeColaboracion(HumanoACrearDTO humanoDTO, Humano humano){
        for(String formaDeColab: humanoDTO.getFormasDeColaborarIds()){
            humano.agregarFormasDeColab(FormaDeColaboracion.valueOf(formaDeColab));
        }
    }

    private void procesarContactos(HumanoACrearDTO humanoACrearDTO, Humano humano){
        for(ContactoDTO contactoDTO: humanoACrearDTO.getContactos()){
            humano.agregarContacto(Contacto.builder()
                    .tipoDeContacto(TipoDeContacto.valueOf(contactoDTO.getTipoContacto()))
                    .contacto(contactoDTO.getDetalleContacto())
                    .build()
            );
        }
    }
}

