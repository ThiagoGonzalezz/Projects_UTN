package vianditasONG.controllers.colaboraciones;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterDonacionDineroDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboraciones.DonacionDeDineroInputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeDineroRepositorio;

import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.*;

@Builder
public class DonacionesDeDineroController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    private final Gson gson = new Gson();

    @Builder.Default
    private DonacionesDeDineroRepositorio repoDonaciones = ServiceLocator.getService(DonacionesDeDineroRepositorio.class);
    @Builder.Default
    private ConverterDonacionDineroDTO converter = ServiceLocator.getService(ConverterDonacionDineroDTO.class);
    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);

    public void index(Context context) {

        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo-rol");
        model.put("tipoRol", tipoRol);

        if ("PERSONA_HUMANA".equalsIgnoreCase(tipoRol)) {
            Long idHumano = context.sessionAttribute("humano-id");
            if (idHumano == null) {
                throw new IllegalArgumentException("El ID del humano no puede ser nulo.");
            }
            Optional<Humano> humanoOptional = humanosRepositorio.buscar(idHumano);
            Humano humano = humanoOptional.orElseThrow(() ->
                    new NoSuchElementException("No se encontró un humano con el ID: " + idHumano)
            );
            List<FormaDeColaboracion> formasDeColaboracion = humano.getFormasDeColaboracion();
            if (!formasDeColaboracion.contains(FormaDeColaboracion.DONACION_DINERO)) {
                throw new AccessDeniedException();
            }

        } else if ("persona_juridica".equalsIgnoreCase(tipoRol)) {
            Long idPersonaJuridica = context.sessionAttribute("persona-juridica-id");
            if (idPersonaJuridica == null) {
                throw new IllegalArgumentException("El ID de la persona jurídica no puede ser nulo.");
            }
            Optional<PersonaJuridica> juridicaOptional = personasJuridicasRepositorio.buscarPorId(idPersonaJuridica);
            PersonaJuridica juridica = juridicaOptional.orElseThrow(() ->
                    new NoSuchElementException("No se encontró una persona jurídica con el ID: " + idPersonaJuridica)
            );

            List<FormaDeColaboracion> formasDeColaboracion = juridica.getFormasDeColaboracion();
            if (!formasDeColaboracion.contains(FormaDeColaboracion.DONACION_DINERO)) {
                throw new AccessDeniedException();
            }
        } else {
            throw new AccessDeniedException();
        }

        Formulario formDonacionDeDinero = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormDonacionDeDinero")).orElseThrow(ServerErrorException::new);
        List<PreguntaDTO> preguntasDTOs = formDonacionDeDinero.getPreguntas().stream()
                .filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        model.put("preguntas", preguntasDTOs);

        context.render("conUsuario/donacion_dinero.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    public void save(Context context) {

        Long idUsuario = null;
        boolean esHumano = true;
        if ("PERSONA_JURIDICA".equals(context.sessionAttribute("tipo-rol"))) {
            idUsuario = context.sessionAttribute("persona-juridica-id");
            esHumano = false;
        } else if ("PERSONA_HUMANA".equals(context.sessionAttribute("tipo-rol"))) {

            idUsuario = context.sessionAttribute("humano-id");
        }


        DonacionDeDineroInputDTO donacionInputDTO = DonacionDeDineroInputDTO.builder()
                .idUsuario(idUsuario)
                .monto(Double.parseDouble(Objects.requireNonNull(context.formParam("monto"))))
                .frecuenciaDonacion(FrecuenciaDonacion
                        .fromFrecuenciaEnDias(Integer.valueOf(Objects.requireNonNull(context.formParam("frecuenciaDonacion")))))
                .build();

        DonacionDeDinero nuevaDonacion = converter.convertToDonacion(donacionInputDTO,esHumano);



        withTransaction(() -> repoDonaciones.guardar(nuevaDonacion));


        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Donación realizada con éxito");

        String respuestaJSON = gson.toJson(responseMessage);


        context.status(200).json(respuestaJSON);



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


