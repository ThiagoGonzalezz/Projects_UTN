package vianditasONG.controllers.menus;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import io.javalin.http.Context;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterContactosDTO;
import vianditasONG.converters.dtoconverters.ConverterFormaDeColaboracionDTO;
import vianditasONG.converters.dtoconverters.ConverterSuscripcionDTO;
import vianditasONG.dtos.inputs.colaboraciones.SuscripcionDTO;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.dtos.outputs.ReporteOutputDTO;
import vianditasONG.dtos.outputs.colaboraciones.FormaDeColaboracionOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.repositorios.colaboracion.IFormasDeColaboracionRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.FormasDeColaboracionRepositorio;
import vianditasONG.modelos.repositorios.contactos.IContactosRepositorio;
import vianditasONG.modelos.repositorios.contactos.imp.ContactosRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMaxRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionCantViandasMinRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.ISuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMaxRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMinRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.utils.Persistente;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;


@Builder
public class MenuHumanoController implements WithSimplePersistenceUnit {

    @Builder.Default
    IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    IFormasDeColaboracionRepositorio formasDeColaboracionRepositorio = ServiceLocator.getService(FormasDeColaboracionRepositorio.class);
    @Builder.Default
    ConverterFormaDeColaboracionDTO converterFormaDeColaboracionDTO = ServiceLocator.getService(ConverterFormaDeColaboracionDTO.class);
    @Builder.Default
    ConverterContactosDTO converterContactoDTO = ServiceLocator.getService(ConverterContactosDTO.class);
    @Builder.Default
    IContactosRepositorio contactosRepositorio = ServiceLocator.getService(ContactosRepositorio.class);
    @Builder.Default
    ConverterSuscripcionDTO converterSuscripcionDTO = ServiceLocator.getService(ConverterSuscripcionDTO.class);
    @Builder.Default
    ISuscripcionDesperfectosRepositorio suscripcionDesperfectosRepositorio = ServiceLocator.getService(SuscripcionDesperfectosRepositorio.class);
    @Builder.Default
    ISuscripcionCantViandasMaxRepositorio suscripcionCantViandasMaxRepositorio = ServiceLocator.getService(SuscripcionCantViandasMaxRepositorio.class);
    @Builder.Default
    ISuscripcionCantViandasMinRepositorio suscripcionCantViandasMinRepositorio = ServiceLocator.getService(SuscripcionCantViandasMinRepositorio.class);

    public void getMenu(Context context) {

        Long idHumano = context.sessionAttribute("humano-id");

        Optional<Humano> humanoOptional = humanosRepositorio.buscar(idHumano);

        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró un humano con el ID: " + idHumano)
        );

        List<FormaDeColaboracion> formasDeColaboracionElegidas = humano.getFormasDeColaboracion();

        List<FormaDeColaboracion> formasDeColaboracionPermitidas = List.of(
                FormaDeColaboracion.DONACION_DINERO,
                FormaDeColaboracion.DONACION_VIANDAS,
                FormaDeColaboracion.DISTRIBUCION_VIANDAS,
                FormaDeColaboracion.ENTREGA_TARJETAS
        );

        List<FormaDeColaboracionOutputDTO> formaDeColaboracionOutputDTOS = formasDeColaboracionPermitidas.stream()
                .map(formaColab -> converterFormaDeColaboracionDTO.convertToDTO(formaColab, formasDeColaboracionElegidas.contains(formaColab)))
                .toList();

        Map<String, Object> model = new HashMap<>();

        Boolean tieneDireccion = humano.getDireccion() != null;
        String tipoRol = context.sessionAttribute("tipo-rol");


        model.put("tipoUsuario", "humano");
        model.put("formas-de-colaboracion", formaDeColaboracionOutputDTOS);
        model.put("tieneDireccion", tieneDireccion);
        model.put("tipoRol", tipoRol);

        List<Contacto> contactos = contactosRepositorio.buscarPorIdHumano(idHumano);
        if (contactos.isEmpty()) {
            throw new IllegalStateException("La lista de contactos está vacía");
        }
        List<ContactoDTO> contactosDTO = contactos
                .stream()
                .map(contacto -> converterContactoDTO.convertToDTO(contacto))
                .toList();
        model.put("contactos", contactosDTO);

        List<SuscripcionCantViandasMax> suscripcionCantViandasMax = this.suscripcionCantViandasMaxRepositorio.buscarPorIdHumano(idHumano).stream().filter(Persistente::getActivo).toList();
        List<SuscripcionCantViandasMin> suscripcionCantViandasMin = this.suscripcionCantViandasMinRepositorio.buscarPorIdHumano(idHumano).stream().filter(Persistente::getActivo).toList();
        List<SuscripcionDesperfectos> suscripcionDesperfectos = this.suscripcionDesperfectosRepositorio.buscarPorIdHumano(idHumano).stream().filter(Persistente::getActivo).toList();

        List<SuscripcionDTO> s1= suscripcionCantViandasMax.stream().map(susc->converterSuscripcionDTO.convertToDTO(susc)).toList();
        List<SuscripcionDTO> s2= suscripcionCantViandasMin.stream().map(susc->converterSuscripcionDTO.convertToDTO(susc)).toList();
        List<SuscripcionDTO> s3= suscripcionDesperfectos.stream().map(susc->converterSuscripcionDTO.convertToDTO(susc)).toList();
        List<SuscripcionDTO> suscripcionesDTO = Stream.concat(Stream.concat(s1.stream(), s2.stream()), s3.stream()).toList();

        model.put("suscripciones", suscripcionesDTO);

        context.render("conUsuario/menu_humano.hbs", model);
    }

    public void save(Context context) throws IOException {
        Long humanoId= context.sessionAttribute("humano-id");

        Optional<Humano> humanoOptional=humanosRepositorio.buscar(humanoId);

        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró un humano con el ID: " + humanoId)
        );

        FormaDeColaboracion nuevaFormaDeColaboracion = FormaDeColaboracion.valueOf(context.formParam("stringEnum"));

        humano.agregarFormasDeColab(nuevaFormaDeColaboracion);

        String provincia = context.formParam("provincia");
        String localidadNombre = context.formParam("localidad");
        String calle = context.formParam("calle");
        String altura = context.formParam("altura");


        if (provincia != null && !provincia.isEmpty()) {
            Localidad localidad = Localidad.builder().nombre(localidadNombre).provincia(Provincia.valueOf(provincia)).build();
            Direccion direccion = Direccion.builder().calle(calle)
                    .altura(altura).localidad(localidad).build();
            humano.setDireccion(direccion);
        }

        withTransaction(() -> {
            humanosRepositorio.actualizar(humano);
        });

        context.redirect("/menu-persona");

    }

}
