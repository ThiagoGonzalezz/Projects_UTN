package vianditasONG.modelos.entities.personaVulnerable;

import lombok.*;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona_en_situacion_vulnerable")
public class PersonaEnSituacionVulnerable extends Persistente {
    @Column(name="nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name="apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;
    @Column(name="fechaDeNacimiento", columnDefinition = "DATETIME")
    private LocalDate fechaDeNacimiento;
    @Column(name="fechaDeRegistro", columnDefinition = "DATE")
    private LocalDateTime fechaDeRegistro;
    @Column(name="poseeVivienda")
    private Boolean poseeVivienda;
    @Enumerated(value = EnumType.STRING)
    private TipoDeDocumento tipoDeDocumento;
    @Column(name="numeroDocumento", columnDefinition = "VARCHAR(20)")
    private String numeroDocumento;
    @Column(name="esAdulto")
    private Boolean esAdulto;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_respondido_id", columnDefinition = "BIGINT")
    private FormularioRespondido formularioRespondido;
    @Embedded
    private Direccion direccion;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "hijo_por_persona_vulnerable",
            joinColumns = @JoinColumn(name = "personaVulnerable_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hijo_id", referencedColumnName = "id")
    )
    private List<PersonaEnSituacionVulnerable> hijos = new ArrayList<>();

    public void agregarHijos(PersonaEnSituacionVulnerable ... personasEnSituacionVulnerable){
        Collections.addAll(this.hijos, personasEnSituacionVulnerable);
    }

    public List<PersonaEnSituacionVulnerable> hijosMenores(){
        return this.hijos
                .stream()
                .filter(h->!h.getEsAdulto())
                .toList();
    }
}
