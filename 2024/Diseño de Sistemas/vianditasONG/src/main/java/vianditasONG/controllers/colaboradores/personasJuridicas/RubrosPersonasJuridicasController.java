package vianditasONG.controllers.colaboradores.personasJuridicas;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterRubrosPersonaJuridicaDTO;
import vianditasONG.dtos.outputs.colaboradores.personasJuridicas.RubroPersonaJuridicaDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.rubros.IRubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class RubrosPersonasJuridicasController implements ICrudViewsHandler {
    @Builder.Default
    private IRubrosPersonaJuridicaRepositorio repoRubros = ServiceLocator.getService(RubrosPersonaJuridicaRepositorio.class);
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private ConverterRubrosPersonaJuridicaDTO converterRubrosPersonaJuridica = ServiceLocator.getService(ConverterRubrosPersonaJuridicaDTO.class);

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

        List<RubroPersonaJuridica> listaRubros = this.repoRubros.buscarTodos();

        List<RubroPersonaJuridicaDTO> listaRubrosDTO = listaRubros
                .stream()
                .map(converterRubrosPersonaJuridica::convertToDTO)
                .collect(Collectors.toList());


        String respuestaJSON = gson.toJson(listaRubrosDTO);

        context.json(respuestaJSON);
    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context){

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}

