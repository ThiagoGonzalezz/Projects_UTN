package vianditasONG.controllers.modelos;


import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.InputOuput.ModeloDTO;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;

import io.javalin.http.Context;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class ModelosController {

    @Builder.Default
    private ModelosRepositorio modelosRepositorio = ServiceLocator.getService(ModelosRepositorio.class);


    public void buscarModelos(Context context) {

        String query = context.queryParam("q");

        if (query == null || query.isEmpty()) {
            context.status(400).result("Falta el parámetro de consulta 'q'");
            return;
        }


        Optional<Modelo> modelos = modelosRepositorio.buscarPorDescripcion(query);

        List<ModeloDTO> modelosDTO = modelos.stream()
                .map(modelo -> ModeloDTO.builder()
                        .id(modelo.getId())
                        .descripcion(modelo.getDescripcion())
                        .build())
                .collect(Collectors.toList());

        context.json(modelosDTO);
    }
}
