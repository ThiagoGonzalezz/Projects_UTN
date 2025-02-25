package vianditasONG.modelos.entities.heladeras.sensores;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;

@Setter
@Entity
@Table(name = "sensor_movimiento")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDeMovimiento extends Persistente {

    @ManyToOne
    @JoinColumn(name = "heladera_id", columnDefinition= "BIGINT")
    private Heladera heladera;

    public Boolean recibirAlerta() throws IOException {

        System.out.println("Alerta de movimiento");
        System.out.println("Heladera ID:       " + this.heladera.getId());

        return Boolean.TRUE;
    }

}
