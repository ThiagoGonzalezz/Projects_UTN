package vianditasONG.modelos.entities.colaboraciones;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorHeladeras.CalculadorHeladeras;
import vianditasONG.modelos.servicios.reportes.GeneradorDeEstadisticas;
import vianditasONG.modelos.servicios.reportes.Reportador;

import java.util.List;
import java.util.Optional;

public class HacerseCargoDeHeladeraMain implements WithSimplePersistenceUnit {

    public void ejecutar() {
        PersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
        HeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
        HacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);
        CalculadorHeladeras calculadorHeladeras = CalculadorHeladeras.builder().build();

        List<Object[]> estadisticasHeladeras = personasJuridicasRepositorio.obtenerEstadisticasHeladerasActivas();

        for (Object[] estadistica : estadisticasHeladeras) {
            Long personaJuridicaId = (Long) estadistica[0];
            Long cantHeladerasActivas = (Long) estadistica[1];
            Long totalMesesActiva = (Long) estadistica[2];
            HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) estadistica[3];
            Heladera heladera = (Heladera) estadistica[4];

            Optional<PersonaJuridica> personaJuridicaOpt = personasJuridicasRepositorio.buscarPorId(personaJuridicaId);
            if (personaJuridicaOpt.isPresent()) {
                PersonaJuridica personaJuridica = personaJuridicaOpt.get();

                Double puntos = calculadorHeladeras.calcularPuntos(cantHeladerasActivas, totalMesesActiva);

                personaJuridica.sumarPuntos(puntos);
                hacerseCargoDeHeladera.sumarPuntos(puntos);
                heladera.incrementarMesActiva();

                withTransaction(() -> {
                    personasJuridicasRepositorio.actualizar(personaJuridica);
                    hacerseCargoDeHeladerasRepositorio.actualizar(hacerseCargoDeHeladera);
                    heladerasRepositorio.actualizar(heladera);
                });
            }
        }
    }

    public static void main(String[] args) {
        new HacerseCargoDeHeladeraMain().ejecutar();
    }
}


//Busca por cada PJ todas las heladeras activas que tiene en el momento y la sumatoria de los meses que estuvieron activa a su cargo hasta entonces(seguido)
//Suma los puntos correspondientes en la PJ y se agregan en la colaboracion HacerseCargoHeladera como acumulados

