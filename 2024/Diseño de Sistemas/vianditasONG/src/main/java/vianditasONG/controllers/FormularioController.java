package vianditasONG.controllers;

import io.javalin.http.Context;
import vianditasONG.config.ServiceLocator;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.ServerErrorException;
import vianditasONG.modelos.entities.formulario.*;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.IOpcionesRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.OpcionesRepositorio;

import java.time.LocalDate;
import java.util.List;

public interface FormularioController {

    default FormularioRespondido crearFormularioRespondido(Context context, String nombreForm) {
        try {
            IFormulariosRepositorio repoFormularios = ServiceLocator.getService(FormulariosRepositorio.class);
            IOpcionesRepositorio repoOpciones = ServiceLocator.getService(OpcionesRepositorio.class);

            Formulario formAsociado = repoFormularios.buscarPorNombre(nombreForm).orElseThrow(ServerErrorException::new);


            FormularioRespondido formularioRespondido = FormularioRespondido.builder()
                    .formularioAsociado(formAsociado)
                    .fechaRealizacion(LocalDate.now())
                    .build();

            Respuesta respuesta;

            for (Pregunta pregunta : formAsociado.getPreguntas()) {
                if (pregunta.getTipo() == TipoPregunta.PREGUNTA_A_DESARROLLAR) {
                    respuesta = Respuesta.builder()
                            .respuestaLibre(context.formParam(String.valueOf(pregunta.getId())))
                            .build();
                } else {
                    List<String> opcionesSeleccionadasIds = context.formParams(String.valueOf(pregunta.getId()));

                    respuesta = Respuesta.builder().build();

                    for (String idOpcion : opcionesSeleccionadasIds) {

                        respuesta.agregarOpciones(repoOpciones.buscarPorId(Long.valueOf(idOpcion)).orElseThrow(BadRequestException::new));

                    }
                }

                respuesta.setPreguntaAsociada(pregunta);

                formularioRespondido.agregarRespuestas(respuesta);

            }

            return formularioRespondido;

        } catch (RuntimeException e) {
            throw new BadRequestException();
        }
    }
}
