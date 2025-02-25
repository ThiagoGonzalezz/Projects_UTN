package vianditasONG.controllers.formularios;


import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterFormularioRespondidoDTO;
import vianditasONG.dtos.outputs.formulario.FormularioRespondidoDTO;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRespondidosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRespondidosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;

import java.util.Optional;

@Builder
public class FormularioRespondidoPJController {
    @Builder.Default
    IPersonasJuridicasRepositorio repositorioPersonasJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private final Gson gson = new Gson();
    @Builder.Default
    private ConverterFormularioRespondidoDTO converterFormRespondidoDto = ServiceLocator.getService(ConverterFormularioRespondidoDTO.class);

    public void show(Context context) {
        Optional<PersonaJuridica> posiblePJ = this.repositorioPersonasJuridicas.buscarPorId(Long.valueOf(context.pathParam("id")));

        if (posiblePJ.isEmpty()) {
            throw new RuntimeException("No fue posible encontrar el formulario respondido.");
        }

        PersonaJuridica pjAsociada = posiblePJ.get();

        FormularioRespondidoDTO formularioRespondidoDTO = this.converterFormRespondidoDto.convertToDto(pjAsociada.getFormularioRespondido(), pjAsociada.getRazonSocial());

        String respuestaJSON = gson.toJson(formularioRespondidoDTO);

        context.json(respuestaJSON).status(200);
    }
}
