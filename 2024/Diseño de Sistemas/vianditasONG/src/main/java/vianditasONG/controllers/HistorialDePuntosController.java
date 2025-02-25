package vianditasONG.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterItemHistorialDTO;
import vianditasONG.dtos.outputs.ItemHistorialOutputDTO;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.canjes.ICanjesRepositorio;
import vianditasONG.modelos.repositorios.canjes.imp.CanjesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IHacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IRegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.*;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.ofertas.IOfertasRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;

import java.util.*;
import java.util.stream.Collectors;

@Builder
public class HistorialDePuntosController {

    @Builder.Default
    private IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator
            .getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private ICanjesRepositorio canjesRepositorio = ServiceLocator.getService(CanjesRepositorio.class);
    @Builder.Default
    private IOfertasRepositorio ofertasRepositorio = ServiceLocator.getService(OfertasRepositorio.class);
    @Builder.Default
    private IDonacionesDeViandasRepositorio donacionesDeViandasRepositorio = ServiceLocator
            .getService(DonacionesDeViandasRepositorio.class);
    @Builder.Default
    private IDonacionesDeDineroRepositorio donacionesDeDineroRepositorio = ServiceLocator
            .getService(DonacionesDeDineroRepositorio.class);
    @Builder.Default
    private IRegistrosPersonasVulnerablesRepositorio registrosPersonasVulnerablesRepositorio = ServiceLocator
            .getService(RegistrosPersonasVulnerablesRepositorio.class);
    @Builder.Default
    private DistribucionesDeViandasRepositorio distribucionesDeViandasRepositorio = ServiceLocator
            .getService(DistribucionesDeViandasRepositorio.class);
    @Builder.Default
    private ConverterItemHistorialDTO converterItemHistorialDTO = ServiceLocator
            .getService(ConverterItemHistorialDTO.class);
    @Builder.Default
    private IHacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = ServiceLocator
            .getService(HacerseCargoDeHeladerasRepositorio.class);

    public void getHistorial(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<ItemHistorialOutputDTO> itemHistorialOutputDTOS = new ArrayList<>();

        if (context.sessionAttribute("tipo-rol") == "PERSONA_JURIDICA") {
            model.put("tipoRol", "PERSONA_JURIDICA");
            Optional<PersonaJuridica> personaJuridica = personasJuridicasRepositorio
                    .buscarPorId(context.sessionAttribute("persona-juridica-id"));

            List<DonacionDeDinero> donacionesDeDinero = donacionesDeDineroRepositorio
                    .buscarTodosPorHumanoId(personaJuridica.get().getId());
            List<HacerseCargoDeHeladera> hacerseCargoDeHeladeras = hacerseCargoDeHeladerasRepositorio
                    .buscarTodosPorPersonaJuridicaId(personaJuridica.get().getId());
            List<Canje> canjes = canjesRepositorio.buscarTodosPorHumanoId(personaJuridica.get().getId());
            List<Oferta> ofertas = ofertasRepositorio.buscarTodosPorPersonaJuridicaId(personaJuridica.get().getId());

            itemHistorialOutputDTOS.addAll(
                    donacionesDeDinero.stream().map(converterItemHistorialDTO::convertDonacionDeDineroToDTO).toList());
            itemHistorialOutputDTOS.addAll(canjes.stream().map(converterItemHistorialDTO::convertCanjeToDTO).toList());
            itemHistorialOutputDTOS.addAll(ofertas.stream().map(converterItemHistorialDTO::convertOfertaToDTO).toList());
            itemHistorialOutputDTOS.addAll(hacerseCargoDeHeladeras.stream().map(converterItemHistorialDTO::convertHacerseCargoToDTO).toList());

            model.put("puntosDisponibles", personaJuridica.get().getPuntos().intValue());
        } else if (context.sessionAttribute("tipo-rol") == "PERSONA_HUMANA") {
            model.put("tipoRol", "PERSONA_HUMANA");
            Optional<Humano> humano = humanosRepositorio.buscar(context.sessionAttribute("humano-id"));

            List<DonacionDeVianda> donacionesDeViandas = donacionesDeViandasRepositorio
                    .buscarTodosPorHumanoId(humano.get().getId());
            List<DistribucionDeVianda> distribucionDeViandas = distribucionesDeViandasRepositorio
                    .buscarTodosPorHumanoId(humano.get().getId());
            List<RegistroPersonasVulnerables> registroPersonasVulnerables = registrosPersonasVulnerablesRepositorio
                    .buscarTodosPorHumanoId(humano.get().getId());
            List<DonacionDeDinero> donacionesDeDinero = donacionesDeDineroRepositorio
                    .buscarTodosPorHumanoId(humano.get().getId());
            List<Canje> canjes = canjesRepositorio.buscarTodosPorHumanoId(humano.get().getId());

            itemHistorialOutputDTOS.addAll(
                    donacionesDeViandas.stream().map(converterItemHistorialDTO::convertDonacionDeViandaToDTO).toList());
            itemHistorialOutputDTOS.addAll(distribucionDeViandas.stream()
                    .map(converterItemHistorialDTO::convertDistribucionDeViandaToDTO).toList());
            itemHistorialOutputDTOS.addAll(registroPersonasVulnerables.stream()
                    .map(converterItemHistorialDTO::convertRegistroPersonasVulnerablesToDTO).toList());
            itemHistorialOutputDTOS.addAll(
                    donacionesDeDinero.stream().map(converterItemHistorialDTO::convertDonacionDeDineroToDTO).toList());
            itemHistorialOutputDTOS.addAll(canjes.stream().map(converterItemHistorialDTO::convertCanjeToDTO).toList());

            model.put("puntosDisponibles", humano.get().getPuntos().intValue());
        }

        itemHistorialOutputDTOS = itemHistorialOutputDTOS.stream()
                .sorted(Comparator.comparing(ItemHistorialOutputDTO::getFechaYHora).reversed())
                .collect(Collectors.toList());


        Map<String, Integer> puntosPorDia = new TreeMap<>();
        for (ItemHistorialOutputDTO item : itemHistorialOutputDTOS) {
            String fecha = item.getFechaYHora().split(" ")[0];
            int puntos = item.getPuntos();
            if ("Canje".equals(item.getTipo())) {
                puntos = -puntos;
            }
            if (!"Oferta Publicada".equals(item.getTipo())) { // Excluir ofertas
                puntosPorDia.put(fecha, puntosPorDia.getOrDefault(fecha, 0) + puntos);
            }
        }

        model.put("historial", itemHistorialOutputDTOS);
        model.put("puntosPorDia", new Gson().toJson(puntosPorDia));
        context.render("conUsuario/historial_puntos.hbs", model);
    }
}