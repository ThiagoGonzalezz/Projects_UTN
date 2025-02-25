package vianditasONG.testConcretos.subscripciones;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPunto;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.entities.heladeras.suscripciones.*;
import vianditasONG.utils.verificadorDeCercania.CalculadorDeDistancias;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class HumanoSubscripcion {

    private Humano humano;
    private Heladera heladera;
    private Contacto contactoEmail;
    private Contacto contactoTelefono;

    private Contacto contactoTelegram;

    @BeforeEach
    public void setUp() throws IOException {
        Localidad localidad = Localidad.builder().nombre("Villa Lugano").provincia(Provincia.BUENOS_AIRES).build();
        Direccion direccion = Direccion.builder().altura("2300").calle("Mozart").localidad(localidad).build();
        PuntoGeografico puntoGeografico = PuntoGeografico.builder().longitud(-58.46799).latitud(-34.659546).build();

        humano = Humano.builder().direccion(direccion).build();

        contactoEmail = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("bautistatorre9@gmail.com").build();
        contactoTelefono = Contacto.builder().tipoDeContacto(TipoDeContacto.WHATSAPP).contacto("1169798369").build();
        contactoTelegram = Contacto.builder().tipoDeContacto(TipoDeContacto.WHATSAPP).contacto("eljincho").build();
        humano.agregarContacto(contactoEmail, contactoTelefono, contactoTelegram);


        PuntoEstrategico puntoEstrategico = PuntoEstrategico.builder().direccion(direccion).puntoGeografico(puntoGeografico).build();
        heladera = Heladera.builder().puntoEstrategico(puntoEstrategico).build();

        VerificadorDeCercania verificadorDeCercania = VerificadorDeCercania.builder().build();
        CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();
        verificadorDeCercania.setCalculadorDeDistancias(calculadorDeDistancias);
        humano.setVerificadorDeCercania(verificadorDeCercania);
        ConversorDirecAPunto conversorDirecAPunto = ConversorDirecAPunto.builder().build();

        ConversorDirecAPuntoAdapter conversorDirecAPuntoAdapterMockeado = mock(ConversorDirecAPuntoAdapter.class);
        when(conversorDirecAPuntoAdapterMockeado.obtenerPuntoGeografico(direccion)).thenReturn(puntoGeografico);
        conversorDirecAPunto.setAdapterConversorDirecAPunto(conversorDirecAPuntoAdapterMockeado);
        humano.setConversorDirecAPunto(conversorDirecAPunto);
        humano.setearPuntoGeografico();
    }

    @Test
    @DisplayName("Suscripción a desperfectos por email")
    public void testSuscribirseDesperfectosPorEmail() {
        //humano.suscribirse(heladera, TipoSuscripcion.DESPERFECTOS, 0, contactoEmail);
        assertFalse(heladera.getSuscripcionesDesperfectos().isEmpty());

        SuscripcionDesperfectos suscripcion = heladera.getSuscripcionesDesperfectos().get(0);
        Assertions.assertEquals(humano, suscripcion.getColaborador());
        Assertions.assertEquals(contactoEmail, suscripcion.getContacto());
        assertTrue(suscripcion.getMedioDeAviso() instanceof NotificadorDeEmail);
    }


    @Test
    @DisplayName("Suscripción a cantidad máxima de viandas por Telegram")
    public void testSuscribirseCantViandasMaxPorTelegram() {
        //humano.suscribirse(heladera, TipoSuscripcion.CANTVIANDASMAX, 10, contactoTelegram);

        assertFalse(heladera.getSuscripcionesCantViandasMax().isEmpty());
        SuscripcionCantViandasMax suscripcion = heladera.getSuscripcionesCantViandasMax().get(0);
        Assertions.assertEquals(humano, suscripcion.getColaborador());
        //assertEquals(contactoTelefono, suscripcion.getContacto());
        assertTrue(suscripcion.getMedioDeAviso() instanceof NotificadorDeTelegram);
    }

    @Test
    @DisplayName("Suscripción con teléfono lanza error")
    public void testSuscribirseConTelefonoLanzaError() {
        //humano.suscribirse(heladera, TipoSuscripcion.DESPERFECTOS, 0, contactoTelefono);

        assertTrue(heladera.getSuscripcionesDesperfectos().isEmpty());
    }
}
