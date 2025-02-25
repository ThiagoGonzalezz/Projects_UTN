package vianditasONG.controllers.colaboradores;

import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterColaboradorDTO;
import vianditasONG.dtos.outputs.ColaboradorOutputDTO;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradores;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradoresAdapterAPI;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.IRecomendadorDeColaboradoresServicio;

import java.util.*;

@Builder
public class RecomendadorColaboradoresController {
    @Builder.Default
    IHumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    ConverterColaboradorDTO converterColaboradorDTO = ServiceLocator.getService(ConverterColaboradorDTO.class);
    @Builder.Default
    RecomendadorDeColaboradores recomendadorDeColaboradores = ServiceLocator.getService(RecomendadorDeColaboradores.class);

    public void index(Context context) {

        List<Humano> humanos = this.humanosRepositorio.buscarTodos();

        if (humanos == null || humanos.isEmpty()) {
            throw new IllegalStateException("La lista de humanos está vacía o es nula");
        }

        humanos = humanos.stream().filter(humano -> humano.getEstadoDeSolicitud() == EstadoDeSolicitud.ACEPTADO).toList();

        List<ColaboradorOutputDTO> colaboradoresHumanosDTO = humanos
                .stream()
                .map(h -> this.converterColaboradorDTO.ConvertToDTO(h))
                .toList();


        List<ColaboradorOutputDTO> colaboradoresDTO = new ArrayList<>();
        colaboradoresDTO.addAll(colaboradoresHumanosDTO);

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores", colaboradoresDTO);
        context.render("admin/recomendacion_colaboradores.hbs", model);
    }

    public void save(Context context) {
        try {
            String body = context.body();
            body = body.replace("[", "").replace("]", "").replace("\"", "");
            String[] ids = body.split(",");

            List<Humano> colaboradores = new ArrayList<>();

            for (String id : ids) {
                Long idHumano = Long.valueOf(id);
                Optional<Humano> humanoOptional = humanosRepositorio.buscar(idHumano);
                Humano humano = humanoOptional.orElseThrow(() ->
                        new NoSuchElementException("No se encontró un humano con el ID: " + idHumano)
                );
                colaboradores.add(humano);
            }

            this.recomendadorDeColaboradores.recomendarColaboradores(colaboradores);

            context.status(200).json(Map.of("message", "Recomendación realizada con éxito"));

        } catch (NoSuchElementException e) {
            context.status(404).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            context.status(500).json(Map.of("error", "Error interno del servidor: " + e.getMessage()));
        }
    }


}
