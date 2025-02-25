package vianditasONG.modelos.servicios.seguridad.distribuidorDeTarjetas;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;

import java.util.Random;

@Builder
public class DistribudorDeTarjetas {

    public TarjetaColaborador asignarTarjetaColaboradorNueva(Humano humano){
        //toDo: Integrar con sistema de tarjetas
        long min = 1_000_000_000_000L;  // Número mínimo de 18 dígitos
        long max = 9_999_999_999_999L;  // Número máximo de 18 dígitos
        Random random = new Random();
        Long codigo =  min + (long)(random.nextDouble() * (max - min));

        return TarjetaColaborador.builder()
                .codigo(codigo.toString())
                .colaboradorAsociado(humano).
                build();
    }
}
