package vianditasONG.controllers.canjes;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.OfertaInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.exceptions.PuntosInsuficientesException;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.Puntuable;
import vianditasONG.modelos.repositorios.canjes.ICanjesRepositorio;
import vianditasONG.modelos.repositorios.canjes.imp.CanjesRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.ofertas.IOfertasRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificacionAceptado;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificacionCanjeado;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Builder
public class CanjesController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Builder.Default
    private IOfertasRepositorio ofertasRepositorio = OfertasRepositorio.builder().build();
    @Builder.Default
    private ICanjesRepositorio canjesRepositorio = CanjesRepositorio.builder().build();
    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = HumanosRepositorio.builder().build();
    @Builder.Default
    private IPersonasJuridicasRepositorio personasJuridicasRepositorio = PersonasJuridicasRepositorio.builder().build();

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {

        Long ofertaId = Long.valueOf(context.pathParam("id"));
        Optional<Oferta> oferta = ofertasRepositorio.buscarPorId(ofertaId);
        Puntuable canjeador = null;

        if(context.sessionAttribute("tipo-rol") == "PERSONA_JURIDICA") {
            Optional<PersonaJuridica> personaJuridica = personasJuridicasRepositorio.buscarPorId(context.sessionAttribute("persona-juridica-id"));
            canjeador =     personaJuridica.get();
        }
        else if(context.sessionAttribute("tipo-rol") == "PERSONA_HUMANA"){
            Optional<Humano> humano = humanosRepositorio.buscar(context.sessionAttribute("humano-id"));
            canjeador =     humano.get();
        }

        Oferta ofertaACanjear = oferta.get();

        if(canjeador == null)
            throw new RuntimeException("No se encontró al canjeador");

        if(!ofertaACanjear.puedeSerCanjeadaPor(canjeador))
            throw new PuntosInsuficientesException();

        Canje nuevoCanje = Canje.de(ofertaACanjear, canjeador);
        canjeador.restarPuntos(ofertaACanjear.getPuntosNecesarios());

        ofertaACanjear.setFueCanjeada(Boolean.TRUE);

        withTransaction(() -> {
            ofertasRepositorio.actualizar(ofertaACanjear);
            ofertasRepositorio.eliminarLogico(ofertaACanjear);
            canjesRepositorio.guardar(nuevoCanje);
        });

        NotificacionCanjeado notificacionCanjeado = NotificacionCanjeado.of(canjeador, ofertaACanjear);
        Contacto contactoOfertante = ofertaACanjear.getOfertante().getContactos().get(0);
        contactoOfertante.getTipoDeContacto().getMedioDeAviso().notificar(contactoOfertante, notificacionCanjeado);

        context.sessionAttribute("showSuccessPopup", true);
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
