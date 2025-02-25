package vianditasONG.modelos.entities.incidentes;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.config.ServiceLocator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ALERTA")
public class Alerta extends Incidente{
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDeAlerta")
    private TipoDeAlerta tipoDeAlerta;
    @Column(name = "foto", columnDefinition = "VARCHAR(150)")
    private String foto;


    public static Alerta of(TipoDeAlerta tipo, Heladera heladera) {
        Alerta alerta = Alerta.builder()
                .tipoDeAlerta(tipo)
                .build();

        alerta.setDescripcion(Config.getInstancia().obtenerDelConfig(tipo.name() + "_Descripcion"));
        alerta.setHeladeraAsociada(heladera);
        alerta.setCalculadorDeTecnicoCercano(ServiceLocator.getService(CalculadorDeTecnicoCercano.class));

        return alerta;
    }


    @Override
    public EstadoHeladera estadoHeladeraAsociado(){
        return switch (this.tipoDeAlerta) {
            case TEMP_FUERA_DE_RANGO -> EstadoHeladera.TEMPERATURA_INADECUADA;
            case INTENTO_FRAUDE -> EstadoHeladera.BLOQUEADA_POR_FRAUDE;
            case DESCONEXION -> EstadoHeladera.DESCONECTADA;
            default -> throw new IllegalStateException("Tipo de alerta desconocido: " + this.tipoDeAlerta);
        };
    }
}
