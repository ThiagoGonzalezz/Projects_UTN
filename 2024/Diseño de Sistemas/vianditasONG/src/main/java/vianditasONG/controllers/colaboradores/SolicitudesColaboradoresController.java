package vianditasONG.controllers.colaboradores;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterSolicitudHumanoDTO;
import vianditasONG.converters.dtoconverters.ConverterSolicitudPersonaJuridicaDTO;
import vianditasONG.dtos.outputs.colaboradores.humanos.SolicitudHumanoDTO;
import vianditasONG.dtos.outputs.colaboradores.personasJuridicas.SolicitudPersonaJuridicaDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasColaboradorRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificacionAceptado;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificacionRechazo;
import vianditasONG.modelos.servicios.seguridad.distribuidorDeTarjetas.DistribudorDeTarjetas;

import javax.management.Notification;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
public class SolicitudesColaboradoresController implements WithSimplePersistenceUnit {
    @Builder.Default
    IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    ConverterSolicitudHumanoDTO converterSolicitudesHumanosDTOs = ServiceLocator.getService(ConverterSolicitudHumanoDTO.class);
    @Builder.Default
    ConverterSolicitudPersonaJuridicaDTO converterSolicitudesPersonasJuridicasDTOs = ServiceLocator.getService(ConverterSolicitudPersonaJuridicaDTO.class);
    @Builder.Default
    DistribudorDeTarjetas distribuidorTarjetas = ServiceLocator.getService(DistribudorDeTarjetas.class);
    @Builder.Default
    ITarjetasColaboradorRepositorio repoTarjetasColab = ServiceLocator.getService(TarjetasColaboradorRepositorio.class);

    public void index(Context context) {

        Map<String, Object> model = new HashMap<>();

        List<SolicitudHumanoDTO> solicitudesHumanosDTOs = this.humanosRepositorio
                .buscarTodos()
                .stream()
                .filter(humano -> humano.getEstadoDeSolicitud() == EstadoDeSolicitud.PENDIENTE)
                .toList()
                .stream()
                .map(h -> this.converterSolicitudesHumanosDTOs.ConvertToDTO(h))
                .toList();

        List<SolicitudPersonaJuridicaDTO> solicitudesPersonasJuridicasDTOs = this.personasJuridicasRepositorio
                .buscarTodos()
                .stream()
                .filter(pj -> pj.getEstadoDeSolicitud() == EstadoDeSolicitud.PENDIENTE)
                .toList()
                .stream()
                .map(pj -> this.converterSolicitudesPersonasJuridicasDTOs.ConvertToDTO(pj))
                .toList();

        model.put("solicitudes-humanos", solicitudesHumanosDTOs);

        model.put("solicitudes-personas-juridicas", solicitudesPersonasJuridicasDTOs);

        context.render("admin/habilitar_colaboradores.hbs", model);
    }

    public void aceptarSolicitudHumana(Context context) throws IOException {
        Humano humano = this.humanosRepositorio.buscar(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        humano.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADO);

        TarjetaColaborador tarjeta = this.distribuidorTarjetas.asignarTarjetaColaboradorNueva(humano);

        withTransaction(() -> {
            this.humanosRepositorio.actualizar(humano);
            this.repoTarjetasColab.guardar(tarjeta);
        });

        Notificacion notificacionAceptado = NotificacionAceptado.of(humano, tarjeta.getCodigo());

        Contacto contactoPersoJuridica = humano.getContactos().get(0);

        contactoPersoJuridica.getTipoDeContacto().getMedioDeAviso().notificar(contactoPersoJuridica, notificacionAceptado);

        context.status(200);
    }

    public void aceptarSolicitudPj(Context context) throws IOException {
        PersonaJuridica personaJuridica = this.personasJuridicasRepositorio.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        personaJuridica.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADO);

        withTransaction(() -> {
            this.personasJuridicasRepositorio.actualizar(personaJuridica);
        });

        Notificacion notificacionAceptado = NotificacionAceptado.of(personaJuridica);

        Contacto contactoPersoJuridica = personaJuridica.getContactos().get(0);

        contactoPersoJuridica.getTipoDeContacto().getMedioDeAviso().notificar(contactoPersoJuridica, notificacionAceptado);

        context.status(200);
    }

    public void rechazarSolicitudHumano(Context context) throws IOException {
        Humano humano = this.humanosRepositorio.buscar(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        humano.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADO);

        withTransaction(() -> {
            this.humanosRepositorio.actualizar(humano);
        });

        String motivo = context.formParam("motivo");

        Notificacion notificacionRechazo = NotificacionRechazo.of(humano, motivo);

        Contacto contactoHumano = humano.getContactos().get(0);

        contactoHumano.getTipoDeContacto().getMedioDeAviso().notificar(contactoHumano, notificacionRechazo);

        context.status(200);
    }

    public void rechazarSolicitudPj(Context context) throws IOException {
        PersonaJuridica personaJuridica = this.personasJuridicasRepositorio.buscarPorId(Long.valueOf(context.pathParam("id"))).orElseThrow(BadRequestException::new);

        personaJuridica.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADO);

        withTransaction(() -> {
            this.personasJuridicasRepositorio.actualizar(personaJuridica);
        });

        String motivo = context.formParam("motivo");

        Notificacion notificacionRechazo = NotificacionRechazo.of(personaJuridica, motivo);

        Contacto contactoPersoJuridica = personaJuridica.getContactos().get(0);

        contactoPersoJuridica.getTipoDeContacto().getMedioDeAviso().notificar(contactoPersoJuridica, notificacionRechazo);

        context.status(200);
    }
}
