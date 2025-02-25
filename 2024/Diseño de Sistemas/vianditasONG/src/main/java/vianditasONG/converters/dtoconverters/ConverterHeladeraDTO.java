package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.heladeras.ColocarHeladeraInputDTO;
import vianditasONG.dtos.inputs.heladeras.EditarHeladeraInputDTO;
import vianditasONG.dtos.inputs.heladeras.SumarHeladeraInputDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraOutputDTO;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.controllers.receptores.ReceptorDeMovimientos;
import vianditasONG.controllers.receptores.ReceptorDeTemperaturas;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Geometry;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public class ConverterHeladeraDTO {

    @Builder.Default
    private LocalidadesRepositorio localidadesRepositorio = ServiceLocator.getService(LocalidadesRepositorio.class);

    @Builder.Default
    private ModelosRepositorio modelosRepositorio = ServiceLocator.getService(ModelosRepositorio.class);

    @Builder.Default
    private ServicioOpenCage servicioOpenCage = ServiceLocator.getService(ServicioOpenCage.class);

    @Builder.Default
    private PersonasJuridicasRepositorio repoPersonasJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);

    public HeladeraOutputDTO convertToOutputDTO(Heladera heladera){
        return HeladeraOutputDTO.builder()
                .id(heladera.getId())
                .estadoHeladera(String.valueOf(heladera.getEstadoHeladera()))
                .nombreHeladera(heladera.getNombre())
                .diasDesactivada(heladera.getDiasDesactivada())
                .modeloId(heladera.getModeloHeladera().getDescripcion())
                .fechaDeRegistro(String.valueOf(heladera.getFechaDeRegistro()))
                .cantidadViandas(heladera.getCantidadViandas())
                .fechaUltimaDesactivacion(String.valueOf(heladera.getFechaUltimaDesactivacion()))
                .temperatura(heladera.getUltimaTemperaturaRegistradaEnGradosCelsius())
                .fechaTempertauraRegistrada(String.valueOf(heladera.getFechaUltimatemperaturaRegistrada()))
                .nombrePuntoEstrategico(heladera.getPuntoEstrategico().getNombre())
                .build();
    }

    public Heladera convertToHeladeraNueva(SumarHeladeraInputDTO dto) throws IOException {


        Modelo modelo = modelosRepositorio.buscarPorId(Long.valueOf(dto.getModeloId()))
                .orElseThrow(() -> new IOException("Modelo no encontrado"));




        PuntoEstrategico puntoEstrategico = FormatearPuntoEstrategico(dto.getLocalidad(), dto.getCalle(), dto.getAltura(), dto.getNombrePuntoEstrategico());


        return Heladera.builder()
                .nombre(dto.getNombreHeladera())
                .cantidadViandas(0)
                .puntoEstrategico(puntoEstrategico)
                .modeloHeladera(modelo)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .diasDesactivada(0)
                .ultimaTemperaturaRegistradaEnGradosCelsius(null)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .fechaUltimoCalculo(LocalDateTime.now())
                .build();
    }

    public Heladera ConvertToHeladeraActualizada(EditarHeladeraInputDTO dto, Heladera heladera) throws IOException {


        Modelo modelo = modelosRepositorio.buscarPorId(Long.valueOf(dto.getModeloId()))
                .orElseThrow(() -> new IOException("Modelo no encontrado"));


        PuntoEstrategico puntoEstrategico = FormatearPuntoEstrategico(dto.getLocalidad(), dto.getCalle(), dto.getAltura(), dto.getNombrePuntoEstrategico());


        heladera.setNombre(dto.getNombreHeladera());
        heladera.setModeloHeladera(modelo);
        heladera.setPuntoEstrategico(puntoEstrategico);
        heladera.setCantidadViandas(dto.getCantidadViandas());
        heladera.setEstadoHeladera(EstadoHeladera.valueOf(dto.getEstadoHeladera()));
        heladera.setUltimaTemperaturaRegistradaEnGradosCelsius(dto.getTemperatura());



       return  heladera;
    }

    public EditarHeladeraInputDTO convertToEditarHeladeraDTO(Heladera heladera){
        return EditarHeladeraInputDTO.builder()
                .heladeraId(heladera.getId())
                .nombreHeladera(heladera.getNombre())
                .altura(heladera.getPuntoEstrategico().getDireccion().getAltura())
                .calle(heladera.getPuntoEstrategico().getDireccion().getCalle())
                .localidad(heladera.getPuntoEstrategico().getDireccion().getLocalidad().getNombre())
                .temperatura(heladera.getUltimaTemperaturaRegistradaEnGradosCelsius())
                .modeloId(heladera.getModeloHeladera().getDescripcion())
                .cantidadViandas(heladera.getCantidadViandas())
                .estadoHeladera(String.valueOf(heladera.getEstadoHeladera()))
                .nombrePuntoEstrategico(heladera.getPuntoEstrategico().getNombre())
                .build();
    }


    public PuntoEstrategico FormatearPuntoEstrategico(String localidad, String calle, String altura, String nombre) throws IOException {

        Localidad localidadNueva = localidadesRepositorio.buscarPorId(Long.valueOf(localidad))
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));

        Direccion direccion = Direccion.builder()
                .calle(calle)
                .altura(altura)
                .localidad(localidadNueva)
                .build();

        RespuestaOpenCage respuestaOpenCage = servicioOpenCage.puntoGeografico(direccion);

        List<Resultado> resultados = respuestaOpenCage.getResults();

        Geometry geometry = resultados.get(0).getGeometry();

        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .latitud(Double.parseDouble(geometry.getLat()))
                .longitud(Double.parseDouble(geometry.getLng()))
                .build();


        return PuntoEstrategico.builder()
                .direccion(direccion)
                .puntoGeografico(puntoGeografico)
                .nombre(nombre)
                .build();


    }


    public Heladera convertToHeladeraAColocar(ColocarHeladeraInputDTO dto) throws IOException {

        Modelo modelo = modelosRepositorio.buscarPorId(dto.getModeloHeladeraId())
                .orElseThrow(() -> new RuntimeException("Modelo con ID " + dto.getModeloHeladeraId() + " no encontrado"));

        PersonaJuridica personaJuridica = repoPersonasJuridicas.buscarPorId(dto.getPersonaJuridicaId())
                .orElseThrow(() -> new RuntimeException("Persona Juridica con id "+dto.getPersonaJuridicaId()+" no encontrada"));


        String nombrePuntoEstrategico= dto.getNombre();

        PuntoEstrategico puntoEstrategico = FormatearPuntoEstrategico(dto.getLocalidadId(), dto.getCalle(), dto.getAltura(), nombrePuntoEstrategico);


        return Heladera.builder()
                .nombre(dto.getNombre())
                .modeloHeladera(modelo)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .puntoEstrategico(puntoEstrategico)
                .fechaDeRegistro(LocalDateTime.now())
                .cantidadViandas(0)
                .diasDesactivada(0)
                .mesesActiva(0)
                .build();
    }

    public Localidad convertToLocalidad(String direccion) {

        String[] partesDireccion = direccion.split(", ");

        String localidadRecibida = null;
        String provincia = null;


        for (int i = 0; i < partesDireccion.length; i++) {
            String parte = partesDireccion[i];

            if (Provincia.esProvinciaValida(parte)) {
                if (i > 0) {
                    localidadRecibida = partesDireccion[i - 1];
                    provincia = partesDireccion[i];
                } else {
                    System.out.println(Config.getInstancia().obtenerDelConfig("mProvinciaNoDetectada"));
                }
                break;
            }
        }


        if (provincia == null || localidadRecibida == null) {
            System.out.println("Error: Provincia o localidad no detectada.");
            return null;
        }


        Provincia provinciaLocalidad = Provincia.fromString(provincia);
        List<Localidad> localidadesProvincia = localidadesRepositorio.buscarTodosPorProvincia(provinciaLocalidad);


        final String localidadFinal = localidadRecibida;
        Optional<Localidad> localidadEncontrada = localidadesProvincia.stream()
                .filter(localidad -> localidad.getNombre().equalsIgnoreCase(localidadFinal))
                .findFirst();


        if (localidadEncontrada.isEmpty()) {
            Localidad nuevaLocalidad = Localidad.builder()
                    .nombre(localidadRecibida)
                    .provincia(provinciaLocalidad)
                    .build();
            nuevaLocalidad.setActivo(false);
            return nuevaLocalidad;
        } else {
            return localidadEncontrada.get();
        }
    }

    public SensorDeTemperatura conectarSensorTemperatura(Heladera heladera) {
        SensorDeTemperatura sensorDeTemperatura = SensorDeTemperatura.builder().heladera(heladera).build();

        ReceptorDeTemperaturas receptorDeTemperaturas = ServiceLocator.getService(ReceptorDeTemperaturas.class);

        receptorDeTemperaturas.agregarSensores(sensorDeTemperatura);

        return sensorDeTemperatura;
    }

    public SensorDeMovimiento conectarSensorMovimiento(Heladera heladera) {
        SensorDeMovimiento sensorDeMovimiento = SensorDeMovimiento.builder().heladera(heladera).build();

        ReceptorDeMovimientos receptorDeMovimientos = ServiceLocator.getService(ReceptorDeMovimientos.class);

        receptorDeMovimientos.agregarSensores(sensorDeMovimiento);

        return sensorDeMovimiento;
    }
}
