package vianditasONG.modelos.entities.colaboraciones.donacionDeViandas;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vianda")
public class Vianda extends Persistente {

    @Column(name = "comida", columnDefinition = "VARCHAR(70)")
    private String comida;
    @Column(name = "fechaDeCaducidad", columnDefinition = "DATE")
    private LocalDate fechaDeCaducidad;
    @Column(name = "fechaDeDonacion", columnDefinition = "TIME")
    private LocalDateTime fechaDeDonacion;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladera_id", columnDefinition = "BIGINT")
    private Heladera heladeraOrigen;
    @Column(name = "entregada", columnDefinition = "BIT")
    private Boolean entregada;
    @Column(name = "calorias", columnDefinition = "INTEGER(5)")
    private Integer calorias;
    @Column(name = "pesoEnGramos", columnDefinition = "DECIMAL(12,2)")
    private Double peso;

}
