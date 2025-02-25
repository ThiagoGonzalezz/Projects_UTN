package vianditasONG.controllers;

import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.datosGenerales.AreaDeBusqueda;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.servicios.conversorDirecAPunto.OpenCageAdapterAPI;
import vianditasONG.modelos.servicios.recomendadorDePuntos.PuntosRecomendadosAdapter;
import vianditasONG.modelos.servicios.recomendadorDePuntos.PuntosRecomendadosAdapterAPI;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class RecomendadorPuntosController {
    @Builder.Default
    private ConversorDirecAPuntoAdapter conversorDirecAPunto = ServiceLocator.getService(OpenCageAdapterAPI.class);
    @Builder.Default
    private PersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private PuntosRecomendadosAdapter servicioRecomendadorDePuntos = ServiceLocator.getService(PuntosRecomendadosAdapterAPI.class);

    public void obtenerPuntosRecomendados(Context context) throws IOException {

        PersonaJuridica colaborador = personasJuridicasRepositorio.buscarPorId(context.sessionAttribute("persona-juridica-id"))
                .orElseThrow(() -> new RuntimeException("Persona juridica con ID " + context.sessionAttribute("persona-juridica-id") + " no encontrada"));

        Direccion direccion = colaborador.getDireccion();
        PuntoGeografico puntoGeografico = this.conversorDirecAPunto.obtenerPuntoGeografico(direccion);
        AreaDeBusqueda areaDeBusqueda = AreaDeBusqueda.builder().puntoGeografico(puntoGeografico).build();
        List<PuntoEstrategico> puntosARecomendar = this.servicioRecomendadorDePuntos.recomendarPuntos(areaDeBusqueda);

        Map<String, Object> respuestaPuntosRecomendados = new HashMap<>();
        respuestaPuntosRecomendados.put("puntos", puntosARecomendar);
        context.json(respuestaPuntosRecomendados);

    }
}
