package vianditasONG.controllers.colaboraciones;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.FormularioController;
import vianditasONG.converters.dtoconverters.ConverterDistribucionViandaDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboraciones.DistribucionDeViandaInputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.exceptions.CantViandasErroneasException;
import vianditasONG.exceptions.DistribuirViandasHeladerasDupException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.PublicadorSolicitudApertura;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.TipoDeAccion;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.IAccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class DistribucionesDeViandasController implements ICrudViewsHandler, WithSimplePersistenceUnit, FormularioController {
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    private IDistribucionesDeViandasRepositorio distribucionesDeViandasRepositorio = ServiceLocator.getService(DistribucionesDeViandasRepositorio.class);
    private ConverterDistribucionViandaDTO converterDistribucionViandaDTO = ServiceLocator.getService(ConverterDistribucionViandaDTO.class);
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);
    IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
    TarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = ServiceLocator.getService(TarjetasColaboradorRepositorio.class);
    IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio = ServiceLocator.getService(AccionesSolicitadasRepositorio.class);

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Long idHumano = context.sessionAttribute("humano-id");

        Optional<Humano> humanoOptional = humanosRepositorio.buscar(idHumano);
        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una heladera con el ID: " + idHumano)
        );

        List<FormaDeColaboracion> formasDeColaboracion = humano.getFormasDeColaboracion();

        Formulario formDistribViandas = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormDistribucionDeViandas")).orElseThrow(ServerErrorException::new);
        List<PreguntaDTO> preguntasDTOs = formDistribViandas.getPreguntas().stream()
                .filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("preguntas", preguntasDTOs);


        if(formasDeColaboracion.contains(FormaDeColaboracion.DISTRIBUCION_VIANDAS)){
            context.render("conUsuario/distribucion_viandas.hbs", model);
        }
        else {
            throw new AccessDeniedException();
        }

    }

    @Override
    public void save(Context context) throws IOException {

        int idHeladeraDestino = (Integer.parseInt(Objects.requireNonNull(context.formParam("heladera-destino-id"))));
        int idHeladeraOrigen = (Integer.parseInt(Objects.requireNonNull(context.formParam("heladera-origen-id"))));

        if (idHeladeraDestino == idHeladeraOrigen) {
            throw new DistribuirViandasHeladerasDupException();
        }

        FormularioRespondido formularioRespondido = this.crearFormularioRespondido(context, (Config.getInstancia().obtenerDelConfig("nombreFormDistribucionDeViandas")));

        DistribucionDeViandaInputDTO distribucionDeViandaInputDTO = DistribucionDeViandaInputDTO.builder()
                .idHumano(context.sessionAttribute("humano-id"))
                .cantViandas(Integer.valueOf(Objects.requireNonNull(context.formParam("cantidad-viandas"))))
                .motivoDistribucion(Objects.requireNonNull(context.formParam("motivo-de-distribucion")))
                .idHeladeraDestino(idHeladeraDestino)
                .idHeladeraOrigen(idHeladeraOrigen)
                .build();

        DistribucionDeVianda distribucionDeVianda = converterDistribucionViandaDTO.converToDistribucionDeVianda(distribucionDeViandaInputDTO);
        distribucionDeVianda.setActivo(Boolean.FALSE); //Porque no es valido hasta que se efectue realmente por MQTT

        Humano humano = distribucionDeVianda.getColaborador();
        distribucionDeVianda.setFormularioRespondido(formularioRespondido);

        Heladera heladeraDestino = distribucionDeVianda.getHeladeraDestino();
        Heladera heladeraOrigen = distribucionDeVianda.getHeladeraOrigen();
        Integer cantViandasMovidas = distribucionDeVianda.getCantidadDeViandas();

        try{
        heladeraDestino.agregarViandas(cantViandasMovidas);
        heladeraOrigen.quitarViandas(cantViandasMovidas);}
        catch (RuntimeException e){
            throw new CantViandasErroneasException();
        }

        Optional<TarjetaColaborador> tarjetaOptional = tarjetasColaboradorRepositorio.buscarPorHumano(humano);
        TarjetaColaborador tarjetaColaborador = tarjetaOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una tarjeta asociada") //TODO: Ver porque el humano no debería llegar aca en caso de no tener tarjeta
        );

        withTransaction(() -> {
            distribucionesDeViandasRepositorio.guardar(distribucionDeVianda);
            humanosRepositorio.actualizar(humano);
            heladerasRepositorio.actualizar(heladeraDestino);
            heladerasRepositorio.actualizar(heladeraOrigen);
        });

        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .tipoDeAccion(TipoDeAccion.APERTURA_PARA_INGRESAR_DONACION)
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now().plusHours(Long.parseLong(Config.getInstancia().obtenerDelConfig("horasParaAbrirHeladera"))))
                .tarjetaColaborador(tarjetaColaborador)
                .formaDeColaboracion(FormaDeColaboracion.DISTRIBUCION_VIANDAS)
                .colaboracionId(distribucionDeVianda.getId())
                .build();

        accionSolicitada.agregarHeladeraSolicitada(heladeraOrigen);
        accionSolicitada.agregarHeladeraSolicitada(heladeraDestino);

        withTransaction(() -> accionesSolicitadasRepositorio.guardar(accionSolicitada));
        PublicadorSolicitudApertura.publicarAperturaSolicitada(accionSolicitada);
        
        context.status(200);
        context.result("Distribución de viandas realizada correctamente.");

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
