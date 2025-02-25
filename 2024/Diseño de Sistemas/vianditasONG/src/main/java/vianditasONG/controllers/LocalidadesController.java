package vianditasONG.controllers;

import com.google.gson.Gson;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterLocalidadesDTO;
import vianditasONG.converters.dtoconverters.ConverterRubrosPersonaJuridicaDTO;
import vianditasONG.dtos.outputs.LocalidadOutputDTO;
import vianditasONG.dtos.outputs.colaboradores.personasJuridicas.RubroPersonaJuridicaDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.rubros.IRubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class LocalidadesController implements ICrudViewsHandler {

    @Builder.Default
    private ILocalidadesRepositorio repoLocalidades = ServiceLocator.getService(LocalidadesRepositorio.class);
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private ConverterLocalidadesDTO converterLocalidades = ServiceLocator.getService(ConverterLocalidadesDTO.class);

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

        Provincia provincia = Provincia.valueOf(context.queryParam("provincia"));

        List<Localidad> listaLocalidades = this.repoLocalidades.buscarTodosPorProvincia(provincia);

        List<LocalidadOutputDTO> listaLocalidadesDTO = listaLocalidades
                .stream()
                .map(converterLocalidades::convertToDTO)
                .collect(Collectors.toList());


        String respuestaJSON = gson.toJson(listaLocalidadesDTO);

        context.json(respuestaJSON);
    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {

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
