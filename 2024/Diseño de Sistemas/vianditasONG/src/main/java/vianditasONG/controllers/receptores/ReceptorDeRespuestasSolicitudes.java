package vianditasONG.controllers.receptores;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AperturaFehaciente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.IntentoAperturaFallida;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.IAccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.IAperturasFallidasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.imp.AperturasFallidasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.IAperturasFehacientesRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.imp.AperturasFehacientesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasColaboradorRepositorio;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
public class ReceptorDeRespuestasSolicitudes implements IMqttMessageListener, WithSimplePersistenceUnit {

    @Builder.Default
    private ITarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = ServiceLocator.getService(TarjetasColaboradorRepositorio.class);
    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio = ServiceLocator.getService(AccionesSolicitadasRepositorio.class);
    @Builder.Default
    private IAperturasFehacientesRepositorio aperturasFehacientesRepositorio = ServiceLocator.getService(AperturasFehacientesRepositorio.class);
    @Builder.Default
    private IAperturasFallidasRepositorio aperturasFallidasRepositorio = ServiceLocator.getService(AperturasFallidasRepositorio.class);
    @Builder.Default
    private CalculadorViandasDistribuidas calculadorViandasDistribuidas = ServiceLocator.getService(CalculadorViandasDistribuidas.class);
    @Builder.Default
    private IDistribucionesDeViandasRepositorio distribucionesDeViandasRepositorio = ServiceLocator.getService(DistribucionesDeViandasRepositorio.class);
    @Builder.Default
    private IDonacionesDeViandasRepositorio donacionesDeViandasRepositorio = ServiceLocator.getService(DonacionesDeViandasRepositorio.class);
    @Builder.Default
    private CalculadorViandasDonadas calculadorViandasDonadas = ServiceLocator.getService(CalculadorViandasDonadas.class);

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        ReceptorDeRespuestasSolicitudes.MoldeMensajeRespuesta mensajeRespuesta = this.castear(mqttMessage.toString());
        TarjetaColaborador tarjetaColaborador = tarjetasColaboradorRepositorio.buscarPorCodigo(mensajeRespuesta.nroTarjeta).orElse(null);
        AccionSolicitada accionSolicitada = accionesSolicitadasRepositorio.buscar(Long.valueOf(mensajeRespuesta.idAccionSolicitada)).orElse(null);
        Heladera heladera = heladerasRepositorio.buscar(accionSolicitada.getHeladerasSolicitadas().stream().findFirst().get().getId()).orElse(null);

        if(tarjetaColaborador == null || heladera == null || accionSolicitada == null)
            return;

        if(mensajeRespuesta.aperturaFehaciente == Boolean.TRUE)
        {
            AperturaFehaciente aperturaFehaciente = AperturaFehaciente.builder()
                    .heladera(heladera)
                    .accionSolicitada(accionSolicitada)
                    .fechaYHora(LocalDateTime.now())
                    .build();

            tarjetaColaborador.registrarAperturaDeHeladera(aperturaFehaciente);

            Double puntosASumar = null;

            if(accionSolicitada.getFormaDeColaboracion() == FormaDeColaboracion.DONACION_VIANDAS)
            {
                Optional<DonacionDeVianda> donacionDeVianda = donacionesDeViandasRepositorio.buscarPorId(accionSolicitada.getColaboracionId());
                donacionDeVianda.get().setActivo(Boolean.TRUE);
                puntosASumar = calculadorViandasDonadas.calcularPuntos(donacionDeVianda.get());
                tarjetaColaborador.getColaboradorAsociado().sumarPuntos(puntosASumar);
            }else if(accionSolicitada.getFormaDeColaboracion() == FormaDeColaboracion.DISTRIBUCION_VIANDAS)
            {
                Optional<DistribucionDeVianda> distribucionDeVianda = distribucionesDeViandasRepositorio.buscarPorId(accionSolicitada.getColaboracionId());
                distribucionDeVianda.get().setActivo(Boolean.TRUE);
                puntosASumar = calculadorViandasDistribuidas.calcularPuntos(distribucionDeVianda.get());
                tarjetaColaborador.getColaboradorAsociado().sumarPuntos(puntosASumar);
            }

            withTransaction(() -> {
                aperturasFehacientesRepositorio.guardar(aperturaFehaciente);
                tarjetasColaboradorRepositorio.actualizar(tarjetaColaborador);
            });
        }
        else{

            IntentoAperturaFallida intentoAperturaFallida = IntentoAperturaFallida.builder()
                    .heladera(heladera)
                    .fechaYHora(LocalDateTime.now())
                    .build();

            tarjetaColaborador.registrarIntentoDeAperturaFallida(intentoAperturaFallida);

            withTransaction(() -> {
                aperturasFehacientesRepositorio.guardar(intentoAperturaFallida);
                tarjetasColaboradorRepositorio.actualizar(tarjetaColaborador);
            });
        }


    }

    private ReceptorDeRespuestasSolicitudes.MoldeMensajeRespuesta castear(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ReceptorDeRespuestasSolicitudes.MoldeMensajeRespuesta.class);
    }
    private class MoldeMensajeRespuesta{
        @Getter
        @Setter
        private Boolean aperturaFehaciente;
        @Getter
        @Setter
        private String nroTarjeta;
        @Getter
        @Setter
        private Integer idAccionSolicitada;
    }

}
