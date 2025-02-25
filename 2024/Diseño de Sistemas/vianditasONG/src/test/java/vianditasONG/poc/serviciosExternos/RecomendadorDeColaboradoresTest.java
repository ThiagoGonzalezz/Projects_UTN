package vianditasONG.poc.serviciosExternos;

import org.junit.Test;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradores;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradoresAdapter;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradoresAdapterAPI;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.RecomendadorDeColaboradoresServicio;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.HumanoRecomendado;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.MedioDeConctactoRecomendado;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RecomendadorDeColaboradoresTest {

    @Test
    public void recomendarListadoDeColaboradores() throws IOException {

        RecomendadorDeColaboradores recomendadorDeColaboradores = RecomendadorDeColaboradores.builder().build();

        Humano humanoARecomendar1 = Humano.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("12345678")
                .fechaDeSolicitud(LocalDate.now())
                .contactos(List.of(Contacto.builder()
                        .contacto("juan@gmail.com")
                        .tipoDeContacto(TipoDeContacto.CORREO).build()))
                .puntos(10.0)
                .build();

        humanoARecomendar1.setId(530l);

        Humano humanoARecomendar2 = Humano.builder()
                .nombre("Felipe")
                .apellido("Rodriguez")
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("32412323")
                .fechaDeSolicitud(LocalDate.now())
                .contactos(List.of(Contacto.builder()
                        .contacto("juan@gmail.com")
                        .tipoDeContacto(TipoDeContacto.CORREO).build()))
                .puntos(10.0)
                .build();

        humanoARecomendar2.setId(531l);

        Humano humanoARecomendar3 = Humano.builder()
                .nombre("Pedro")
                .apellido("Ramirez")
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("4732823")
                .fechaDeSolicitud(LocalDate.now())
                .contactos(List.of(Contacto.builder()
                        .contacto("pedro@gmail.com")
                        .tipoDeContacto(TipoDeContacto.CORREO).build()))
                .puntos(12.0)
                .build();

        humanoARecomendar3.setId(532l);


        recomendadorDeColaboradores.recomendarColaboradores(List.of(humanoARecomendar1, humanoARecomendar2, humanoARecomendar3));

    }

}
