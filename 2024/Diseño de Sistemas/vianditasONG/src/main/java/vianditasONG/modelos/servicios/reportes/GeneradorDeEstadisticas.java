package vianditasONG.modelos.servicios.reportes;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class GeneradorDeEstadisticas implements WithSimplePersistenceUnit {

    public List<Object[]> contarIncidentesPorHeladeraSemanaActual() {
        return entityManager()
                .createQuery("SELECT i.heladeraAsociada.nombre, COUNT(i) " +
                        "FROM Incidente i " +
                        "WHERE FUNCTION('WEEK', i.fechaYHora, 1) = FUNCTION('WEEK', CURRENT_DATE(), 1) " +
                        "AND FUNCTION('YEAR', i.fechaYHora) = FUNCTION('YEAR', CURRENT_DATE()) " +
                        "GROUP BY i.heladeraAsociada", Object[].class)
                .getResultList();
    }

    public List<Object[]> contarDonacionesPorColaborador() {
        return entityManager()
                .createQuery("SELECT d.colaborador.id, COUNT(d) " +
                        "FROM DonacionDeVianda d " +
                        "GROUP BY d.colaborador", Object[].class)
                .getResultList();
    }

    public List<Object[]> contarViandasPorHeladeraSemanaActual() {
        return entityManager()
                .createQuery("SELECT h.nombre, " +
                        "(SELECT COUNT(d) FROM DonacionDeVianda d WHERE d.heladera.id = h.id AND FUNCTION('WEEK', d.fecha, 1) = FUNCTION('WEEK', CURRENT_DATE(), 1) AND FUNCTION('YEAR', d.fecha) = FUNCTION('YEAR', CURRENT_DATE())), " +
                        "(SELECT SUM(dv.cantidadDeViandas) FROM DistribucionDeVianda dv WHERE dv.heladeraDestino.id = h.id AND FUNCTION('WEEK', dv.fecha, 1) = FUNCTION('WEEK', CURRENT_DATE(), 1) AND FUNCTION('YEAR', dv.fecha) = FUNCTION('YEAR', CURRENT_DATE())), " +
                        "(SELECT SUM(r.cantidadViandas) FROM RegistroUsoTarjeta r WHERE r.heladera.id = h.id AND FUNCTION('WEEK', r.fechaUso, 1) = FUNCTION('WEEK', CURRENT_DATE(), 1) AND FUNCTION('YEAR', r.fechaUso) = FUNCTION('YEAR', CURRENT_DATE())), " +
                        "(SELECT SUM(dv.cantidadDeViandas) FROM DistribucionDeVianda dv WHERE dv.heladeraOrigen.id = h.id AND FUNCTION('WEEK', dv.fecha, 1) = FUNCTION('WEEK', CURRENT_DATE(), 1) AND FUNCTION('YEAR', dv.fecha) = FUNCTION('YEAR', CURRENT_DATE())) " +
                        "FROM Heladera h", Object[].class)
                .getResultList();
    }

}
