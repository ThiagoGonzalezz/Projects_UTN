package vianditasONG.testConcretos.heladeras;

import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.HeladeraEvaluadorDesconexion;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EvaluadorDesconexionHeladerasTest {

    private static final Logger logger = Logger.getLogger(EvaluadorDesconexionHeladerasTest.class.getName());

    @Test
    public void testEvaluacionDesconexionHeladeras() throws IOException {
        IHeladerasRepositorio repositorioMock = mock(IHeladerasRepositorio.class);

        Heladera heladera1 = mock(Heladera.class);
        Heladera heladera2 = mock(Heladera.class);
        Heladera heladera3 = mock(Heladera.class);

        when(heladera1.getEstadoHeladera()).thenReturn(EstadoHeladera.ACTIVA);
        when(heladera2.getEstadoHeladera()).thenReturn(EstadoHeladera.ACTIVA);
        when(heladera3.getEstadoHeladera()).thenReturn(EstadoHeladera.DESCONECTADA);

        List<Heladera> heladeras = Arrays.asList(heladera1, heladera2, heladera3);
        when(repositorioMock.buscarTodos()).thenReturn(heladeras);

        HeladeraEvaluadorDesconexion evaluador = new HeladeraEvaluadorDesconexion(repositorioMock);

        FileHandler fileHandler = new FileHandler("heladera_evaluador_temp_test.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);

        try {
            logger.info("Iniciando cronjob para evaluar estado de heladeras...");

            evaluador.ejecutar();

            verify(heladera1, times(1)).revisarEstado();
            verify(heladera2, times(1)).revisarEstado();
            verify(heladera3, never()).revisarEstado();

            verify(repositorioMock, times(1)).actualizar(heladera1);
            verify(repositorioMock, times(1)).actualizar(heladera2);
            verify(repositorioMock, never()).actualizar(heladera3);

            logger.info("Cronjob finalizado correctamente.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en la ejecución del cronjob", e);
        } finally {
            fileHandler.close();
        }
    }
}