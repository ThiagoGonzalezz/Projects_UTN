package vianditasONG.modelos.servicios.migracionDeColaboraciones;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.repositorios.colaboracion.imp.DistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.RegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.ICalculadorPesosDonados;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.ICalculadorTarjetasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.ICalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.ICalculadorViandasDonadas;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.MailCredencial;
import vianditasONG.utils.creadorDeCredenciales.CreadorDeCredenciales;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import vianditasONG.utils.creadorDeCredenciales.ICreadorDeCredenciales;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.repositorios.colaboracion.IDistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IRegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ImportadorDeColaboraciones implements WithSimplePersistenceUnit {
    @Builder.Default
    private ICreadorDeCredenciales creadorDeCredenciales = ServiceLocator.getService(CreadorDeCredenciales.class);
    @Builder.Default
    private IHumanosRepositorio repositorioHumanos = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IDistribucionesDeViandasRepositorio repositorioDistribucionesDeViandas = ServiceLocator.getService(DistribucionesDeViandasRepositorio.class);
    @Builder.Default
    private IDonacionesDeViandasRepositorio repositorioDonacionesDeViandas = ServiceLocator.getService(DonacionesDeViandasRepositorio.class);
    @Builder.Default
    private IRegistrosPersonasVulnerablesRepositorio repositorioRegistrosPersonasVulnerables = ServiceLocator.getService(RegistrosPersonasVulnerablesRepositorio.class);
    @Builder.Default
    private IDonacionesDeDineroRepositorio repositorioDonacionesDeDinero = ServiceLocator.getService(DonacionesDeDineroRepositorio.class);
    @Builder.Default
    private ICalculadorPesosDonados calculadorPesosDonados = ServiceLocator.getService(CalculadorPesosDonados.class);
    @Builder.Default
    private ICalculadorViandasDistribuidas calculadorViandasDistribuidas = ServiceLocator.getService(CalculadorViandasDistribuidas.class);
    @Builder.Default
    private ICalculadorViandasDonadas calculadorViandasDonadas = ServiceLocator.getService(CalculadorViandasDonadas.class);
    @Builder.Default
    private ICalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas = ServiceLocator.getService(CalculadorTarjetasDistribuidas.class);
    @Builder.Default
    private EnviadorDeMails enviadorDeMails = ServiceLocator.getService(EnviadorDeMails.class);



    public void importarCSV(InputStream inputStream) throws IOException{
        List<LineaColaboracion> lineas = new LectorColaboraciones().leerArchivoCSV(inputStream);

        for(LineaColaboracion linea : lineas) {

        this.cargarColaboracion(linea);

        }
    }

    private void cargarColaboracion(LineaColaboracion linea) throws IOException {
        Optional<Humano> humanoBuscado;
        TipoDeDocumento tipoDocumento = TipoDeDocumento.valueOf(linea.getTipoDoc());

        humanoBuscado = this.repositorioHumanos.buscarPorDocumento(tipoDocumento, linea.getDocumento());

        humanoBuscado.ifPresentOrElse(
                humano -> {
                    this.guardarColaboracionesSegunTipo(humano, linea.getFormaDeColaboracion(), Double.valueOf(linea.getCantidad()), linea.getFechaColaboracion());
                    },
                () -> {
                    Humano humanoCreado = this.registrarHumano(linea.getNombre(), linea.getApellido(), TipoDeDocumento.valueOf(linea.getTipoDoc()), linea.getDocumento(), linea.getMail());

                    this.guardarColaboracionesSegunTipo(humanoCreado, linea.getFormaDeColaboracion(), Double.valueOf(linea.getCantidad()), linea.getFechaColaboracion());

                    Credencial credencial = this.creadorDeCredenciales.crearCredencial(linea.getMail(), humanoCreado);

                    try {
                        enviadorDeMails.enviarMail(MailCredencial.of(credencial));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    private Humano registrarHumano(String nombre, String apellido, TipoDeDocumento tipoDoc, String numDoc, String mail) {

        Humano humanoCreado = Humano.builder()
                .nombre(nombre)
                .apellido(apellido)
                .tipoDeDocumento(tipoDoc)
                .numeroDocumento(numDoc)
                .fechaDeSolicitud(LocalDate.now())
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .puntos(0d)
                .build();

        Contacto contacto = Contacto.builder().
                                tipoDeContacto(TipoDeContacto.CORREO).
                                contacto(mail).
                                build();

        humanoCreado.agregarContacto(contacto);
        withTransaction(() -> {
            this.repositorioHumanos.guardar(humanoCreado);
        });
        return humanoCreado;
    }


    private void guardarColaboracionesSegunTipo(Humano humano, String formaDeContribucion, Double cantidad, String fecha) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaCasteada = LocalDate.parse(fecha, formato);

        switch (formaDeContribucion) {
            case "DINERO":
                guardarDonacionDeDinero(humano, cantidad, fechaCasteada);
                break;

            case "REDISTRIBUCION_VIANDAS":
                guardarDistribucionDeViandas(humano, cantidad, fechaCasteada);
                break;

            case "DONACION_VIANDAS":
                guardarDonacionDeViandas(humano, cantidad, fechaCasteada);
                break;

            case "ENTREGA_TARJETAS":
                guardarEntregaDeTarjetas(humano, cantidad, fechaCasteada);
                break;
        }
    }

    private void guardarDonacionDeDinero(Humano humano, Double cantidad, LocalDate fecha) {
        DonacionDeDinero donacionDeDinero = DonacionDeDinero.builder()
                .fecha(fecha)
                .humano(humano)
                .frecuenciaDonacion(FrecuenciaDonacion.UNICA_VEZ)
                .monto(cantidad)
                .build();
        humano.sumarPuntos(calculadorPesosDonados.calcularPuntos(donacionDeDinero.getMonto()));
        withTransaction(() -> this.repositorioDonacionesDeDinero.guardar(donacionDeDinero));
    }

    private void guardarDistribucionDeViandas(Humano humano, Double cantidad, LocalDate fecha) {
        DistribucionDeVianda distribucionDeVianda = DistribucionDeVianda.builder()
                .fecha(fecha)
                .colaborador(humano)
                .cantidadDeViandas(cantidad.intValue())
                .build();
        humano.sumarPuntos(calculadorViandasDistribuidas.calcularPuntos(distribucionDeVianda));
        withTransaction(() -> this.repositorioDistribucionesDeViandas.guardar(distribucionDeVianda));
    }

    private void guardarDonacionDeViandas(Humano humano, Double cantidad, LocalDate fecha) {
        DonacionDeVianda donacionDeVianda = DonacionDeVianda.builder()
                .fecha(fecha)
                .colaborador(humano)
                .build();

        Vianda viandaAntigua = Vianda.builder().build();
        for (int i = 0; i < cantidad; i++) {
            donacionDeVianda.agregarViandas(viandaAntigua);
            humano.sumarPuntos(calculadorViandasDonadas.calcularPuntos(donacionDeVianda));
        }

        withTransaction(() -> this.repositorioDonacionesDeViandas.guardar(donacionDeVianda));
    }

    private void guardarEntregaDeTarjetas(Humano humano, Double cantidad, LocalDate fecha) {
        withTransaction(() -> {
            for (int i = 0; i < cantidad; i++) {
                RegistroPersonasVulnerables registro = RegistroPersonasVulnerables.builder()
                        .fecha(fecha)
                        .colaborador(humano)
                        .build();
                this.repositorioRegistrosPersonasVulnerables.guardar(registro);
                humano.sumarPuntos(calculadorTarjetasDistribuidas.calcularPuntos(registro));
            }
        });
    }

}





