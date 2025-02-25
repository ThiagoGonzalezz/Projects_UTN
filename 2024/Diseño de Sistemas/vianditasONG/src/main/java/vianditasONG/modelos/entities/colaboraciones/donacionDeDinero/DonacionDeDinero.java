package vianditasONG.modelos.entities.colaboraciones.donacionDeDinero;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import javax.persistence.*;


@Entity
@Table(name = "donacion_de_dinero")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonacionDeDinero extends Persistente {

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "monto", nullable = false, columnDefinition = "DOUBLE")
    private Double monto;

    @Column(name = "frecuencia_en_dias", nullable = false, columnDefinition = "INTEGER")
    @Builder.Default
    private FrecuenciaDonacion frecuenciaDonacion = FrecuenciaDonacion.UNICA_VEZ;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_juridica_id", columnDefinition = "BIGINT")
    private PersonaJuridica personaJuridica;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "humano_id",columnDefinition = "BIGINT")

    private Humano humano;

    public Double pesosDonados() {
        if (this.frecuenciaDonacion.getFrecuenciaEnDias() == 0) {
            return monto;
        }

        LocalDate fechaActual = LocalDate.now();
        long diasTranscurridos = ChronoUnit.DAYS.between(this.fecha, fechaActual);
        Double totalDonado = (int) (diasTranscurridos / this.frecuenciaDonacion.getFrecuenciaEnDias()) * this.monto;
        return totalDonado + this.monto;
    }

}
