package vianditasONG.controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterPersonasVulnerablesDto;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboraciones.PersonaVulnerableInputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.exceptions.TarjetaInexistenteException;
import vianditasONG.exceptions.TarjetaYaUtilizadaException;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.repositorios.colaboracion.IRegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.RegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.IPersonasEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.imp.PersonaEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosOfertasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.util.*;

@Builder
public class PersonasVulnerablesController implements ICrudViewsHandler, WithSimplePersistenceUnit, FormularioController {

    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IPersonasEnSituacionVulnerableRepositorio personasEnSituacionVulnerableRepositorio = ServiceLocator.getService(PersonaEnSituacionVulnerableRepositorio.class);
    @Builder.Default
    private IRegistrosPersonasVulnerablesRepositorio registrosPersonasVulnerablesRepositorio = ServiceLocator.getService(RegistrosPersonasVulnerablesRepositorio.class);
    @Builder.Default
    private ITarjetasDePersonasVulnerablesRepositorio tarjetasDePersonasVulnerablesRepositorio = ServiceLocator.getService(TarjetasDePersonasVulnerablesRepositorio.class);
    @Builder.Default
    private ConverterPersonasVulnerablesDto converterPersonasVulnerablesDto = ServiceLocator.getService(ConverterPersonasVulnerablesDto.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);
    @Builder.Default
    CalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas = ServiceLocator.getService(CalculadorTarjetasDistribuidas.class);


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

        Formulario formPersonaVulnerable = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormPersonaVulnerable")).orElseThrow(ServerErrorException::new);

        List<PreguntaDTO> preguntasDTOs = formPersonaVulnerable.getPreguntas().stream().filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();

        model.put("preguntas", preguntasDTOs);

        context.render("conUsuario/registro_persona_vulnerable.hbs", model);

    }

    @Override
    public void save(Context context) {

        Optional<Humano> humanoOpcional = humanosRepositorio.buscar(context.sessionAttribute("humano-id"));
        if(humanoOpcional.isEmpty())
            throw new RuntimeException("No tomó el humano que cargó a la persona vulnerable");

        Humano humanoACargo = humanoOpcional.get();

        List<PersonaEnSituacionVulnerable> menores = registrarMenores(context);

        PersonaVulnerableInputDTO personaVulnerableInputDTO = registrarPersonaVulnerable(context);

        FormularioRespondido formularioRespondido = this.crearFormularioRespondido(context, Config.getInstancia().obtenerDelConfig("nombreFormPersonaVulnerable"));

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = converterPersonasVulnerablesDto.convertToPersonaVulnerable(personaVulnerableInputDTO);
        personaEnSituacionVulnerable.setFormularioRespondido(formularioRespondido);
        menores.forEach(personaEnSituacionVulnerable::agregarHijos);

        RegistroPersonasVulnerables registroPersonasVulnerables = RegistroPersonasVulnerables.builder()
                .personaVulnerable(personaEnSituacionVulnerable)
                .colaborador(humanoACargo)
                .fecha(LocalDate.now())
                .build();

        Optional<TarjetaDePersonaVulnerable> tarjetaDePersonaVulnerable =  tarjetasDePersonasVulnerablesRepositorio.buscarPorCodigo(personaVulnerableInputDTO.getCodigoDeTarjeta());

        if(tarjetaDePersonaVulnerable.isEmpty())
            throw new TarjetaInexistenteException();

        TarjetaDePersonaVulnerable tarjetaPersistida = tarjetaDePersonaVulnerable.get();

        if(tarjetaPersistida.getPersonaAsociada() != null)
            throw new TarjetaYaUtilizadaException();

        tarjetaPersistida.setPersonaAsociada(personaEnSituacionVulnerable);

        Double puntos = calculadorTarjetasDistribuidas.calcularPuntos(registroPersonasVulnerables);
        humanoACargo.sumarPuntos(puntos);

        withTransaction(() -> {
            personasEnSituacionVulnerableRepositorio.guardar(personaEnSituacionVulnerable);
            registrosPersonasVulnerablesRepositorio.guardar(registroPersonasVulnerables);
            tarjetasDePersonasVulnerablesRepositorio.actualizar(tarjetaPersistida);

            for (PersonaEnSituacionVulnerable menor : menores) {
                RegistroPersonasVulnerables registroMenor = RegistroPersonasVulnerables.builder()
                        .personaVulnerable(menor)
                        .colaborador(humanoACargo)
                        .fecha(LocalDate.now())
                        .build();

                humanoACargo.sumarPuntos(calculadorTarjetasDistribuidas.calcularPuntos(registroMenor));
                registrosPersonasVulnerablesRepositorio.guardar(registroMenor);
            }

            humanosRepositorio.actualizar(humanoACargo);
        });

        Map<String, Object> model = new HashMap<>();
        model.put("registroExitoso", true);
        context.render("conUsuario/registro_persona_vulnerable.hbs", model);

    }

    private PersonaVulnerableInputDTO registrarPersonaVulnerable(Context context) {

        return   PersonaVulnerableInputDTO.builder()
                .nombre(context.formParam("nombre"))
                .apellido(context.formParam("apellido"))
                .fechaDeNacimiento(context.formParam("fechaDeNacimiento"))
                .tipoDeDocumento(context.formParam("tipoDeDocumento"))
                .numeroDocumento(context.formParam("numeroDeDocumento"))
                .provincia(context.formParam("provincia"))
                .localidad(context.formParam("localidad"))
                .calle(context.formParam("calle"))
                .altura(context.formParam("altura"))
                .codigoDeTarjeta(context.formParam("codigoDeTarjeta"))
                .build();

    }

    private List<PersonaEnSituacionVulnerable> registrarMenores(Context context) {

        List<PersonaEnSituacionVulnerable> menores = new ArrayList<>();
        int cantidadMenores = Integer.parseInt(context.formParam("cantidadDeMenores"));
        for (int i = 0; i < cantidadMenores; i++) {
            PersonaVulnerableInputDTO menorInputDTO = PersonaVulnerableInputDTO.builder()
                    .nombre(context.formParam("menores[" + i + "][nombre]"))
                    .apellido(context.formParam("menores[" + i + "][apellido]"))
                    .fechaDeNacimiento(context.formParam("menores[" + i + "][fechaDeNacimiento]"))
                    .tipoDeDocumento(context.formParam("menores[" + i + "][tipoDeDocumento]"))
                    .numeroDocumento(context.formParam("menores[" + i + "][numeroDeDocumento]"))
                    .provincia(context.formParam("provincia"))
                    .localidad(context.formParam("localidad"))
                    .calle(context.formParam("calle"))
                    .altura(context.formParam("altura"))
                    .codigoDeTarjeta(context.formParam("codigoDeTarjeta"))
                    .build();

            PersonaEnSituacionVulnerable menor = converterPersonasVulnerablesDto.convertToPersonaVulnerable(menorInputDTO);
            menores.add(menor);
        }
        return menores;
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
