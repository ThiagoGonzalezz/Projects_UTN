package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas;
import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import lombok.Setter;

@Builder
@Setter
public class CalculadorViandasDonadas implements ICalculadorViandasDonadas {
    @Builder.Default
    private double coeficiente = 1.5d;

    public double calcularPuntos(DonacionDeVianda donacionDeVianda){
        return donacionDeVianda.cantViandas()*coeficiente;
    }
}
