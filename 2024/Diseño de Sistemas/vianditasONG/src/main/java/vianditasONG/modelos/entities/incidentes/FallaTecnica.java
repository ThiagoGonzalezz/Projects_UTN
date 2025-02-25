package vianditasONG.modelos.entities.incidentes;

import lombok.*;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.config.ServiceLocator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@DiscriminatorValue("FALLA_TECNICA")
public class FallaTecnica extends Incidente {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;
    @Column(name = "foto", columnDefinition = "VARCHAR(150)")
    private String foto;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reporte")
    private EstadoReporte estadoReporte;

    public static FallaTecnica of(Heladera heladera, String descripcion, Humano colaborador) {
        FallaTecnica falla = FallaTecnica.builder()
                .colaborador(colaborador)
                .build();

        falla.setDescripcion(descripcion);
        falla.setHeladeraAsociada(heladera);
        falla.setCalculadorDeTecnicoCercano(ServiceLocator.getService(CalculadorDeTecnicoCercano.class));

        return falla;
    }

    public static FallaTecnica of(Heladera heladera, String descripcion, Humano colaborador, String foto) {
        FallaTecnica falla = FallaTecnica.builder()
                .colaborador(colaborador)
                .foto(foto)
                .build();

        falla.setDescripcion(descripcion);
        falla.setHeladeraAsociada(heladera);

        return falla;
    }

    @Override
    public EstadoHeladera estadoHeladeraAsociado(){
        return EstadoHeladera.CON_FALLA_TECNICA;
    }
}

