package vianditasONG.controllers.colaboraciones;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.FormularioController;
import vianditasONG.converters.dtoconverters.ConverterDonacionViandaDTO;
import vianditasONG.converters.dtoconverters.ConverterPreguntasDTO;
import vianditasONG.dtos.inputs.colaboraciones.DonacionDeViandaInputDTO;
import vianditasONG.dtos.inputs.colaboraciones.ViandaDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.exceptions.AccessDeniedException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
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
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
public class DonacionesDeViandasController implements ICrudViewsHandler, WithSimplePersistenceUnit, FormularioController {

    @Builder.Default
    private IDonacionesDeViandasRepositorio donacionesDeViandasRepositorio = ServiceLocator.getService(DonacionesDeViandasRepositorio.class);
    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private ConverterDonacionViandaDTO converterDonacionViandaDTO = ServiceLocator.getService(ConverterDonacionViandaDTO.class);
    @Builder.Default
    IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
    @Builder.Default
    ConverterPreguntasDTO converterPreguntasDTO = ServiceLocator.getService(ConverterPreguntasDTO.class);
    @Builder.Default
    TarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = ServiceLocator.getService(TarjetasColaboradorRepositorio.class);
    @Builder.Default
    IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio = ServiceLocator.getService(AccionesSolicitadasRepositorio.class);
    @Builder.Default
    IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);

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

        Formulario formDonacionViandas = this.repoFormularios.buscarPorNombre(Config.getInstancia().obtenerDelConfig("nombreFormDonacionDeViandas")).orElseThrow(ServerErrorException::new);
        List<PreguntaDTO> preguntasDTOs = formDonacionViandas.getPreguntas().stream().filter(p -> !p.getEsEstatica())
                .map(converterPreguntasDTO::convertToPreguntaDTO)
                .toList();
        Map<String, Object> model = new HashMap<>();
        model.put("preguntas", preguntasDTOs);

        if (formasDeColaboracion.contains(FormaDeColaboracion.DONACION_VIANDAS)) {
            context.render("conUsuario/donacion_vianda.hbs", model);
        } else {
            throw new AccessDeniedException();
        }

    }


    @Override
    public void save(Context context) throws IOException {
        List<ViandaDTO> viandasDTO = new ArrayList<>();

        Set<String> keys = context.formParamMap().keySet().stream()
                .filter(param -> param.startsWith("viandas["))
                .collect(Collectors.toSet());

        int cantViandas = keys.stream()
                .map(param -> param.substring(8, param.indexOf("]")))
                .mapToInt(Integer::parseInt)
                .max().orElse(0) + 1;

        for (int i = 0; i < cantViandas; i++) {
            String comida = context.formParam("viandas[" + i + "].comida");
            Integer calorias = Integer.parseInt(context.formParam("viandas[" + i + "].calorias"));
            String fechaCaducidad = context.formParam("viandas[" + i + "].fechaCaducidad");
            Double peso = Double.parseDouble(Objects.requireNonNull(context.formParam("viandas[" + i + "].peso")));

            ViandaDTO vianda = ViandaDTO.builder()
                    .comida(comida)
                    .calorias(calorias)
                    .fechaDeCaducidad(fechaCaducidad)
                    .pesoEnGramos(peso)
                    .build();

            viandasDTO.add(vianda);
        }

        Integer idHeladera = Integer.valueOf(Objects.requireNonNull(context.formParam("heladera-destino-id")));

        DonacionDeViandaInputDTO donacionDeViandaInputDTO = DonacionDeViandaInputDTO.builder()
                .idHumano((Objects.requireNonNull(context.sessionAttribute("humano-id"))))
                .viandas(viandasDTO)
                .idHeladeraSeleccionada(idHeladera)
                .build();

        DonacionDeVianda donacionDeVianda = converterDonacionViandaDTO.convertToDonacionDeVianda(donacionDeViandaInputDTO);
        donacionDeVianda.setActivo(Boolean.FALSE);//Porque no es valido hasta que se efectue realmente por MQTT

        Humano humano = donacionDeVianda.getColaborador();

        Optional<TarjetaColaborador> tarjetaOptional = tarjetasColaboradorRepositorio.buscarPorHumano(humano);
        TarjetaColaborador tarjetaColaborador = tarjetaOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una tarjeta asociada") //TODO: Ver porque el humano no debería llegar aca en caso de no tener tarjeta
        );

        Optional<Heladera> heladeraOptional = this.heladerasRepositorio.buscar(Long.valueOf(idHeladera));
        Heladera heladera = heladeraOptional.orElseThrow(() -> new RuntimeException("No se encontró la heladera"));
        heladera.agregarViandas(donacionDeVianda.cantViandas());

        withTransaction(() -> {
            donacionesDeViandasRepositorio.guardar(donacionDeVianda);
            humanosRepositorio.actualizar(humano);
            heladerasRepositorio.actualizar(heladera);
        });

        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .tipoDeAccion(TipoDeAccion.APERTURA_PARA_INGRESAR_DONACION)
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now().plusHours(Long.parseLong(Config.getInstancia().obtenerDelConfig("horasParaAbrirHeladera"))))
                .tarjetaColaborador(tarjetaColaborador)
                .formaDeColaboracion(FormaDeColaboracion.DONACION_VIANDAS)
                .colaboracionId(donacionDeVianda.getId())
                .build();
        accionSolicitada.agregarHeladeraSolicitada(donacionDeVianda.getViandas().stream().findFirst().get().getHeladeraOrigen());

        withTransaction(() -> {accionesSolicitadasRepositorio.guardar(accionSolicitada);});

        PublicadorSolicitudApertura.publicarAperturaSolicitada(accionSolicitada);

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
