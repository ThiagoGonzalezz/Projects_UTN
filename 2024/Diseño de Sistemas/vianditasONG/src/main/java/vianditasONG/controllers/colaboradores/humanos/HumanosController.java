package vianditasONG.controllers.colaboradores.humanos;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.FormularioController;
import vianditasONG.converters.dtoconverters.ConverterHumanoACrearDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.dtos.inputs.colaboradores.HumanoACrearDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.formulario.*;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.utils.ICrudViewsHandler;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class HumanosController implements ICrudViewsHandler, WithSimplePersistenceUnit, FormularioController {
    @Builder.Default
    private IHumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private ConverterHumanoACrearDTO converterHumanos = ServiceLocator.getService(ConverterHumanoACrearDTO.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);
    @Builder.Default
    private String nombreForm = Config.getInstancia().obtenerDelConfig("nombreFormHumano");
    @Override
    public void index(Context context) {


    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

        context.sessionAttribute("tipo-rol-a-crear", String.valueOf(Rol.PERSONA_HUMANA));

        Formulario formHumano = this.repoFormularios.buscarPorNombre(this.nombreForm).orElseThrow(ServerErrorException::new);

        List<PreguntaDTO> preguntasDTOs = formHumano.getPreguntas().stream().filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();

        model.put("preguntas", preguntasDTOs);

        context.render("sinUsuario/registro_persona_humana.hbs", model);
    }

    @Override
    public void save(Context context) throws IOException {

        HumanoACrearDTO humanoDTO = this.crearHumanoACrearDTO(context);

        FormularioRespondido formularioRespondido = this.crearFormularioRespondido(context, this.nombreForm);

        Humano humano = this.converterHumanos.convertToHumano(humanoDTO);

        humano.setFormularioRespondido(formularioRespondido);

        withTransaction(() -> {
            this.repoHumanos.guardar(humano);
        });

        context.sessionAttribute("colaborador-creado-id", humano.getId().toString());

        context.status(200);
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
    

    private HumanoACrearDTO crearHumanoACrearDTO(Context context){
        try {

            HumanoACrearDTO humanoDTO = HumanoACrearDTO.builder()
                    .nombre(context.formParam("nombre"))
                    .apellido(context.formParam("apellido"))
                    .fechaNacimiento(context.formParam("fechaNac"))
                    .build();

            String localidad = context.formParam("localidad");
            String calle = context.formParam("calle");
            String altura = context.formParam("altura");

            if (localidad != null && !calle.isEmpty() && !altura.isEmpty()) {
                humanoDTO.setLocalidadId(Long.parseLong(localidad));
                humanoDTO.setCalle(calle);
                humanoDTO.setAltura(altura);
            }

            for(String formaDeColab : context.formParams("formas-de-colaboracion[]")){
                humanoDTO.agregarFormasDeColaborar((formaDeColab));
            }

            List<String> listaContactos = context.formParams("contacto[]");

            for(String tipoContacto : context.formParams("tipo-contacto[]")){
                humanoDTO.agregarContactos(
                        ContactoDTO.builder()
                                .tipoContacto(tipoContacto)
                                .detalleContacto(listaContactos.remove(0))
                                .build());
            }

            return humanoDTO;
        } catch (RuntimeException e){
            throw new BadRequestException();
        }
    }
}
