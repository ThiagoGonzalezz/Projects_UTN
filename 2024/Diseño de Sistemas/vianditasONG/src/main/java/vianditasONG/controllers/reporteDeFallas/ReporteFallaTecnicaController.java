package vianditasONG.controllers.reporteDeFallas;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterFallaDTO;
import vianditasONG.converters.dtoconverters.ConverterHeladerasLoginDto;
import vianditasONG.dtos.inputs.FallaTecnicaInputDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.incidentes.EstadoReporte;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.repositorios.falla_tecnica.imp.FallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public class ReporteFallaTecnicaController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    private final Gson gson = new Gson();

    @Builder.Default
    ConverterFallaDTO converter = ServiceLocator.getService(ConverterFallaDTO.class);

    @Builder.Default
    private ConverterHeladerasLoginDto converterHeladeras = ServiceLocator.getService(ConverterHeladerasLoginDto.class);

    @Builder.Default
    FallasTecnicasRepositorio repoFallas = ServiceLocator.getService(FallasTecnicasRepositorio.class);

    @Builder.Default
    HeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);


    @Override
    public void index(Context context) {
        context.render("conUsuario/reportar_falla_tecnica.hbs");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {

        UploadedFile imagenFalla = context.uploadedFile("imagenesFalla");

        FallaTecnicaInputDTO dto = FallaTecnicaInputDTO.builder()
                .colaboradorId(context.sessionAttribute("humano-id"))
                .descripcionFalla(context.formParam("descripcionFalla"))
                .heladeraId(Long.valueOf(Objects.requireNonNull(context.formParam("heladeraId"))))
                .build();

        List<FallaTecnica> fallas= repoFallas.buscarTodos().stream().filter(fallaTecnica -> fallaTecnica.getEstadoReporte()!= EstadoReporte.RESUELTO).toList();


        boolean existeFallaParaHeladera = fallas.stream()
                .anyMatch(f -> f.getHeladeraAsociada().getId().equals(dto.getHeladeraId()));

        if (existeFallaParaHeladera) {

            context.status(200).json(gson.toJson(Map.of("success", false, "message", "Ya existe una falla reportada para esta heladera.")));
            return;
        }
        Heladera heladeraReportada = repoHeladeras.buscar(Long.valueOf(Objects.requireNonNull(context.formParam("heladeraId"))))
                .orElseThrow(() -> new RuntimeException("Heladera no encontrada"));

        heladeraReportada.setEstadoHeladera(EstadoHeladera.CON_FALLA_TECNICA);

        FallaTecnica fallaTecnica = converter.convertToFallaTecnica(dto, imagenFalla);

        heladeraReportada.registarIncidente(fallaTecnica);

        withTransaction(() -> {
            repoFallas.guardar(fallaTecnica);
            repoHeladeras.actualizar(heladeraReportada);
        });


        context.status(200).json(gson.toJson(Map.of("success", true, "message", "Falla técnica reportada con éxito")));
    }


    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) throws IOException {

    }

    @Override
    public void delete(Context context) {

    }
}
