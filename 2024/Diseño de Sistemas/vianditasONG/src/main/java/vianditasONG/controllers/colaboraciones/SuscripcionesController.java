package vianditasONG.controllers.colaboraciones;

import com.google.gson.Gson;
import com.sun.xml.bind.v2.model.core.ID;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import org.json.JSONObject;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterSuscripcionDTO;
import vianditasONG.dtos.inputs.colaboraciones.SuscripcionDTO;
import vianditasONG.exceptions.ImposibilidadSuscripcionException;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.suscripciones.*;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMaxRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMinRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMaxRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMinRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeWhatsapp;

import java.io.IOException;
import java.util.*;

@Builder
public class SuscripcionesController implements WithSimplePersistenceUnit {
    @Builder.Default
    ConverterSuscripcionDTO converterSuscripcionDTO = ServiceLocator.getService(ConverterSuscripcionDTO.class);
    @Builder.Default
    ISuscripcionDesperfectosRepositorio suscripcionDesperfectosRepositorio = ServiceLocator.getService(SuscripcionDesperfectosRepositorio.class);
    @Builder.Default
    ISuscripcionCantViandasMaxRepositorio suscripcionCantViandasMaxRepositorio = ServiceLocator.getService(SuscripcionCantViandasMaxRepositorio.class);
    @Builder.Default
    ISuscripcionCantViandasMinRepositorio suscripcionCantViandasMinRepositorio = ServiceLocator.getService(SuscripcionCantViandasMinRepositorio.class);
    @Builder.Default
    IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);


    public void save(Context context) throws IOException {

        List<String> suscripcionesElegidas = context.formParams("notificaciones[]");

        Long idHumano = context.sessionAttribute("humano-id");
        Optional<Humano> humanoOptional = humanosRepositorio.buscar(idHumano);
        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró un humano con el ID: " + idHumano)
        );

        Long idHeladera = Long.valueOf(Objects.requireNonNull(context.formParam("heladeraId")));
        Optional<Heladera> heladeraOptional = heladerasRepositorio.buscar(idHeladera);
        Heladera heladera = heladeraOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una heladera con el ID: " + idHeladera)
        );

        if(/*humano.puedeSuscribirse(heladera)*/true){

            for (String suscripcion : suscripcionesElegidas) {
                SuscripcionDTO suscripcionDTO = null;
                String contactoSeleccionado = context.formParam("contacto");
                assert contactoSeleccionado != null;
                String[] partes = contactoSeleccionado.split("_");
                String tipoContacto = partes[0];
                String detalleContacto = partes[1];

                Contacto contacto = Contacto.builder().contacto(detalleContacto).tipoDeContacto(TipoDeContacto.valueOf(tipoContacto)).build();
                MedioDeAviso medioDeAviso = this.getMedioNotificacion(contacto);

                if ("Viandas Disponibles".equals(suscripcion)) {
                    String cantViandasMin = context.formParam("nViandasDisponibles");

                    if (cantViandasMin != null && !cantViandasMin.isEmpty()) {
                        SuscripcionCantViandasMin suscripcionCantViandasMin = SuscripcionCantViandasMin.builder()
                                .colaborador(humano)
                                .contacto(contacto)
                                .medioDeAviso(medioDeAviso)
                                .cantViandasMin(Integer.parseInt(cantViandasMin))
                                .heladera(heladera)
                                .build();

                        withTransaction(() -> {
                            suscripcionCantViandasMinRepositorio.guardar(suscripcionCantViandasMin);
                            heladera.agregarSuscripcionCantViandasMin(suscripcionCantViandasMin);
                        });
                        NotificacionSuscripcionExitosa notificacion = NotificacionSuscripcionExitosa.of(heladera, TipoSuscripcion.CANTVIANDASMIN, cantViandasMin);
                        medioDeAviso.notificar(contacto,notificacion);

                    }
                } else if ("Viandas Faltantes".equals(suscripcion)) {
                    String cantViandasMax = context.formParam("nViandasFaltantes");
                    if (cantViandasMax != null && !cantViandasMax.isEmpty()) {

                        SuscripcionCantViandasMax suscripcionCantViandasMax = SuscripcionCantViandasMax.builder()
                                .colaborador(humano)
                                .contacto(contacto)
                                .cantViandasMax(Integer.parseInt(cantViandasMax))
                                .medioDeAviso(medioDeAviso)
                                .heladera(heladera)
                                .build();
                        withTransaction(() -> {
                            suscripcionCantViandasMaxRepositorio.guardar(suscripcionCantViandasMax);
                            heladera.agregarSuscripcionCantViandasMax(suscripcionCantViandasMax);
                        });
                        NotificacionSuscripcionExitosa notificacion = NotificacionSuscripcionExitosa.of(heladera, TipoSuscripcion.CANTVIANDASMAX, cantViandasMax);
                        medioDeAviso.notificar(contacto,notificacion);
                    }
                } else if ("Desperfectos".equals(suscripcion)) {
                    SuscripcionDesperfectos suscripcionDesperfectos = SuscripcionDesperfectos.builder()
                            .colaborador(humano)
                            .contacto(contacto)
                            .medioDeAviso(medioDeAviso)
                            .heladera(heladera)
                            .build();
                    withTransaction(() -> {
                        suscripcionDesperfectosRepositorio.guardar(suscripcionDesperfectos);
                        heladera.agregarSuscripcionDesperfectos(suscripcionDesperfectos);
                    });
                    NotificacionSuscripcionExitosa notificacion = NotificacionSuscripcionExitosa.of(heladera, TipoSuscripcion.DESPERFECTOS, "");
                    medioDeAviso.notificar(contacto,notificacion);
                }
                context.status(200);
            }
    }
    else{
        throw new ImposibilidadSuscripcionException();
    }}

    public void anularSuscripcion(Context context) throws IOException {
        try {
            String body = context.body();
            JSONObject json = new JSONObject(body);
            Long id = json.getLong("id");
            String tipo = json.getString("tipo");

            switch (tipo){
                case "DESPERFECTOS" -> {
                   Optional<SuscripcionDesperfectos> suscripcionDesperfectosOptional = this.suscripcionDesperfectosRepositorio.buscarPorId(id);
                   SuscripcionDesperfectos suscripcionDesperfectos = suscripcionDesperfectosOptional.orElseThrow(() ->
                           new NoSuchElementException("No se encontró una suscripción con el ID: " + id));

                   Heladera heladera =suscripcionDesperfectos.getHeladera();
                   heladera.anularSuscripcionDesperfectos(id);

                    withTransaction(() -> this.suscripcionDesperfectosRepositorio.eliminarLogico(suscripcionDesperfectos));
                }
                case "VIANDAS MÁXIMAS" -> {
                    Optional<SuscripcionCantViandasMax> suscripcionCantViandasMaxOptional= this.suscripcionCantViandasMaxRepositorio.buscarPorId(id);
                    SuscripcionCantViandasMax suscripcionCantViandasMax =  suscripcionCantViandasMaxOptional.orElseThrow(() ->
                            new NoSuchElementException("No se encontró una suscripción con el ID: " + id));

                    Heladera heladera =suscripcionCantViandasMax.getHeladera();
                    heladera.anularSuscripcionCantViandasMax(id);

                    withTransaction(() ->this.suscripcionCantViandasMaxRepositorio.eliminarLogico(suscripcionCantViandasMax));
                }
                case "VIANDAS MÍNIMAS" -> {
                    Optional<SuscripcionCantViandasMin> suscripcionCantViandasMinOptional= this.suscripcionCantViandasMinRepositorio.buscarPorId(id);
                    SuscripcionCantViandasMin suscripcionCantViandasMin =  suscripcionCantViandasMinOptional.orElseThrow(() ->
                            new NoSuchElementException("No se encontró una suscripción con el ID: " + id));

                    Heladera heladera =suscripcionCantViandasMin.getHeladera();
                    heladera.anularSuscripcionCantViandasMin(id);

                    withTransaction(() ->this.suscripcionCantViandasMinRepositorio.eliminarLogico(suscripcionCantViandasMin));
                }
            }

            context.status(200);
            context.json("Suscripción anulada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500);
            context.json("Error al procesar la solicitud.");
        }
    }

    private MedioDeAviso getMedioNotificacion(Contacto contacto) {
        TipoDeContacto tipoDeContacto = contacto.getTipoDeContacto();

        return switch (tipoDeContacto) {
            case CORREO -> ServiceLocator.getService(NotificadorDeEmail.class);
            case TELEGRAM -> ServiceLocator.getService(NotificadorDeTelegram.class);
            case WHATSAPP -> ServiceLocator.getService(NotificadorDeWhatsapp.class);
            default -> null;
        };
    }
}
