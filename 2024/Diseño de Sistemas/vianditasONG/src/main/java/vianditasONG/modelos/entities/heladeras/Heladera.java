package vianditasONG.modelos.entities.heladeras;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "heladera")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heladera extends Persistente{

    @Column(name = "nombre_heladera", nullable = false, columnDefinition= "TEXT")
    private String nombre;

    @Embedded
    private PuntoEstrategico puntoEstrategico;

    @Column(name = "cantidad_viandas", columnDefinition= "INTEGER")
    private Integer cantidadViandas;

    @Column(name = "fecha_registro", columnDefinition="DATETIME")
    private LocalDateTime fechaDeRegistro;

    @Enumerated
    @Column(name = "estado_heladera")
    private EstadoHeladera estadoHeladera;

    @ManyToOne
    @JoinColumn(name = "modelo_id", columnDefinition="BIGINT")
    private Modelo modeloHeladera;

    @Column(name = "dias_desactivada", columnDefinition="INTEGER")
    private Integer diasDesactivada;

    @Column(name = "fecha_ultima_desactivacion", columnDefinition="DATETIME")
    private LocalDateTime fechaUltimaDesactivacion;

    @Column(name = "temperatura_deseada", columnDefinition="FLOAT")
    private Float temperaturaDeseadaEnGradosCelsius;

    @Column(name = "ultima_temperatura_registrada", columnDefinition="FLOAT")
    private Float ultimaTemperaturaRegistradaEnGradosCelsius;

    @Column(name = "fecha_ultima_temperatura_registrada", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaUltimatemperaturaRegistrada;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id", columnDefinition="BIGINT")
    private List<VisitaHeladera> visitasHeladera;

    @ManyToMany 
    @JoinTable(
            name = "heladera_cercana",
            joinColumns = @JoinColumn(name = "heladera_id"),
            inverseJoinColumns = @JoinColumn(name = "heladera_cercana_id")
    )
    private List<Heladera> heladerasCercanas;

    @Transient
    private CalculadorDeHeladerasCercanas calculadorDeHeladerasCercanas;

    //@Column(name = "dias_desactivada_ultimo_calculo")
    //private Integer diasDesactivadaDesdeElUltimoCalculo;

    @Column(name = "meses_activa")
    private Integer mesesActiva;


    @Column(name = "fecha_ultimo_calculo")
    private LocalDateTime fechaUltimoCalculo;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)// Relación con Suscripciones
    @JoinColumn(name = "heladera_id", columnDefinition="BIGINT")
    private List<SuscripcionDesperfectos> suscripcionesDesperfectos = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id", columnDefinition="BIGINT")
    private List<SuscripcionCantViandasMin> suscripcionesCantViandasMin = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id", columnDefinition="BIGINT")
    private List<SuscripcionCantViandasMax> suscripcionesCantViandasMax = new ArrayList<>();




    public void agregarViandas(Integer cantidad) throws IOException {
        int nuevaCantidad = this.cantidadViandas + cantidad;
        if(nuevaCantidad > this.modeloHeladera.getCapacidadMaximaViandas()){
            throw new RuntimeException();
        }
        this.cantidadViandas = nuevaCantidad;
        notificarSuscriptoresDeCantidad();
    }

    public void quitarViandas(Integer cantidad) throws IOException {
        int nuevaCantidad = this.cantidadViandas - cantidad;

        if(nuevaCantidad<0){
            throw new RuntimeException();
        }
        this.cantidadViandas = nuevaCantidad;
        notificarSuscriptoresDeCantidad();
    }

    public Integer diasActivaSinCalcular() {
        if (this.estadoHeladera != EstadoHeladera.ACTIVA) {
            //diasDesactivadaDesdeElUltimoCalculo += duracionUltimaDesactivacion();
            this.fechaUltimaDesactivacion = LocalDateTime.now();
        }
        long duracionSinCalcular = ChronoUnit.DAYS.between(fechaUltimoCalculo.toLocalDate(), LocalDate.now());
        Integer resultado = (int) duracionSinCalcular;// - diasDesactivadaDesdeElUltimoCalculo;
        //this.diasDesactivadaDesdeElUltimoCalculo = 0;
        this.fechaUltimoCalculo = LocalDateTime.now();
        return resultado;
    }

    public void activarHeladera() {
        if (this.estadoHeladera != EstadoHeladera.ACTIVA) {
            estadoHeladera = EstadoHeladera.ACTIVA;
        //    diasDesactivadaDesdeElUltimoCalculo += duracionUltimaDesactivacion();
        }
    }

    public void desactivarHeladera(EstadoHeladera nuevoEstado) { //TODO: VERIFICAR QUE EN LOS CONTROLLERS SE USE ESTE METODO
        if (this.estadoHeladera == EstadoHeladera.ACTIVA) {
            estadoHeladera = nuevoEstado;
            fechaUltimaDesactivacion = LocalDateTime.now();
            mesesActiva = 0;
        }
    }

    public Integer duracionUltimaDesactivacion() {
        if (fechaUltimaDesactivacion == null) return 0; // Agregar una verificación para evitar errores.
        long diasDesactivada = ChronoUnit.DAYS.between(fechaUltimaDesactivacion.toLocalDate(), LocalDate.now());
        return (int) diasDesactivada;
    }

    public void actualizarTemperatura(Float nuevaTemperatura) {
        this.ultimaTemperaturaRegistradaEnGradosCelsius = nuevaTemperatura;
    }

    public boolean getEstaActiva() {
        return this.estadoHeladera == EstadoHeladera.ACTIVA;
    }

    public void setEstaActiva(boolean b) {
        this.estadoHeladera = EstadoHeladera.ACTIVA;
    }

    public void agregarseEnLaListaDeOtrasHeladerasCercanas() { //esto debería ejecutarse cuando se crea la heladera
        heladerasCercanas.forEach(h -> h.agregarHeladeraCercana(this));
    }

    public void agregarHeladeraCercana(Heladera heladera) {
        if (!this.heladerasCercanas.contains(heladera)) {
            this.heladerasCercanas.add(heladera);
        }
    }

    public void setearHeladerasCercanas() {
        this.heladerasCercanas = calculadorDeHeladerasCercanas.obtenerHeladerasCercanas(this);
    }

    public String direccionCompleta() {
        return this.puntoEstrategico.getDireccion().direccionCompleta();
    }

    public String nombre() {
        return this.puntoEstrategico.getNombre();
    }

    public void registarIncidente(Incidente incidente) throws IOException {
        this.estadoHeladera = incidente.estadoHeladeraAsociado();
        this.notificarSuscriptoresDeDesperfectos();
        incidente.notificarse();
    }

    public void agregarSuscripcionDesperfectos(SuscripcionDesperfectos suscripcion) {
        this.suscripcionesDesperfectos.add(suscripcion);
    }

    public void anularSuscripcionDesperfectos(Long id) {
        suscripcionesDesperfectos.removeIf(suscripcion -> suscripcion.getId().equals(id));
    }

    public void agregarSuscripcionCantViandasMin(SuscripcionCantViandasMin suscripcion) {
        this.suscripcionesCantViandasMin.add(suscripcion);
    }

    public void anularSuscripcionCantViandasMin(Long id) {
        suscripcionesCantViandasMin.removeIf(suscripcion -> suscripcion.getId().equals(id));
    }

    public void agregarSuscripcionCantViandasMax(SuscripcionCantViandasMax suscripcion) {
        this.suscripcionesCantViandasMax.add(suscripcion);
    }

    public void anularSuscripcionCantViandasMax(Long id) {
        suscripcionesCantViandasMax.removeIf(suscripcion -> suscripcion.getId().equals(id));
    }


    //PARA CRONJOB
    public void revisarEstado() {
        if (this.estadoHeladera == EstadoHeladera.ACTIVA) {
            LocalDateTime ahora = LocalDateTime.now();
            if (Duration.between(this.fechaUltimatemperaturaRegistrada, ahora).toMinutes() > 5) {
                this.estadoHeladera = EstadoHeladera.DESCONECTADA;
                this.fechaUltimaDesactivacion = ahora;
                Alerta.of(TipoDeAlerta.DESCONEXION, this).notificarse();
            }
        }
    }

    public void notificarSuscriptoresDeCantidad() throws IOException {

        for (SuscripcionCantViandasMin suscripcion : this.suscripcionesCantViandasMin) {
            if (this.cantidadViandas <= suscripcion.getCantViandasMin()) {
                suscripcion.serNotificadoPor(this);
            }
        }


        for (SuscripcionCantViandasMax suscripcion : this.suscripcionesCantViandasMax) {
            if (this.cantidadViandas >= suscripcion.getCantViandasMax()) {
                suscripcion.serNotificadoPor(this);
            }
        }

    }

    public void registrarVisita(VisitaHeladera visita) {
        this.visitasHeladera.add(visita);

        if (visita.getSolucionoElIncidente()) {
            this.activarHeladera();
        }
    }


    public void notificarSuscriptoresDeDesperfectos() throws IOException {
        if (this.estadoHeladera != EstadoHeladera.ACTIVA) {
            for (SuscripcionDesperfectos suscripcion : this.suscripcionesDesperfectos) {
                suscripcion.serNotificadoPor(this);
            }
        }
    }

    public Integer cantViandasRestantes(){
        return this.modeloHeladera.getCapacidadMaximaViandas() - this.cantidadViandas;
    }

    public void incrementarMesActiva() {
        mesesActiva = mesesActiva +1;
    }
}