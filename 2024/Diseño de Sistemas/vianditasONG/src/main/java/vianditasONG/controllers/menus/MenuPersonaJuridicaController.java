package vianditasONG.controllers.menus;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterFormaDeColaboracionDTO;
import vianditasONG.dtos.outputs.ReporteOutputDTO;
import vianditasONG.dtos.outputs.colaboraciones.FormaDeColaboracionOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.repositorios.colaboracion.IFormasDeColaboracionRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.FormasDeColaboracionRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;

import java.time.LocalDate;
import java.util.*;

@Builder
public class MenuPersonaJuridicaController implements WithSimplePersistenceUnit{

    @Builder.Default
    IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    IFormasDeColaboracionRepositorio formasDeColaboracionRepositorio = ServiceLocator.getService(FormasDeColaboracionRepositorio.class);
    @Builder.Default
    ConverterFormaDeColaboracionDTO converterFormaDeColaboracionDTO = ServiceLocator.getService(ConverterFormaDeColaboracionDTO.class);


    public void getMenu(Context context){

        Long idPersonaJuridica= context.sessionAttribute("persona-juridica-id");

        Optional<PersonaJuridica> personaJuridicaOptional=personasJuridicasRepositorio.buscarPorId(idPersonaJuridica);

        PersonaJuridica personaJuridica = personaJuridicaOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una persona jurídica con el ID: " + idPersonaJuridica)
        );

        List<FormaDeColaboracion> formasDeColaboracionElegidas= personaJuridica.getFormasDeColaboracion();

        List<FormaDeColaboracion> formasDeColaboracionPermitidas = List.of(
                FormaDeColaboracion.DONACION_DINERO,
                FormaDeColaboracion.OFERTA_PRODUCTOS_Y_SERVICIOS,
                FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS
        );

        List<FormaDeColaboracionOutputDTO> formaDeColaboracionOutputDTOS = formasDeColaboracionPermitidas.stream()
                .map(formaColab -> converterFormaDeColaboracionDTO.convertToDTO(formaColab, formasDeColaboracionElegidas.contains(formaColab)))
                .toList();

        Map<String, Object> model = new HashMap<>();

        Boolean tieneDireccion = personaJuridica.getDireccion()!=null;

        String tipoRol = context.sessionAttribute("tipo-rol");

        model.put("tipoRol", tipoRol);
        model.put("formas-de-colaboracion", formaDeColaboracionOutputDTOS);
        model.put("tieneDireccion", tieneDireccion);

        context.render("conUsuario/menu_personaJuridica.hbs", model);

    }

    public void save(Context context) {
        Long idPersonaJuridica= context.sessionAttribute("persona-juridica-id");

        Optional<PersonaJuridica> personaJuridicaOptional=personasJuridicasRepositorio.buscarPorId(idPersonaJuridica);

        PersonaJuridica personaJuridica = personaJuridicaOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una persona jurídica con el ID: " + idPersonaJuridica)
        );

        FormaDeColaboracion nuevaFormaDeColaboracion = FormaDeColaboracion.valueOf(context.formParam("stringEnum"));

        personaJuridica.agregarFormasDeColab(nuevaFormaDeColaboracion);

        String provincia = context.formParam("provincia");
        String localidadNombre = context.formParam("localidad");
        String calle = context.formParam("calle");
        String altura = context.formParam("altura");


        if (provincia != null && !provincia.isEmpty()) {
            Localidad localidad = Localidad.builder().nombre(localidadNombre).provincia(Provincia.valueOf(provincia)).build();
            Direccion direccion = Direccion.builder().calle(calle)
                    .altura(altura).localidad(localidad).build();
            personaJuridica.setDireccion(direccion);
        }

        withTransaction(() -> {
            personasJuridicasRepositorio.actualizar(personaJuridica);
        });

        context.redirect("/menu-personaJuridica");

    }
}
