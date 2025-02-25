package models.entities.recomendador;

import lombok.Builder;
import models.entities.calculoDeCercanias.IVerificadorDeCercania;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.entities.verificadorDias.IVerificadorDiasAbierto;
import models.entities.verificadorHorarios.IVerificadorDeHorarios;
import utils.ServiceLocator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
public class RecomendadorDePuntos {
    @Builder.Default
    private List<PuntoDeDonacion> listaDePuntosDeDonacion = new ArrayList<>();
    @Builder.Default
    private IVerificadorDeCercania verificadorDeCercania = ServiceLocator.getService(IVerificadorDeCercania.class);
    @Builder.Default
    private IVerificadorDiasAbierto verificadorDiasAbierto = ServiceLocator.getService(IVerificadorDiasAbierto.class);
    @Builder.Default
    private IVerificadorDeHorarios verificadorDeHorarios = ServiceLocator.getService(IVerificadorDeHorarios.class);


    public void agregarPuntosDeDonacion(PuntoDeDonacion ... puntosDeDonacion){
        Collections.addAll(this.listaDePuntosDeDonacion, puntosDeDonacion);
    }

    public List<PuntoDeDonacion> recomendarPuntosDeDonacion(PuntoGeografico puntoInicial, float distanciaMaxEnKm){
        return this.listaDePuntosDeDonacion.stream().filter(
                    puntoDeDonacion -> this.verificadorDeCercania
                    .estanCerca(puntoInicial
                    ,puntoDeDonacion.getPuntoGeografico()
                    ,distanciaMaxEnKm))
                    .toList();
    }

    public List<PuntoDeDonacion> recomendarPuntosDeDonacion(PuntoGeografico puntoInicial, float distanciaMaxEnKm, List<DayOfWeek> diasBuscados){
        return this.listaDePuntosDeDonacion.stream().filter(
                        puntoDeDonacion -> this.verificadorDeCercania
                                .estanCerca(puntoInicial
                                        ,puntoDeDonacion.getPuntoGeografico()
                                        ,distanciaMaxEnKm)
                                && this.verificadorDiasAbierto
                                .coincideAlgunDia(puntoDeDonacion, diasBuscados))
                .toList();
    }

    public List<PuntoDeDonacion> recomendarPuntosDeDonacion(PuntoGeografico puntoInicial, float distanciaMaxEnKm, LocalTime horaBuscada){
        return this.listaDePuntosDeDonacion.stream().filter(
                puntoDeDonacion -> this.verificadorDeCercania
                                .estanCerca(puntoInicial
                                        ,puntoDeDonacion.getPuntoGeografico()
                                        ,distanciaMaxEnKm)
                                && this.verificadorDeHorarios
                                .coincideHorario(puntoDeDonacion, horaBuscada))
                .toList();
    }

    public List<PuntoDeDonacion> recomendarPuntosDeDonacion(PuntoGeografico puntoInicial, float distanciaMaxEnKm, LocalTime horaBuscada, List<DayOfWeek> diasBuscados){
        return this.listaDePuntosDeDonacion.stream().filter(
                        puntoDeDonacion -> this.verificadorDeCercania
                                .estanCerca(puntoInicial
                                        ,puntoDeDonacion.getPuntoGeografico()
                                        ,distanciaMaxEnKm)
                                && this.verificadorDeHorarios
                                .coincideHorario(puntoDeDonacion, horaBuscada)
                                && this.verificadorDiasAbierto
                                .coincideAlgunDia(puntoDeDonacion, diasBuscados))
                .toList();
    }
}
