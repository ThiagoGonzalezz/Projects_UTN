package db;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.repositories.imp.RepositorioPuntosDonacion;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertNotNull;


public class ContextTest implements SimplePersistenceTest {

    @Test
    void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    void contextUpWithTransaction() throws Exception {
        withTransaction(() -> {

        });
    }

}