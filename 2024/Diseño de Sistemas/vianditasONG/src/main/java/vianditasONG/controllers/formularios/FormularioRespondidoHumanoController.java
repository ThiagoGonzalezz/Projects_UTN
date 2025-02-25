package vianditasONG.controllers.formularios;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterFormularioRespondidoDTO;
import vianditasONG.dtos.outputs.formulario.FormularioRespondidoDTO;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;

import java.util.Optional;

@Builder
public class FormularioRespondidoHumanoController {
    @Builder.Default
    IHumanosRepositorio repositorioHumanos = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private ConverterFormularioRespondidoDTO converterFormRespondidoDto = ServiceLocator.getService(ConverterFormularioRespondidoDTO.class);

    public void show(Context context) {
        Optional<Humano> posibleHumano = this.repositorioHumanos.buscar(Long.valueOf(context.pathParam("id")));

        if (posibleHumano.isEmpty()) {
            throw new RuntimeException("No fue posible encontrar el formulario respondido.");
        }

        Humano humanoAsociado = posibleHumano.get();

        FormularioRespondidoDTO formularioRespondidoDTO = this.converterFormRespondidoDto.convertToDto(humanoAsociado.getFormularioRespondido(), humanoAsociado.getNombre() + " " + humanoAsociado.getApellido());

        String respuestaJSON = gson.toJson(formularioRespondidoDTO);

        context.json(respuestaJSON).status(200);
    }
}