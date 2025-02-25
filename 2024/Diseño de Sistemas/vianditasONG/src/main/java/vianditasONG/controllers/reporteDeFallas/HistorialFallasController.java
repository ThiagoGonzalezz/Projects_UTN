package vianditasONG.controllers.reporteDeFallas;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.outputs.FallaTecnicaOutputDTO;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.repositorios.falla_tecnica.imp.FallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class HistorialFallasController implements ICrudViewsHandler {

    @Builder.Default
    private FallasTecnicasRepositorio fallasTecnicasRepositorio = ServiceLocator.getService(FallasTecnicasRepositorio.class);

    @Builder.Default
    private HumanosRepositorio humanosRepositorio = ServiceLocator.getService(HumanosRepositorio.class);

    @Override
    public void index(Context context) {

        Long usuarioId = context.sessionAttribute("usuario-id");

        Humano humano = humanosRepositorio.buscarPorUsuario(usuarioId).orElseThrow(() -> new RuntimeException("Humano no encontrado"));


        List<FallaTecnica> fallasTecnicas = fallasTecnicasRepositorio.buscarFallasPorHumano(humano.getId());


        context.render("conUsuario/historial_fallas.hbs", Map.of("fallasTecnicas", fallasTecnicas));
    }

    @Override
    public void show(Context context) {

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


