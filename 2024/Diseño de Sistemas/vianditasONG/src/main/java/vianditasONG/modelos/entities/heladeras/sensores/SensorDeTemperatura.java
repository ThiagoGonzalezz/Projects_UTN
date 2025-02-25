package vianditasONG.modelos.entities.heladeras.sensores;

import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.util.Optional;

@Entity
@Table(name = "sensor_temperatura")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDeTemperatura extends Persistente {


    @ManyToOne
    @JoinColumn(name = "heladera_id", columnDefinition="BIGINT")
    private Heladera heladera;

    public Boolean tieneTemperaturaAdecuada(Float temperatura) {
        Modelo modeloHeladera = heladera.getModeloHeladera();
        return temperatura <= modeloHeladera.getTempMaximaEnGradosCelsius()
                && temperatura >= modeloHeladera.getTempMinimaEnGradosCelsius();
    }


}

