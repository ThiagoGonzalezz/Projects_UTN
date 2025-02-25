package vianditasONG.controllers.colaboraciones;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.outputs.IncidenteDTO;
import vianditasONG.dtos.outputs.colaboraciones.HeladeraACargoDTO;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IncidentesRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class HeladerasACargoController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    HacerseCargoDeHeladerasRepositorio repoHacerseCargo = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);

    @Builder.Default
    PersonasJuridicasRepositorio repoPersonaJuridica= ServiceLocator.getService(PersonasJuridicasRepositorio.class);

    @Override
    public void index(Context context) {
        Long usuarioId = context.sessionAttribute("usuario-id");

        PersonaJuridica colaborador = repoPersonaJuridica.buscarPorUsuario(usuarioId)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));

        List<HacerseCargoDeHeladera>  heladeras = repoHacerseCargo.buscarTodosPorPersonaJuridicaId(colaborador.getId());

        // Convertir cada HacerseCargoDeHeladera a HeladeraACargoDTO
        List<HeladeraACargoDTO> heladerasDTO = heladeras.stream()
                .map(h -> new HeladeraACargoDTO(
                        h.getHeladera().nombre(),
                        h.getHeladera().getId(),
                        h.getHeladera().direccionCompleta(),
                        h.getHeladera().getEstadoHeladera().name(),
                        h.getFecha().toString()
                ))
                .collect(Collectors.toList());

        // Renderizar la vista con los DTOs
        context.render("conUsuario/heladeras_a_cargo.hbs", Map.of("heladeras", heladerasDTO));

    }

    public void obtenerIncidentesDeHeladera(Context context) {
        Long id = context.pathParamAsClass("id", Long.class).get();

        // Obtener los incidentes asociados a la heladera
        List<Incidente> incidentes = ServiceLocator.getService(IncidentesRepositorio.class)
                .buscarPorHeladeraId(id);

        if (incidentes.isEmpty()) {
            // Devolver un mensaje si no hay incidentes
            context.json(Map.of("mensaje", "Esta heladera nunca tuvo incidentes"));
        } else {
            // Convertir los incidentes a DTOs
            List<IncidenteDTO> incidentesDTO = incidentes.stream()
                    .map(incidente -> new IncidenteDTO(
                            incidente.getDescripcion(),
                            incidente.getFotoResolucion(),
                            incidente.getFechaResolucion() != null ? incidente.getFechaResolucion().toString() : "No resuelto",
                            incidente.getTecnicoResolutor() != null ? incidente.getTecnicoResolutor().getCuil() : "No asignado",
                            incidente.getFechaYHora().toString()
                    ))
                    .collect(Collectors.toList());

            // Devolver la lista de DTOs en formato JSON
            context.json(incidentesDTO);
        }
    }




    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {

    }

    @Override
    public void edit(Context context) throws IOException {

    }

    @Override
    public void update(Context context) throws IOException {

    }

    @Override
    public void delete(Context context) {

    }
}
