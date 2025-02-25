package vianditasONG.converters.dtoconverters;

import io.javalin.http.UploadedFile;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.FallaTecnicaInputDTO;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.incidentes.EstadoReporte;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;


@Builder
public class ConverterFallaDTO {

    @Builder.Default
    HumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);

    @Builder.Default
    HeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);

    public FallaTecnica convertToFallaTecnica(FallaTecnicaInputDTO dto, UploadedFile imagenFalla) throws IOException {
        Humano colaborador = repoHumanos.buscar(dto.getColaboradorId())
                .orElseThrow(() -> new RuntimeException("Humano con ID " + dto.getColaboradorId() + " no encontrado"));

        Heladera heladera = repoHeladeras.buscar(dto.getHeladeraId())
                .orElseThrow(() -> new RuntimeException("Humano con ID " + dto.getHeladeraId() + " no encontrado"));

        String rutaImagen = guardarImagen(imagenFalla);

        FallaTecnica fallaTecnica = FallaTecnica.builder()
                .colaborador(colaborador)
                .foto(rutaImagen)
                .estadoReporte(EstadoReporte.EN_PROCESO)
                .build();
        fallaTecnica.setFechaYHora(LocalDateTime.now());
        fallaTecnica.setFoto(rutaImagen);
        fallaTecnica.setDescripcion(dto.getDescripcionFalla());
        fallaTecnica.setHeladeraAsociada(heladera);

        return fallaTecnica;

    }


    private String guardarImagen(UploadedFile archivo) throws IOException {
        if (archivo == null) {
            return null;
        }

        Path destination = Paths.get("uploads/" + archivo.filename());

        Files.copy(archivo.content(), destination, StandardCopyOption.REPLACE_EXISTING);

        return "uploads/"+archivo.filename();
    }
}
