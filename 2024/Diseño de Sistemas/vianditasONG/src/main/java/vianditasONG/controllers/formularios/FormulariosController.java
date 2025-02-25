package vianditasONG.controllers.formularios;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterFormEstadisticaDTO;
import vianditasONG.converters.dtoconverters.ConverterPregEditDTO;
import vianditasONG.dtos.inputs.formularios.PreguntaInputDTO;
import vianditasONG.dtos.inputs.usuario.UsuarioDTO;
import vianditasONG.dtos.outputs.ColaboradorOutputDTO;
import vianditasONG.dtos.outputs.formulario.FormularioEstadisticaDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaEditableOutputDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.formulario.Pregunta;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.PreguntasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FormulariosController implements WithSimplePersistenceUnit {

    private FormulariosRepositorio repoForms = ServiceLocator.getService(FormulariosRepositorio.class);

    private ConverterFormEstadisticaDTO converterFormOutputDTO = ServiceLocator.getService(ConverterFormEstadisticaDTO.class);

    private ConverterPregEditDTO converterPregEditDTO = ServiceLocator.getService(ConverterPregEditDTO.class);

    private PreguntasRepositorio repoPreguntas = ServiceLocator.getService(PreguntasRepositorio.class);

    private final Gson gson = new Gson();
    public void index(Context context) {
        List<Formulario> forms = this.repoForms.buscarTodos();

        List<FormularioEstadisticaDTO> listaFormsDtos = forms
                .stream()
                .map(f -> this.converterFormOutputDTO.convertToDTO(f))
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("formularios", listaFormsDtos);
        context.status(200).render("admin/formularios/formularios_index.hbs", model);
    }


    public void create(Context context) {
        Formulario form = Formulario.builder()
                .esEstablecido(false)
                .nombre("Ingrese el nombre del formulario")
                .build();

        form.setActivo(true);

        withTransaction(() -> this.repoForms.guardar(form));

        context.status(200).redirect("/formularios/" + form.getId() + "/edicion");
    }

    public void edit(Context context) throws IOException {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        List<PreguntaEditableOutputDTO> listaPregsDtos = form
                .getPreguntas()
                .stream()
                .map(p -> this.converterPregEditDTO.convertToDTO(p))
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("preguntas", listaPregsDtos);
        model.put("nombreForm", form.getNombre());
        model.put("tipoForm", form.getEsEstablecido()? "Establecido" : "No Establecido");
        context.status(200).render("admin/formularios/modificar_formulario.hbs", model);
    }


    public void updateNombre(Context context) throws IOException {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        String nuevoNombre = context.formParam("nombre");

        form.setNombre(nuevoNombre);

        withTransaction(() -> this.repoForms.actualizar(form));

        context.status(200);

    }

    public void delete(Context context) {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        form.setActivo(false);

        withTransaction(() -> this.repoForms.actualizar(form));

        context.status(200);
    }

    public void deletePregunta(Context context) {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        Long idPreg = Long.valueOf(context.pathParam("id-preg"));

        form.getPreguntas().removeIf(p -> p.getId().equals(idPreg));

        withTransaction(() -> this.repoForms.actualizar(form));

        context.status(200);
    }

    public void updatePregunta(Context context) {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        PreguntaInputDTO preguntaInputEditDto = gson.fromJson(context.body(), PreguntaInputDTO.class);

        Pregunta pregunta = this.converterPregEditDTO.convertToEntity(preguntaInputEditDto);

        pregunta.setId(Long.parseLong(preguntaInputEditDto.getId()));

        form.getPreguntas().replaceAll(p ->
                p.getId().equals(pregunta.getId()) ? pregunta : p
        );

        pregunta.setId(this.repoPreguntas.generarId());

        withTransaction(() -> this.repoForms.actualizar(form));

        context.status(200).json(Map.of("id", pregunta.getId()));
    }

    public void createPregunta(Context context) {
        Formulario form = this.repoForms.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        PreguntaInputDTO preguntaInputEditDto = gson.fromJson(context.body(), PreguntaInputDTO.class);

        Pregunta pregunta = this.converterPregEditDTO.convertToEntity(preguntaInputEditDto);

        //pregunta.setId(this.repoPreguntas.generarId());

        form.agregarPregunta(pregunta);

        withTransaction(() -> {this.repoPreguntas.guardar(pregunta); this.repoForms.actualizar(form);});

        context.status(200).json(Map.of("id", pregunta.getId()));

    }
}
