package vianditasONG.controllers.heladeras;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterHeladeraDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class HeladerasAMostrarController {
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private IHeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private ConverterHeladeraDTO converter = ServiceLocator.getService(ConverterHeladeraDTO.class);
    @Builder.Default
    HacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);

    public void index(Context context) {

        List<Heladera> heladeras = repoHeladeras.buscarTodos();

        List<HeladeraOutputDTO> heladerasDTO = heladeras.stream()
                .map(heladera -> HeladeraOutputDTO.builder()
                        .id(heladera.getId())
                        .nombreHeladera(heladera.getNombre())
                        .modeloId(heladera.getModeloHeladera().getId().toString())
                        .nombrePuntoEstrategico(heladera.getPuntoEstrategico().getNombre())
                        .cantidadViandas(heladera.getCantidadViandas())
                        .fechaDeRegistro(heladera.getFechaDeRegistro().toString())
                        .estadoHeladera(heladera.getEstadoHeladera().name())
                        .diasDesactivada(heladera.getDiasDesactivada())
                        .encargadoHeladera(encontrarEncargado(heladera))
                        .fechaUltimaDesactivacion(heladera.getFechaUltimaDesactivacion() != null
                                ? heladera.getFechaUltimaDesactivacion().toString()
                                : "Nunca fue desactivada")
                        .temperatura(heladera.getUltimaTemperaturaRegistradaEnGradosCelsius())
                        .fechaTempertauraRegistrada(String.valueOf(heladera.getFechaUltimatemperaturaRegistrada()))
                        .build())
                .collect(Collectors.toList());

        String respuestaJSON = gson.toJson(heladerasDTO);

        context.json(respuestaJSON);

    }

    public String encontrarEncargado(Heladera heladera) {

        HacerseCargoDeHeladera colaboracion = hacerseCargoDeHeladerasRepositorio.buscarPorIdHeladera(heladera.getId())
                .orElseThrow(() -> new RuntimeException("Colaboracion no encontrada"));

        return colaboracion.getColaborador().getRazonSocial();
    }

}
