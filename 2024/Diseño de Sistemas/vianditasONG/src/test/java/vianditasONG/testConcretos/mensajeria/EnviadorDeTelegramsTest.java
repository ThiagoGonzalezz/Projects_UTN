package vianditasONG.testConcretos.mensajeria;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.entities.heladeras.suscripciones.NotificacionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.NotificacionSuscripcionExitosa;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.entities.heladeras.suscripciones.TipoSuscripcion;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPunto;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.utils.verificadorDeCercania.CalculadorDeDistancias;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EnviadorDeTelegramsTest {
    private Humano suscriptor;
    private Heladera heladeraAnotificar;
    private Contacto contactoEmail;
    private Contacto contactoTelefono;

    private Contacto contactoTelegram;

    @BeforeEach
    public void setUp() throws IOException {
        Localidad localidad = Localidad.builder().nombre("Villa Lugano").provincia(Provincia.BUENOS_AIRES).build();
        Direccion direccion = Direccion.builder().altura("2300").calle("Mozart").localidad(localidad).build();
        PuntoGeografico puntoGeografico = PuntoGeografico.builder().longitud(-58.46799).latitud(-34.659546).build();

        suscriptor = Humano.builder().direccion(direccion).build();
        contactoEmail = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("bautistatorre9@gmail.com").build();
        contactoTelefono = Contacto.builder().tipoDeContacto(TipoDeContacto.WHATSAPP).contacto("1169798369").build();
        contactoTelegram = Contacto.builder().tipoDeContacto(TipoDeContacto.TELEGRAM).contacto("eljincho").build();
        suscriptor.agregarContacto(contactoEmail, contactoTelefono, contactoTelegram);


        PuntoEstrategico puntoEstrategico = PuntoEstrategico.builder().direccion(direccion).puntoGeografico(puntoGeografico).build();
        heladeraAnotificar = Heladera.builder().
                puntoEstrategico(puntoEstrategico).
                cantidadViandas(22).
                build();

        VerificadorDeCercania verificadorDeCercania = VerificadorDeCercania.builder().build();
        CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();
        verificadorDeCercania.setCalculadorDeDistancias(calculadorDeDistancias);
        suscriptor.setVerificadorDeCercania(verificadorDeCercania);
        ConversorDirecAPunto conversorDirecAPunto = ConversorDirecAPunto.builder().build();

        ConversorDirecAPuntoAdapter conversorDirecAPuntoAdapterMockeado = mock(ConversorDirecAPuntoAdapter.class);
        when(conversorDirecAPuntoAdapterMockeado.obtenerPuntoGeografico(direccion)).thenReturn(puntoGeografico);
        conversorDirecAPunto.setAdapterConversorDirecAPunto(conversorDirecAPuntoAdapterMockeado);
        suscriptor.setConversorDirecAPunto(conversorDirecAPunto);
        suscriptor.setearPuntoGeografico();
    }

    @Test
    public void testEnviarMensajeCantidadDeViandas() throws IOException {
        String botToken = "7069685840:AAG8V9tfW0auC_LryUB_OCvcA2OhC4P_4Y4";
        String chatId = "1257345730";

        Contacto contacto = new Contacto(TipoDeContacto.TELEGRAM,chatId);
        NotificadorDeTelegram notificadorDeTelegram = ServiceLocator.getService(NotificadorDeTelegram.class);


        NotificacionSuscripcionExitosa notificacion = NotificacionSuscripcionExitosa.builder()
                                                    .titulo("prueba")
                                                    .mensaje("mensaje")
                                                    .build();


        notificadorDeTelegram.notificar(contacto, notificacion);


    }
}

