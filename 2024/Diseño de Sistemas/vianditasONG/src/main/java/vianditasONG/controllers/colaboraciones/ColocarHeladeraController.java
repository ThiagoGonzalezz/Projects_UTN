package vianditasONG.controllers.colaboraciones;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterHeladeraDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.heladeras.ColocarHeladeraInputDTO;
import vianditasONG.dtos.outputs.DireccionDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;

import io.javalin.http.Context;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.servicios.conversorDirecAPunto.OpenCageAdapterAPI;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Geometry;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;
import vianditasONG.serviciosExternos.recomendadorDePuntos.ServicioRecomendadorDePuntos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.ListadoDePuntosEstrategicos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.PuntoMolde;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Builder
public class ColocarHeladeraController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private ModelosRepositorio repoModelos = ServiceLocator.getService(ModelosRepositorio.class);
    @Builder.Default
    ConverterHeladeraDTO converterHeladera = ServiceLocator.getService(ConverterHeladeraDTO.class);
    @Builder.Default
    LocalidadesRepositorio repoLocalidades = ServiceLocator.getService(LocalidadesRepositorio.class);
    @Builder.Default
    HacerseCargoDeHeladerasRepositorio repoHacerseCargo = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);
    @Builder.Default
    HeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private PersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private SensoresDeMovimientoRepositorio sensoresDeMovimientoRepositorio = ServiceLocator.getService(SensoresDeMovimientoRepositorio.class);
    @Builder.Default
    private SensoresDeTemperaturaRepositorio sensoresDeTemperaturaRepositorio = ServiceLocator.getService(SensoresDeTemperaturaRepositorio.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);

    public void index(Context context) {
        List<Modelo> modelos = repoModelos.buscarTodos();
        Long idPj = context.sessionAttribute("persona-juridica-id");

        PersonaJuridica personaJuridica = personasJuridicasRepositorio.buscarPorId(idPj)
                .orElseThrow(() -> new RuntimeException("Persona Juridica  no encontrada"));
        ;


        Map<String, Object> model = new HashMap<>();

        Formulario formHacerseCargoHeladera = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormHacerseCargoDeHeladera")).orElseThrow(ServerErrorException::new);
        List<PreguntaDTO> preguntasDTOs = formHacerseCargoHeladera.getPreguntas().stream()
                .filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        model.put("preguntas", preguntasDTOs);
        model.put("modelosHeladera", modelos);


        if (!(personaJuridica.getDireccion() == null)) {
            context.render("conUsuario/colocar_heladera.hbs", model);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    public void save(Context context) throws IOException {

        String[] partesDireccion = Objects.requireNonNull(context.formParam("punto-colocacion-direccion"))
                .split(", ");

        Localidad localidad = converterHeladera.
                convertToLocalidad(Objects.requireNonNull(context.formParam("punto-colocacion-direccion")));

        if (!localidad.isActivo()) {
            localidad.setActivo(true);
            withTransaction(() -> repoLocalidades.guardar(localidad));
        }

        ColocarHeladeraInputDTO dto = ColocarHeladeraInputDTO.builder()
                .nombre(context.formParam("nombre"))
                .modeloHeladeraId(Long.valueOf(Objects.requireNonNull(context.formParam("heladeraModelo"))))
                .calle(partesDireccion[1])
                .altura(partesDireccion[0])
                .localidadId(String.valueOf(localidad.getId()))
                .personaJuridicaId(context.sessionAttribute("persona-juridica-id"))
                .build();

        Heladera heladera = converterHeladera.convertToHeladeraAColocar(dto);

        SensorDeTemperatura sensorDeTemperatura = converterHeladera.conectarSensorTemperatura(heladera);
        SensorDeMovimiento sensorDeMovimiento = converterHeladera.conectarSensorMovimiento(heladera);


        PersonaJuridica persona = personasJuridicasRepositorio.buscarPorId(context.sessionAttribute("persona-juridica-id"))
                .orElseThrow(() -> new RuntimeException("Persona Juridica con id " + context.sessionAttribute("persona-juridica-id") + " no encontrada"));


        HacerseCargoDeHeladera hacerseCargoDeHeladera = HacerseCargoDeHeladera.builder()
                .fecha(LocalDate.now())
                .puntosAcumulados(0d)
                .colaborador(persona)
                .heladera(heladera)
                .build();

        withTransaction(() -> {

            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento);
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura);
            repoHeladeras.guardar(heladera);
            repoHacerseCargo.guardar(hacerseCargoDeHeladera);

        });

        context.status(200).json(gson.toJson(Map.of("success", true, "message", "Heladera colocada con éxito")));
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) throws IOException {

    }

    @Override
    public void delete(Context context) {

    }

}





