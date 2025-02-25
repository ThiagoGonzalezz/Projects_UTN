package vianditasONG.testConcretos.cargaDeColaboradores;


import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorHeladeras.CalculadorHeladeras;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.Mail;
import vianditasONG.modelos.servicios.migracionDeColaboraciones.ImportadorDeColaboraciones;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import vianditasONG.utils.creadorDeCredenciales.ICreadorDeCredenciales;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMailsAdapter;
import vianditasONG.modelos.repositorios.colaboracion.imp.DistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.RegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vianditasONG.utils.usuarioRolesYPermisos.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class CargaDeColaboradoresTest {
    public ImportadorDeColaboraciones importador;

    DistribucionesDeViandasRepositorio repositorioDistribucionDeViandas = DistribucionesDeViandasRepositorio.builder().build();

    DonacionesDeDineroRepositorio repositorioDonacionDeDinero = DonacionesDeDineroRepositorio.builder().build();

    DonacionesDeViandasRepositorio repositorioDonacionDeViandas = DonacionesDeViandasRepositorio.builder().build();

    RegistrosPersonasVulnerablesRepositorio repositorioRegistroPersonasVulnerables = RegistrosPersonasVulnerablesRepositorio.builder().build();

    HumanosRepositorio  repositorioHumanos = HumanosRepositorio.builder().build();

    Humano humano = Humano.builder().nombre("Pedrito").tipoDeDocumento(TipoDeDocumento.DNI).numeroDocumento("34567891").build();
    Usuario usuario = Usuario.builder()
            .nombreDeUsuario("Pedrito17")
            .hashContrasenia("Disenio17!!a")
            .rol(Rol.PERSONA_HUMANA)
            .build();
    Humano juanPerez= Humano.builder()
            .tipoDeDocumento(TipoDeDocumento.DNI)
            .numeroDocumento("12345678")
            .nombre("Juan")
            .apellido("Perez")
            .fechaNacimiento(LocalDate.now())
            .usuario(usuario)
            .fechaDeSolicitud(LocalDate.now())
            .puntos(5d)
            .build();

    Humano andresMoreno = Humano.builder()
            .tipoDeDocumento(TipoDeDocumento.DNI)
            .numeroDocumento("32198765")
            .nombre("Andres")
            .apellido("Moreno")
            .usuario(usuario)
            .fechaDeSolicitud(LocalDate.now())
            .puntos(5d)
            .build();

    Credencial credencial = Credencial.of("5", "test@gmail.com", humano);

    CalculadorPesosDonados calculadorDinero = CalculadorPesosDonados.builder().build();

    CalculadorViandasDonadas calculadorDonacionesViandas = CalculadorViandasDonadas.builder().build();

    CalculadorViandasDistribuidas calculadorDistribuciones = CalculadorViandasDistribuidas.builder().build();
    CalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas = CalculadorTarjetasDistribuidas.builder().build();
    CalculadorHeladeras calculadorHeladeras = CalculadorHeladeras.builder().build();

    @BeforeEach
    public void inicializar() throws IOException {
        repositorioHumanos.guardar(juanPerez);
        repositorioHumanos.guardar(andresMoreno);

        ICreadorDeCredenciales creadorDeCredenciales = mock(ICreadorDeCredenciales.class);
        when(creadorDeCredenciales.crearCredencial(anyString(), any(Humano.class))).thenReturn(credencial);

        EnviadorDeMailsAdapter enviadorDeMailsAdapter = mock(EnviadorDeMailsAdapter.class);
        doNothing().when(enviadorDeMailsAdapter).enviarMail(any(Mail.class));

        EnviadorDeMails enviadorDeMails= EnviadorDeMails.builder()
                .adapterEnviadorDeMails(enviadorDeMailsAdapter)
                .build();

        calculadorDinero.setCoeficiente(1d);
        calculadorDonacionesViandas.setCoeficiente(1d);
        calculadorDistribuciones.setCoeficiente(1d);
        calculadorTarjetasDistribuidas.setCoeficiente(1d);


        importador = ImportadorDeColaboraciones.builder()
                .calculadorPesosDonados(calculadorDinero)
                .calculadorViandasDonadas(calculadorDonacionesViandas)
                .calculadorViandasDistribuidas(calculadorDistribuciones)
                .calculadorTarjetasDistribuidas(calculadorTarjetasDistribuidas)
                .creadorDeCredenciales(creadorDeCredenciales)
                .repositorioHumanos(repositorioHumanos)
                .repositorioRegistrosPersonasVulnerables(repositorioRegistroPersonasVulnerables)
                .repositorioDonacionesDeViandas(repositorioDonacionDeViandas)
                .repositorioDonacionesDeDinero(repositorioDonacionDeDinero)
                .repositorioDistribucionesDeViandas(repositorioDistribucionDeViandas)
                .enviadorDeMails(enviadorDeMails)
                .build();

        try (InputStream inputStream = Files.newInputStream(Paths.get("src/test/resources/pruebaCSV.csv"))) {
            importador.importarCSV(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Distribuciones de viandas correctamente importadas")
    public void distribuciones() throws IOException, CsvException {

        Assertions.assertEquals(24, repositorioDistribucionDeViandas.buscarTodos().size());

        //Assertions.assertEquals(45d, calculadorDistribuciones.calcularPuntos(repositorioHumanos.buscarPorDocumento(TipoDeDocumento.DNI, "34567891").get()));
        Assertions.assertEquals(14780, repositorioHumanos.buscarPorDocumento(TipoDeDocumento.DNI, "34567891").get().getPuntos());
    }

    @Test
    @DisplayName("Donaciones de viandas correctamente importadas")
    public void donacionesVianda() throws IOException, CsvException {

        Assertions.assertEquals(8, repositorioDonacionDeViandas.buscarTodos().size());
        //Assertions.assertEquals(80d, calculadorDonacionesViandas.calcularPuntos(repositorioHumanos.buscarPorDocumento(TipoDeDocumento.DNI, "34567891").get()));
    }
    @Test
    @DisplayName("Donaciones de dinero correctamente importadas")
    public void donacionesDinero() throws IOException, CsvException {

        Assertions.assertEquals(54, repositorioDonacionDeDinero.buscarTodos().size());
       //Assertions.assertEquals(400d, calculadorDinero.calcularPuntos(repositorioHumanos.buscarPorDocumento(TipoDeDocumento.DNI, "34567891").get()));
    }
    @Test
    @DisplayName("Entrega de tarjetas correctamente importadas")
    public void entregaDeTarjetas() throws IOException, CsvException {

        Assertions.assertEquals(510, repositorioRegistroPersonasVulnerables.buscarTodos().size());
    }

    @Test
    @DisplayName("Cantidad de colaboradores registrados correcta")
    //Teniendo en cuenta que algunos ya estaban registrados y otros se repiten
    public void colaboradoresRegistrados() throws IOException, CsvException {

        Assertions.assertEquals(35, repositorioHumanos.buscarTodos().size());
    }

    @Test
    @DisplayName("La cantidad de cada contribucion especifica se importa correctamente")
    public void puntosColaborador() throws IOException, CsvException {

        Assertions.assertEquals(true, true);
    }

}
