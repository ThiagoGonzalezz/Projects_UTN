package vianditasONG.modelos.entities.incidentes;

import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.TecnicoBot;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.NotificacionIncidente;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.modelos.entities.tecnicos.ExcepcionTecnicoNoDisponible;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.config.ServiceLocator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo")
public abstract class Incidente extends Persistente {

    @Column(name = "fechaYHora", columnDefinition = "DATETIME")
    protected LocalDateTime fechaYHora;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="heladera_id", columnDefinition = "BIGINT")
    protected Heladera heladeraAsociada;
    @Column(name="descripcion", columnDefinition = "TEXT")
    protected String descripcion;
    @Column(name = "foto_resolucion", columnDefinition = "TEXT")
    protected String fotoResolucion;
    @Column(name = "fecha_resolucion", columnDefinition = "DATETIME")
    protected LocalDateTime fechaResolucion;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id", columnDefinition = "BIGINT")
    protected Tecnico tecnicoResolutor;
    @Transient
    protected CalculadorDeTecnicoCercano calculadorDeTecnicoCercano = ServiceLocator.getService(CalculadorDeTecnicoCercano.class);
    @Transient
    protected TecnicoBot tecnicoBot = ServiceLocator.getService(TecnicoBot.class);
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reporte")
    private EstadoReporte estadoReporte;

    public String nombreHeladeraAsociada() {
        return this.heladeraAsociada.nombre();
    }

    public String direccionAsociada() {
        return this.heladeraAsociada.direccionCompleta();
    }

    public abstract EstadoHeladera estadoHeladeraAsociado();




    @PostLoad
    public void asignarCalculadorDeTecnicoCercano() {
        this.calculadorDeTecnicoCercano = ServiceLocator.getService(CalculadorDeTecnicoCercano.class);
        this.tecnicoBot = ServiceLocator.getService(TecnicoBot.class);
    }

    public void notificarse(){
        try {
            Tecnico tecnicoCercano = calculadorDeTecnicoCercano.obtenerTecnicoMasCercano(this.getHeladeraAsociada());
            NotificacionIncidente notificacion = NotificacionIncidente.of(this);
            tecnicoCercano.getMedioDeAviso().notificar(tecnicoCercano.getContactos().get(0), notificacion);
            tecnicoCercano.setDisponible(false);
            tecnicoCercano.agregarIncidente(this);
        } catch (ExcepcionTecnicoNoDisponible | IOException e) {}
    }

}
