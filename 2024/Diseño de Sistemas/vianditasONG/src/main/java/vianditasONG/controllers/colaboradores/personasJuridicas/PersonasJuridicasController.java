package vianditasONG.controllers.colaboradores.personasJuridicas;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.FormularioController;
import vianditasONG.converters.dtoconverters.ConverterPersonaJuridicaACrearDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.dtos.inputs.colaboradores.PersonaJuridicaACrearDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public class PersonasJuridicasController implements FormularioController, ICrudViewsHandler, WithSimplePersistenceUnit {
    @Builder.Default
    private IPersonasJuridicasRepositorio repoPersonasJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private ConverterPersonaJuridicaACrearDTO converterPersonasJuridicas = ServiceLocator.getService(ConverterPersonaJuridicaACrearDTO.class);
    @Builder.Default
    private IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    private ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);
    @Builder.Default
    private String nombreForm = Config.getInstancia().obtenerDelConfig("nombreFormPersonasJuridicas");


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        context.sessionAttribute("tipo-rol-a-crear", String.valueOf(Rol.PERSONA_JURIDICA));


        Formulario formPersonasJuridicas = this.repoFormularios.buscarPorNombre(this.nombreForm).orElseThrow(ServerErrorException::new);

        List<PreguntaDTO> preguntasDTOs = formPersonasJuridicas.getPreguntas().stream()
                .filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();

        model.put("preguntas", preguntasDTOs);

        context.render("sinUsuario/registro_persona_juridica.hbs", model);

    }

    @Override
    public void save(Context context) throws IOException {

        PersonaJuridicaACrearDTO  personaJuridicaDTO = this.crearPersonaJuridicaACrearDTO(context);

        FormularioRespondido formularioRespondido = this.crearFormularioRespondido(context, this.nombreForm);

        PersonaJuridica personaJuridica = this.converterPersonasJuridicas.convertToPersonaJuridica(personaJuridicaDTO);

        personaJuridica.setFormularioRespondido(formularioRespondido);

        withTransaction(() -> {
            this.repoPersonasJuridicas.guardar(personaJuridica);
        });

        context.sessionAttribute("colaborador-creado-id", personaJuridica.getId().toString());

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

    private PersonaJuridicaACrearDTO crearPersonaJuridicaACrearDTO(Context context){
        try {

            PersonaJuridicaACrearDTO personaJuridicaDTO = PersonaJuridicaACrearDTO.builder()
                    .razonSocial(context.formParam("razon-social"))
                    .rubroId(Long.parseLong(Objects.requireNonNull(context.formParam("rubro"))))
                    .tipoDeOrganizacion(context.formParam("tipo-organizacion"))
                    .build();

            String localidad = context.formParam("localidad");
            String calle = context.formParam("calle");
            String altura = context.formParam("altura");

            if (localidad != null && !calle.isEmpty() && !altura.isEmpty()) {
                personaJuridicaDTO.setLocalidadId(Long.parseLong(localidad));
                personaJuridicaDTO.setCalle(calle);
                personaJuridicaDTO.setAltura(altura);
            }

            for(String formaDeColab : context.formParams("formas-de-colaboracion[]")){
                personaJuridicaDTO.agregarFormasDeColaborar((formaDeColab));
            }

            List<String> listaContactos = context.formParams("contacto[]");

            for(String tipoContacto : context.formParams("tipo-contacto[]")){
                personaJuridicaDTO.agregarContactos(
                        ContactoDTO.builder()
                                .tipoContacto(tipoContacto)
                                .detalleContacto(listaContactos.remove(0))
                                .build());
            }

            return personaJuridicaDTO;
        } catch (RuntimeException e){
            throw new BadRequestException();
        }
    }
}
