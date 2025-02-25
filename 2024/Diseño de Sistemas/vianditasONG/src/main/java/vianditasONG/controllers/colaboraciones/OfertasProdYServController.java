package vianditasONG.controllers.colaboraciones;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterOfertasDto;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.converters.dtoconverters.ConverterRubrosOfertasDto;
import vianditasONG.dtos.inputs.colaboraciones.OfertaInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.dtos.outputs.colaboraciones.RubroOfertaOutputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.ofertas.IOfertasRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosOfertasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Builder
public class OfertasProdYServController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Builder.Default
    private RubrosOfertasRepositorio rubrosOfertasRepositorio = ServiceLocator.getService(RubrosOfertasRepositorio.class);
    @Builder.Default
    private ConverterRubrosOfertasDto converterRubrosOfertasDto = ServiceLocator.getService(ConverterRubrosOfertasDto.class);
    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private IOfertasRepositorio ofertasRepositorio = ServiceLocator.getService(OfertasRepositorio.class);
    @Builder.Default
    private ConverterOfertasDto converterOfertasDto = ServiceLocator.getService(ConverterOfertasDto.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);

    @Override
    public void index(Context context) {
        List<Oferta> ofertas = ofertasRepositorio.buscarTodosActivos();

        List<OfertaOutputDto> ofertasDTO = ofertas
                .stream()
                .map(converterOfertasDto::convertToDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("ofertas", ofertasDTO);

        if(context.sessionAttribute("tipo-rol") == "PERSONA_JURIDICA"){
            model.put("tipoRol","PERSONA_JURIDICA");
            Optional<PersonaJuridica> personaJuridica = personasJuridicasRepositorio.buscarPorId(context.sessionAttribute("persona-juridica-id"));
            model.put("puntosDisponibles", personaJuridica.get().getPuntos().intValue());
        }

        else if(context.sessionAttribute("tipo-rol") == "PERSONA_HUMANA"){
            model.put("tipoRol","PERSONA_HUMANA");
            Optional<Humano> humano = humanosRepositorio.buscar(context.sessionAttribute("humano-id"));
            model.put("puntosDisponibles", humano.get().getPuntos().intValue());
        }

        Boolean showSuccessPopup = context.sessionAttribute("showSuccessPopup");
        context.sessionAttribute("showSuccessPopup", null);
        model.put("showSuccessPopup", showSuccessPopup);

        Boolean showOfertedPopup = context.sessionAttribute("showOfertedPopup");
        context.sessionAttribute("showOfertedPopup", null);
        model.put("showSuccessPopupOferta", showOfertedPopup);

        context.render("conUsuario/catalogo_ofertas.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

        List<RubroOferta> rubrosOfertas = rubrosOfertasRepositorio.buscarTodos();

        List<RubroOfertaOutputDTO> rubroOfertaOutputDTOS = rubrosOfertas
                .stream()
                .map(converterRubrosOfertasDto::convertToDTO)
                .toList();



        Map<String, Object> model = new HashMap<>();
        model.put("rubros", rubroOfertaOutputDTOS);

        Formulario formProdsYServs = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormCanje")).orElseThrow(ServerErrorException::new);
        List<PreguntaDTO> preguntasDTOs = formProdsYServs.getPreguntas().stream()
                .filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        model.put("preguntas", preguntasDTOs);

        context.render("conUsuario/ofrecer_prod_y_serv.hbs", model);
    }

    @Override
    public void save(Context context) throws IOException {

        UploadedFile uploadedFile = context.uploadedFile("imagen");

        if (uploadedFile == null)
            throw new IOException("No se ha subido ningún archivo.");


        Path destination = Paths.get("uploads/" + uploadedFile.filename());

        Files.copy(uploadedFile.content(), destination, StandardCopyOption.REPLACE_EXISTING);


        OfertaInputDTO nuevaOferta = OfertaInputDTO.builder()
                .nombre(context.formParam("descripcion"))
                .puntosNecesarios(Double.valueOf(Objects.requireNonNull(context.formParam("puntosNecesarios"))))
                .rubroId(Long.valueOf(Objects.requireNonNull(context.formParam("rubro"))))
                .imagen("uploads/"+uploadedFile.filename())
                .ofertanteId(context.sessionAttribute("persona-juridica-id"))
                .build();

        Oferta oferta = converterOfertasDto.convertToOferta(nuevaOferta);

        withTransaction(() -> {
            ofertasRepositorio.guardar(oferta);
        });

        context.sessionAttribute("showOfertedPopup", true);
        context.redirect("/catalogo-productos-y-servicios");

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
