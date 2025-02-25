package vianditasONG.controllers.tecnicos;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterTecnicoDTO;
import vianditasONG.dtos.outputs.tecnicos.TecnicoOutputDTO;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public class MostrarTecnicosController {

    @Builder.Default
    private ConverterTecnicoDTO converterTecnicoDTO = ServiceLocator.getService(ConverterTecnicoDTO.class);

    @Builder.Default
    private TecnicosRepositorio tecnicosRepositorio = ServiceLocator.getService(TecnicosRepositorio.class);

    @Builder.Default
    private final Gson gson = new Gson();

    public void index(Context context) {
        List<Tecnico> tecnicos = tecnicosRepositorio.buscarTodos();

        // Convertir la lista de Tecnico a TecnicoOutputDTO solo si el tecnico está activo
        List<TecnicoOutputDTO> tecnicosDTO = tecnicos.stream()
                .filter(Tecnico::getActivo) // Filtrar solo los técnicos activos
                .map(tecnico -> {
                    try {
                        return converterTecnicoDTO.convertToTecnicoOutputDTO(tecnico);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Devuelve null o maneja el error según el caso; aquí devuelvo null para evitar detener el stream
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Filtrar cualquier valor null en caso de error
                .collect(Collectors.toList());

        String respuestaJSON = gson.toJson(tecnicosDTO);
        context.json(respuestaJSON);
    }
}

