package vianditasONG.converters.dtoconverters;


import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.tecnicos.EditarTecnicoInputDTO;
import vianditasONG.dtos.inputs.tecnicos.SumarTecnicoInputDTO;
import vianditasONG.dtos.outputs.tecnicos.TecnicoOutputDTO;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.tecnicos.AreaDeCobertura;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Geometry;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;

import java.awt.geom.Area;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Builder
public class ConverterTecnicoDTO {

    @Builder.Default
    private ServicioOpenCage servicioOpenCage = ServiceLocator.getService(ServicioOpenCage.class);

    @Builder.Default
    private ConverterHeladeraDTO converterHeladeraDTO = ServiceLocator.getService(ConverterHeladeraDTO.class);


    public TecnicoOutputDTO convertToTecnicoOutputDTO(Tecnico tecnico) throws IOException {

        PuntoGeografico puntoGeo = new PuntoGeografico(tecnico.getAreaDeCobertura().getPuntoGeografico().getLatitud(), tecnico.getAreaDeCobertura().getPuntoGeografico().getLongitud());
        RespuestaOpenCage direccionObtenida = servicioOpenCage.obtenerDireccionDesdePunto(puntoGeo);
        List<Resultado> resultadosDireccion = direccionObtenida.getResults();


      TecnicoOutputDTO dto = TecnicoOutputDTO.builder()
              .cuil(tecnico.getCuil())
              .activo(tecnico.getActivo())
              .nombre(tecnico.getNombre())
              .apellido(tecnico.getApellido())
              .tipoDeDocumento(tecnico.getTipoDeDocumento().name())
              .numeroDeDocumento(tecnico.getNumeroDeDocumento())
              .tipoContacto(tecnico.getContactos().get(0).getTipoDeContacto().name())
              .contacto(tecnico.getContactos().get(0).getContacto())
              .areaDeCoberturaKM(tecnico.getAreaDeCobertura().getRadioEnKM())
              .areaDeCoberturaDireccion(resultadosDireccion.get(0).getFormatted())
              .build();

            return dto;
    }

    public Tecnico convertToTecnicoNuevo(SumarTecnicoInputDTO dto) throws IOException {

        Contacto contacto = Contacto.builder().tipoDeContacto(TipoDeContacto.valueOf(dto.getTipoContacto()))
                .contacto(dto.getContacto()).build();


        Provincia provincia = Provincia.valueOf(dto.getProvincia());

        Localidad localidad = Localidad.builder().provincia(provincia).nombre(dto.getLocalidad()).build();

        Direccion direccion = Direccion.builder()
                .calle(dto.getCalle())
                .altura(String.valueOf(dto.getAltura()))
                .localidad(localidad)
                .build();

        RespuestaOpenCage respuestaOpenCage = servicioOpenCage.puntoGeografico(direccion);

        List<Resultado> resultados = respuestaOpenCage.getResults();

        Geometry geometry = resultados.get(0).getGeometry();

        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .latitud(Double.parseDouble(geometry.getLat()))
                .longitud(Double.parseDouble(geometry.getLng()))
                .build();


        AreaDeCobertura areaDeCobertura = AreaDeCobertura.builder().radioEnKM(dto.getAreaDeCoberturaKM()).puntoGeografico(puntoGeografico).build();

        Tecnico tecnico = Tecnico.builder()
                .cuil(dto.getCuil())
                .activo(true)
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .tipoDeDocumento(TipoDeDocumento.valueOf(dto.getTipoDeDocumento()))
                .numeroDeDocumento(dto.getNumeroDeDocumento())
                .medioDeAviso(contacto.getTipoDeContacto().getMedioDeAviso())
                .areaDeCobertura(areaDeCobertura).build();

        tecnico.agregarContacto(contacto);
        return  tecnico;
    }

    public Tecnico convertToTecnicoActualizado(EditarTecnicoInputDTO dto, Tecnico tecnico) throws IOException {

        Contacto contacto = Contacto.builder().tipoDeContacto(TipoDeContacto.valueOf(dto.getTipoDeContacto())).contacto(dto.getContacto()).build();
        Localidad localidad = Localidad.builder().provincia(Provincia.valueOf(dto.getProvincia())).nombre(dto.getLocalidad()).build();

        Direccion direccion = Direccion.builder()
                .calle(dto.getCalle())
                .altura(String.valueOf(dto.getAltura()))
                .localidad(localidad)
                .build();

        RespuestaOpenCage respuestaOpenCage = servicioOpenCage.puntoGeografico(direccion);

        List<Resultado> resultados = respuestaOpenCage.getResults();

        Geometry geometry = resultados.get(0).getGeometry();

        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .latitud(Double.parseDouble(geometry.getLat()))
                .longitud(Double.parseDouble(geometry.getLng()))
                .build();


        AreaDeCobertura areaDeCobertura = AreaDeCobertura.builder().puntoGeografico(puntoGeografico).radioEnKM(dto.getAreaDeCoberturaKM()).build();

           tecnico.setNombre(dto.getNombre());
           tecnico.setApellido(dto.getApellido());
           tecnico.setTipoDeDocumento(TipoDeDocumento.valueOf(dto.getTipoDeDocumento()));
           tecnico.setNumeroDeDocumento(dto.getNumeroDeDocumento());
           tecnico.agregarContacto(contacto);
           tecnico.setAreaDeCobertura(areaDeCobertura);

           return tecnico;

    }
}

