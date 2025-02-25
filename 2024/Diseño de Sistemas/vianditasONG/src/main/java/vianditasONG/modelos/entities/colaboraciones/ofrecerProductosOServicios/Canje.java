package vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.Puntuable;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "canje")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Canje {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "fechaYHora", columnDefinition = "DATETIME")
    private LocalDateTime fechaYHora;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ofertante_id")
    private PersonaJuridica ofertante;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "personaJuridica_id")
    private PersonaJuridica canjeadorPersonaJuridica;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "humano_id")
    private Humano canjeadorHumano;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="oferta_id")
    private Oferta oferta;

    @Column(name = "puntos")
    private Double puntos;

    public static Canje de(Oferta oferta, Puntuable puntuable) {
        Canje.CanjeBuilder canjeBuilder = Canje.builder()
                .fechaYHora(LocalDateTime.now())
                .ofertante(oferta.getOfertante())
                .puntos(oferta.getPuntosNecesarios())
                .oferta(oferta);

        if (puntuable instanceof PersonaJuridica) {
            canjeBuilder.canjeadorPersonaJuridica((PersonaJuridica) puntuable);
        } else if (puntuable instanceof Humano) {
            canjeBuilder.canjeadorHumano((Humano) puntuable);
        }

        return canjeBuilder.build();
    }
}
