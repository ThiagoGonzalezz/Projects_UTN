package vianditasONG.modelos.entities.heladeras;

import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import lombok.Setter;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
@Setter
public class HeladeraEvaluadorDesconexion {
    private static final Logger logger = Logger.getLogger(HeladeraEvaluadorDesconexion.class.getName());
    private IHeladerasRepositorio repositorio;

    public HeladeraEvaluadorDesconexion(IHeladerasRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar() {
        try {
            FileHandler fileHandler = new FileHandler("heladera_evaluador_temp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.info("Iniciando cronjob para evaluar estado de heladeras...");
            List<Heladera> heladeras = repositorio.buscarTodos();

            for (Heladera heladera : heladeras) {
                if (heladera.getEstadoHeladera() == EstadoHeladera.ACTIVA) {
                    heladera.revisarEstado();
                    repositorio.actualizar(heladera);
                }
            }

            logger.info("Cronjob finalizado correctamente.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en la ejecución del cronjob", e);
        }
    }

    public static void main(String[] args) {
        IHeladerasRepositorio repositorio = HeladerasRepositorio.builder().build();
        new HeladeraEvaluadorDesconexion(repositorio).ejecutar();
    }
}