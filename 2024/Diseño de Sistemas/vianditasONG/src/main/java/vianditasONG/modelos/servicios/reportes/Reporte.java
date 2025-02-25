package vianditasONG.modelos.servicios.reportes;

import lombok.*;
import vianditasONG.utils.Persistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reporte")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reporte extends Persistente {
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoReporte tipoReporte;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ruta_pdf")
    private String rutaPdf;

    @ElementCollection
    @CollectionTable(name = "reporte_detalle", joinColumns = @JoinColumn(name = "reporte_id"))
    @Column(name = "detalle")
    private List<String> detalles = new ArrayList<>();


}
