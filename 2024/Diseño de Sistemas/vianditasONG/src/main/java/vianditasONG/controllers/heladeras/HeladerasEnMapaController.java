package vianditasONG.controllers.heladeras;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterHeladerasLoginDto;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.utils.Persistente;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class HeladerasEnMapaController {
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private IHeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private ConverterHeladerasLoginDto converterHeladeras = ServiceLocator.getService(ConverterHeladerasLoginDto.class);

    public void index(Context context) {


        List<Heladera> listaHeladeras = this.repoHeladeras.buscarTodos();

        List<HeladeraEnMapaDTO> listaHeladerasDTO = listaHeladeras
                .stream()
                .filter(Persistente::getActivo)
                .map(converterHeladeras::convertToDTO)
                .collect(Collectors.toList());

        String respuestaJSON = gson.toJson(listaHeladerasDTO);
        
        context.json(respuestaJSON);
    }
}

