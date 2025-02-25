package vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios;

import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Puntuable;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "oferta")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Oferta extends Persistente {
     @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
     @JoinColumn(name = "rubroOferta_id")
     private RubroOferta rubro;

     @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
     private String nombre;

     @Column(name = "puntosNecesarios")
     private Double puntosNecesarios;

     @Column(name = "imagen", columnDefinition = "VARCHAR(150)")
     private String imagen;

     @Column(name = "fechaYHora")
     private LocalDateTime fechaYHora;

     @Builder.Default
     @Column(name = "fueCanjeada")
     private Boolean fueCanjeada = false;

     @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
     @JoinColumn(name = "personaJuridica_id")
     private PersonaJuridica ofertante;

     public Boolean puedeSerCanjeadaPor(Puntuable puntuable) {

          return puntuable.getPuntos() >= puntosNecesarios;
     }

}
