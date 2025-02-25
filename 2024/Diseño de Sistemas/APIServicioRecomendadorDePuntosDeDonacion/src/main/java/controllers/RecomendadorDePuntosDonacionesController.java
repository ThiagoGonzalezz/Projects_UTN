package controllers;

import io.javalin.Javalin;
import config.Config;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import services.IRecomendadorDePuntosDonacionesService;
import utils.ParserParametros;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


public class RecomendadorDePuntosDonacionesController {

    public RecomendadorDePuntosDonacionesController(Javalin app, IRecomendadorDePuntosDonacionesService recomendadorDePuntosService) {


        app.get("/api/recomendadorDePuntos", ctx -> {

            float radio = Float.parseFloat(Config.getInstancia().obtenerDelConfig("radioDefault"));
            LocalTime horarioApertura;
            LocalTime horarioCierre;
            List<DayOfWeek> diasAbierto;
            List<PuntoDeDonacion> puntos = null;


            double latitud = ParserParametros.parsearCoordenada(ctx.queryParam("latitud"));
            double longitud = ParserParametros.parsearCoordenada(ctx.queryParam("longitud"));

            if (ctx.queryParam("radio") != null)
                radio = ParserParametros.parsearRadio(ctx.queryParam("radio"));

            if (ctx.queryParam("horarioBuscado") == null && ctx.queryParam("diasBuscados") == null) {
                puntos = recomendadorDePuntosService.recomendarPuntos(latitud, longitud, radio);

            } else if (ctx.queryParam("horarioBuscado") != null && ctx.queryParam("diasBuscados") != null) {
                LocalTime horarioBuscado = ParserParametros.parsearHorario(ctx.queryParam("horarioBuscado"));
                List<DayOfWeek> diasBuscados = ParserParametros.parsearDias(ctx.queryParam("diasBuscados"));
                puntos = recomendadorDePuntosService.recomendarPuntos(latitud, longitud, radio, horarioBuscado, diasBuscados);

            } else if (ctx.queryParam("horarioBuscado") != null) {
                LocalTime horarioBuscado = ParserParametros.parsearHorario(ctx.queryParam("horarioBuscado"));
                puntos = recomendadorDePuntosService.recomendarPuntos(latitud, longitud, radio, horarioBuscado);

            } else if (ctx.queryParam("diasBuscados") != null) {
                List<DayOfWeek> diasBuscados = ParserParametros.parsearDias(ctx.queryParam("diasBuscados"));
                puntos = recomendadorDePuntosService.recomendarPuntos(latitud, longitud, radio, diasBuscados);
            }

            ctx.json(puntos);
        });
    }
}
