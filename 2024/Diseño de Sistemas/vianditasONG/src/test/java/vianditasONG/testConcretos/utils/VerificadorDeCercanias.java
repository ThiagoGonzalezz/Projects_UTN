package vianditasONG.testConcretos.utils;

import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.utils.verificadorDeCercania.CalculadorDeDistancias;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VerificadorDeCercanias {

    VerificadorDeCercania verificadorDeCercania = VerificadorDeCercania.builder().build();
    CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();
    PuntoGeografico puntoGeografico1;
    PuntoGeografico puntoGeografico2;
    PuntoGeografico puntoGeografico3;

    @BeforeEach
    public void inicializar(){
        puntoGeografico1 = PuntoGeografico.builder().latitud(-34.5986333).longitud(-58.4199428).build();
        puntoGeografico2 = PuntoGeografico.builder().latitud(-34.603348).longitud(-58.420427).build();
        puntoGeografico3 = PuntoGeografico.builder().latitud(-34.659528).longitud(-58.467983).build();
        verificadorDeCercania.setCalculadorDeDistancias(calculadorDeDistancias);
    }
    @Test
    public void dosPuntosEstanCerca(){
        Assertions.assertTrue(verificadorDeCercania.estanCerca(puntoGeografico1, puntoGeografico2));
    }
    @Test
    public void dosPuntosNoEstanCerca(){
        Assertions.assertFalse(verificadorDeCercania.estanCerca(puntoGeografico1, puntoGeografico3));
    }
}
