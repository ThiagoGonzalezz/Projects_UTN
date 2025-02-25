package vianditasONG.modelos.entities.colaboradores;

import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPunto;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.entities.heladeras.suscripciones.TipoSuscripcion;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotTelegramAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWPP;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeWhatsapp;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Entity
@Table(name = "humano")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Humano extends Persistente implements Puntuable{

    @Column(name = "nombre",nullable = false, columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", nullable = false, columnDefinition = "VARCHAR(50)")
    private String apellido;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", columnDefinition = "BIGINT")
    private Usuario usuario;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_respondido_id", columnDefinition = "BIGINT")
    private FormularioRespondido formularioRespondido;

    @Enumerated(value = EnumType.STRING)
    private EstadoDeSolicitud estadoDeSolicitud;

    @Column(name = "fecha_de_nacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Embedded 
    private Direccion direccion;

    @Enumerated(value = EnumType.STRING)
    private TipoDeDocumento tipoDeDocumento;

    @Column(name = "numero_doc", columnDefinition = "INTEGER(15)")
    private String numeroDocumento;

    @Column(name = "fecha_solicitud", columnDefinition = "DATE")
    private LocalDate fechaDeSolicitud;

    @Column(name = "fecha_solicitud_respondida", columnDefinition = "DATETIME")
    private LocalDateTime fechaDeSolicitudRespondida;

    @Column(name = "puntos_acumulados", nullable = false, columnDefinition = "DOUBLE")
    @Builder.Default
    private Double puntos = Double.valueOf(Config.getInstancia().obtenerDelConfig("cantPuntosInicialesColaborador"));

    @Embedded
    private PuntoGeografico puntoGeografico;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "humano_id")
    @Builder.Default
    private List<Contacto> contactos = new ArrayList<>();

    @Transient
    @Builder.Default
    private VerificadorDeCercania verificadorDeCercania = ServiceLocator.getService(VerificadorDeCercania.class);

    @Transient
    @Builder.Default
    private ConversorDirecAPunto conversorDirecAPunto = ServiceLocator.getService(ConversorDirecAPunto.class);

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "formas_de_colaboracion_por_humano",
            joinColumns = @JoinColumn(name = "humano_id",referencedColumnName="id")
    )
    @Column(name = "formaDeColaboracion")
    private List<FormaDeColaboracion> formasDeColaboracion = new ArrayList<>();


    public void agregarContacto(Contacto ... contactos) {
        Collections.addAll(this.contactos, contactos);
    }

    public void restarPuntos(Double cantidadPuntos){
        this.puntos-= cantidadPuntos;
    }

    @Override
    public void sumarPuntos(Double cantPuntos) {
        this.puntos += cantPuntos;
    }

    public void setDireccion(Direccion direccion) throws IOException {
        this.direccion = direccion;

        //setearPuntoGeografico(); toDo: @Thiago decirle a @Agus nose que atributo pasar cuando instancio el opennCageAdapterApi
    }

    public void agregarFormasDeColab(FormaDeColaboracion ... formasDeColab) {
        Collections.addAll(this.formasDeColaboracion, formasDeColab);
    }

    public void setearPuntoGeografico() throws IOException {
        this.puntoGeografico = conversorDirecAPunto.obtenerPuntoGeografico(this.direccion);
    }

    public boolean puedeSuscribirse(Heladera heladera){
        if (this.direccion!=null){
            return verificadorDeCercania.estanCerca(
                    this.puntoGeografico,
                    heladera.getPuntoEstrategico().getPuntoGeografico()
            );
        } else {
            throw new ExcepcionDireccionNula(Config.getInstancia().obtenerDelConfig("mDireccionNula"));
        }
    }

    public String obtenerNombreCompleto(){
        return nombre + apellido;
    }

}

