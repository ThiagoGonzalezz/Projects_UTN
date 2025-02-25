package utils;

import lombok.Builder;
import models.entities.calculoDeCercanias.CalculadorDeDistancias;
import models.entities.calculoDeCercanias.ICalculadorDeDistancias;
import models.entities.calculoDeCercanias.IVerificadorDeCercania;
import models.entities.calculoDeCercanias.VerificadorDeCercania;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.entities.recomendador.RecomendadorDePuntos;
import models.entities.verificadorDias.IVerificadorDiasAbierto;
import models.entities.verificadorDias.VerificadorDiasAbierto;
import models.entities.verificadorHorarios.IVerificadorDeHorarios;
import models.entities.verificadorHorarios.VerificadorDeHorarios;
import models.repositories.imp.RepositorioPuntosDonacion;
import services.IRecomendadorDePuntosDonacionesService;
import services.RecomendadorDePuntosDonacionesDonacionesService;

public class EncendidoSeguro {
    public static void inicializar(){

        RepositorioPuntosDonacion repositorioPuntosDonacion = RepositorioPuntosDonacion.builder().build();

        CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();

        ServiceLocator.registerService(ICalculadorDeDistancias.class, calculadorDeDistancias);

        VerificadorDeCercania verificadorDeCercania = VerificadorDeCercania.builder().build();

        VerificadorDeHorarios verificadorDeHorarios = VerificadorDeHorarios.builder().build();

        VerificadorDiasAbierto verificadorDiasAbierto = VerificadorDiasAbierto.builder().build();

        ServiceLocator.registerService(IVerificadorDeHorarios.class, verificadorDeHorarios);
        ServiceLocator.registerService(IVerificadorDiasAbierto.class, verificadorDiasAbierto);
        ServiceLocator.registerService(IVerificadorDeCercania.class, verificadorDeCercania);



        RecomendadorDePuntos recomendadorDePuntos = RecomendadorDePuntos.builder().build();

        recomendadorDePuntos.agregarPuntosDeDonacion(repositorioPuntosDonacion.buscarTodos().toArray(new PuntoDeDonacion[0]));

        ServiceLocator.registerService(RecomendadorDePuntos.class, recomendadorDePuntos);

    }
}
