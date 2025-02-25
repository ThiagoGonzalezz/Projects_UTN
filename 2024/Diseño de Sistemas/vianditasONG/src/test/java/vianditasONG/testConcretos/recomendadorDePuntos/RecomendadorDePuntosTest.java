package vianditasONG.testConcretos.recomendadorDePuntos;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import vianditasONG.modelos.entities.datosGenerales.AreaDeBusqueda;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.servicios.recomendadorDePuntos.PuntosRecomendadosAdapter;
import vianditasONG.modelos.servicios.recomendadorDePuntos.RecomendadorDePuntosIdeales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecomendadorDePuntosTest {

    RecomendadorDePuntosIdeales recomendadorDePuntosIdeales = new RecomendadorDePuntosIdeales();
    List<PuntoEstrategico> puntosRecomendados = new ArrayList<>();


    @Test
    public void solicitudDePuntosEstrategicos() throws IOException {
        PuntoGeografico puntoGeografico = PuntoGeografico.builder().longitud(12.345678).latitud(23.456789).build();
        AreaDeBusqueda areaDeBusqueda = new AreaDeBusqueda(puntoGeografico, 10);

        PuntosRecomendadosAdapter puntosRecomendadosAdapterMockeado = mock(PuntosRecomendadosAdapter.class);
        when(puntosRecomendadosAdapterMockeado.recomendarPuntos(areaDeBusqueda)).thenReturn(puntosRecomendados);

        PuntoGeografico puntoGeograficoRecomendado1 = PuntoGeografico.builder().longitud(21.345678).latitud(32.456789).build();
        PuntoGeografico puntoGeograficoRecomendado2 = PuntoGeografico.builder().longitud(23.345678).latitud(43.456789).build();
        PuntoEstrategico puntoEstrategicoRecomendado1 = PuntoEstrategico.builder().puntoGeografico(puntoGeograficoRecomendado1).build();
        PuntoEstrategico puntoEstrategicoRecomendado2 = PuntoEstrategico.builder().puntoGeografico(puntoGeograficoRecomendado2).build();

        puntosRecomendados.add(puntoEstrategicoRecomendado1);
        puntosRecomendados.add(puntoEstrategicoRecomendado2);

        recomendadorDePuntosIdeales.setAdapterPuntosRecomendados(puntosRecomendadosAdapterMockeado);

        List<PuntoEstrategico> puntosResultantes = recomendadorDePuntosIdeales.recomendarPuntos(areaDeBusqueda);


        Assertions.assertEquals("32.456789", String.valueOf(puntosResultantes.get(0).getPuntoGeografico().getLatitud()));
        Assertions.assertEquals("21.345678", String.valueOf(puntosResultantes.get(0).getPuntoGeografico().getLongitud()));

        Assertions.assertEquals("43.456789", String.valueOf(puntosResultantes.get(1).getPuntoGeografico().getLatitud()));
        Assertions.assertEquals("23.345678", String.valueOf(puntosResultantes.get(1).getPuntoGeografico().getLongitud()));
    }

}
