package vianditasONG.modelos.entities.colaboraciones;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registro_personas_vulnerables")
@Builder
public class RegistroPersonasVulnerables extends Persistente {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="personaVulnerable_id", columnDefinition = "BIGINT")
    private PersonaEnSituacionVulnerable personaVulnerable;
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;
}
