package vianditasONG.controllers.heladeras;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterHeladeraDTO;
import vianditasONG.dtos.inputs.heladeras.EditarHeladeraInputDTO;
import vianditasONG.dtos.inputs.heladeras.SumarHeladeraInputDTO;
import vianditasONG.dtos.outputs.IncidenteDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.Modelo;

import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IncidentesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;

import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public class GestionDeHeladerasController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    private HeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private ConverterHeladeraDTO converterHeladeraDTO = ServiceLocator.getService(ConverterHeladeraDTO.class);
    @Builder.Default
    private ModelosRepositorio modelosRepositorio = ServiceLocator.getService(ModelosRepositorio.class);
    @Builder.Default
    private LocalidadesRepositorio localidadesRepositorio = ServiceLocator.getService(LocalidadesRepositorio.class);
    @Builder.Default
    private SensoresDeMovimientoRepositorio sensoresDeMovimientoRepositorio = ServiceLocator.getService(SensoresDeMovimientoRepositorio.class);
    @Builder.Default
    private SensoresDeTemperaturaRepositorio sensoresDeTemperaturaRepositorio = ServiceLocator.getService(SensoresDeTemperaturaRepositorio.class);

    @Builder.Default
    private HacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);
    @Builder.Default
    private PersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);

    @Builder.Default
    private final Gson gson = new Gson();

    @Override
    public void index(Context context) {

        List<Modelo> modelos = modelosRepositorio.buscarTodos();
        List<Localidad> localidades = localidadesRepositorio.buscarTodos();
        List<PersonaJuridica> personasJuridicas = personasJuridicasRepositorio.buscarTodos().stream()
                .filter(persona -> persona.getFormasDeColaboracion().contains(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS))
                .collect(Collectors.toList());

        context.render("admin/gestion_de_heladeras.hbs", Map.of( "modelos", modelos, "localidades", localidades, "personasJuridicas", personasJuridicas));
    }

    @Override
    public void save(Context context) throws IOException {
        SumarHeladeraInputDTO heladeraInputDTO = SumarHeladeraInputDTO.builder()
                .nombreHeladera(context.formParam("heladeraNombre"))
                .modeloId(context.formParam("heladeraModelo"))
                .calle(context.formParam("calle"))
                .altura(context.formParam("altura"))
                .localidad(context.formParam("localidad"))
                .nombrePuntoEstrategico(context.formParam("nombrePuntoEstrategico"))
                .encargado(Long.valueOf(Objects.requireNonNull(context.formParam("personaJuridica"))))
                .build();


        Heladera heladera = converterHeladeraDTO.convertToHeladeraNueva(heladeraInputDTO);

        SensorDeTemperatura sensorDeTemperatura = converterHeladeraDTO.conectarSensorTemperatura(heladera);
        SensorDeMovimiento sensorDeMovimiento = converterHeladeraDTO.conectarSensorMovimiento(heladera);

        PersonaJuridica administracion = personasJuridicasRepositorio.buscarPorId(heladeraInputDTO.getEncargado())
                .orElseThrow(() -> new RuntimeException("No se encontro al encargado"));;
        HacerseCargoDeHeladera hacerseCargoDeHeladera = HacerseCargoDeHeladera.builder()
                .colaborador(administracion)
                .heladera(heladera)
                .build();

        withTransaction(() -> {
            hacerseCargoDeHeladerasRepositorio.guardar(hacerseCargoDeHeladera);
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento);
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura);
            heladerasRepositorio.guardar(heladera);
        });

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Heladera añadida con éxito");

        String respuestaJSON = gson.toJson(responseMessage);


        context.status(200).json(respuestaJSON);

    }

    @Override
    public void edit(Context context) {
        Long id = context.pathParamAsClass("id", Long.class).get();
        Heladera heladera = heladerasRepositorio.buscar(id)
                .orElseThrow(() -> new RuntimeException("Heladera no encontrada"));


        EditarHeladeraInputDTO heladeraDTO = EditarHeladeraInputDTO.builder()
                .heladeraId(heladera.getId())
                .nombreHeladera(heladera.getNombre())
                .modeloId(heladera.getModeloHeladera().getId().toString())
                .nombrePuntoEstrategico(heladera.getPuntoEstrategico().getNombre())
                .cantidadViandas(heladera.getCantidadViandas())
                .estadoHeladera(heladera.getEstadoHeladera().name())
                .temperatura(heladera.getUltimaTemperaturaRegistradaEnGradosCelsius())
                .altura(heladera.getPuntoEstrategico().getDireccion().getAltura())
                .calle(heladera.getPuntoEstrategico().getDireccion().getCalle())
                .localidad(heladera.getPuntoEstrategico().getDireccion().getLocalidad().getNombre())
                .build();

        String respuestaJSON = gson.toJson(heladeraDTO);

        context.json(respuestaJSON);
    }

    @Override
    public void update(Context context) throws IOException {
        Long id = Long.valueOf(context.pathParam("id"));
        Heladera heladera = heladerasRepositorio.buscar(id)
                .orElseThrow(() -> new RuntimeException("Heladera no encontrada"));


        EditarHeladeraInputDTO heladeraInputDTO = EditarHeladeraInputDTO.builder()
                .heladeraId(Long.valueOf(Objects.requireNonNull(context.formParam("heladeraId"))))
                .nombreHeladera(context.formParam("nombreHeladera"))
                .modeloId(context.formParam("modeloId"))
                .nombrePuntoEstrategico(context.formParam("nombrePuntoEstrategico"))
                .cantidadViandas(Integer.parseInt(Objects.requireNonNull(context.formParam("cantidadViandas"))))
                .estadoHeladera(context.formParam("estadoHeladera"))
                .temperatura(Float.parseFloat(Objects.requireNonNull(context.formParam("temperaturaHeladera"))))
                .altura(context.formParam("altura"))
                .calle(context.formParam("calle"))
                .localidad(context.formParam("localidad"))
                .build();

        System.out.println("estaado HELADERAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+ context.formParam("nombreHeladera"));



        Heladera heladeraActualizada = converterHeladeraDTO.ConvertToHeladeraActualizada(heladeraInputDTO,heladera);

        withTransaction(() -> heladerasRepositorio.actualizar(heladeraActualizada));

        heladeraActualizada.notificarSuscriptoresDeCantidad();
        heladeraActualizada.notificarSuscriptoresDeDesperfectos();

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Heladera actualizada con éxito");

        String respuestaJSON = gson.toJson(responseMessage);


        context.status(200).json(respuestaJSON);

    }

    @Override
    public void delete(Context context) {
        Long id = Long.valueOf(context.pathParam("id"));

        Heladera heladera = heladerasRepositorio.buscar(id)
                .orElseThrow(() -> new RuntimeException("Heladera no encontrada"));

        withTransaction(() -> heladerasRepositorio.eliminarLogico(heladera));

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Heladera eliminada con éxito");

        String respuestaJSON = gson.toJson(responseMessage);


        context.status(200).json(respuestaJSON);

    }

    public void obtenerIncidentesDeHeladera(Context context) {
        Long id = context.pathParamAsClass("id", Long.class).get();

        // Obtener los incidentes asociados a la heladera
        List<Incidente> incidentes = ServiceLocator.getService(IncidentesRepositorio.class)
                .buscarPorHeladeraId(id);

        if (incidentes.isEmpty()) {
            // Devolver un mensaje si no hay incidentes
            context.json(Map.of("mensaje", "Esta heladera nunca tuvo incidentes"));
        } else {
            // Convertir los incidentes a DTOs
            List<IncidenteDTO> incidentesDTO = incidentes.stream()
                    .map(incidente -> new IncidenteDTO(
                            incidente.getDescripcion(),
                            incidente.getFotoResolucion(),
                            incidente.getFechaResolucion() != null ? incidente.getFechaResolucion().toString() : "No resuelto",
                            incidente.getTecnicoResolutor() != null ? incidente.getTecnicoResolutor().getCuil() : "No asignado",
                            incidente.getFechaYHora().toString()
                    ))
                    .collect(Collectors.toList());

            // Devolver la lista de DTOs en formato JSON
            context.json(incidentesDTO);
        }
    }


    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }


}





