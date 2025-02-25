import com.twilio.example.Example;
import controllers.RecomendadorDePuntosDonacionesController;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import models.entities.calculoDeCercanias.CalculadorDeDistancias;
import models.entities.calculoDeCercanias.VerificadorDeCercania;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.entities.recomendador.RecomendadorDePuntos;
import models.repositories.IRepositorioPuntosDonacion;
import models.repositories.imp.RepositorioPuntosDonacion;
import services.RecomendadorDePuntosDonacionesDonacionesService;
import utils.EncendidoSeguro;

public class RecomendacionDePuntosMain implements WithSimplePersistenceUnit {

    public static void main(String[] args) {

        EncendidoSeguro.inicializar();

        RecomendadorDePuntosDonacionesDonacionesService recomendadorDePuntosDonacionesService = RecomendadorDePuntosDonacionesDonacionesService.builder().build();

        Javalin app = Javalin.create().start(7000);

        new RecomendadorDePuntosDonacionesController(app, recomendadorDePuntosDonacionesService);

    }
}
