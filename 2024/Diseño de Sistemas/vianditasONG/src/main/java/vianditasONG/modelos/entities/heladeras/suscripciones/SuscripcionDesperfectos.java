package vianditasONG.modelos.entities.heladeras.suscripciones;

import vianditasONG.converters.dbconverters.MedioDeAvisoConverter;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suscripcion_desperfectos")
public class SuscripcionDesperfectos extends Persistente {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="contacto_id", columnDefinition = "BIGINT")
    private Contacto contacto;
    @Convert(converter = MedioDeAvisoConverter.class)
    @Column(name = "medioDeNotificacion")
    private MedioDeAviso medioDeAviso;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladera_id", columnDefinition = "BIGINT")
    private Heladera heladera;

    public void serNotificadoPor(Heladera heladera) throws IOException {
        NotificacionDesperfectos notificacion = NotificacionDesperfectos.of(heladera);
        medioDeAviso.notificar(contacto, notificacion);
    }
}
