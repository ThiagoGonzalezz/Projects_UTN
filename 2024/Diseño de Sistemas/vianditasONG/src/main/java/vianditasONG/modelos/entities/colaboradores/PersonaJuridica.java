package vianditasONG.modelos.entities.colaboradores;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "persona_juridica")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonaJuridica extends Persistente implements Puntuable {
    @Column(name = "razonSocial", columnDefinition = "VARCHAR(50)")
    private String razonSocial;

    @Enumerated(value = EnumType.STRING)
    private TipoDeOrganizacion tipoDeOrganizacion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="rubroPersonaJuridica_id")
    private RubroPersonaJuridica rubro;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "forma_de_colaboracion",
            joinColumns = @JoinColumn(name = "personaJuridica_id",referencedColumnName="id")
    )
    @Column(name = "formaDeColaboracion")
    private List<FormaDeColaboracion> formasDeColaboracion = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "personaJuridica_id")
    @Builder.Default
    private List<Contacto> contactos = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "formularioRespondido_id", columnDefinition = "BIGINT")
    private FormularioRespondido formularioRespondido;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @Enumerated(value = EnumType.STRING)
    private EstadoDeSolicitud estadoDeSolicitud;

    @Embedded
    private Direccion direccion;

    @Column(name="fechaDeSolicitud", columnDefinition = "DATETIME")
    private LocalDateTime fechaDeSolicitud;
    @Column(name="fechaDeSolicitudRespondida", columnDefinition = "DATETIME")
    private LocalDateTime fechaDeSolicitudRespondida;

    @Builder.Default
    @Column (name = "puntos")
    private Double puntos=Double.valueOf(Config.getInstancia().obtenerDelConfig("cantPuntosInicialesColaborador"));

    public void restarPuntos(Double cantidadPuntos){
        this.puntos-= cantidadPuntos;
    }


    @Override
    public void sumarPuntos(Double cantPuntos) {
        this.puntos += cantPuntos;
    }

    public void agregarContacto(Contacto ... contactos) {
        Collections.addAll(this.contactos, contactos);
    }

    public void agregarFormasDeColab(FormaDeColaboracion ... formasDeColab) {
        Collections.addAll(this.formasDeColaboracion, formasDeColab);
    }

    public void agregarDonacionesDeDinero(DonacionDeDinero donacion, DonacionDeDinero donacion1, DonacionDeDinero donacion2) {
    //ToDo
    }

    public String obtenerNombreCompleto(){
        return razonSocial;
    }

    public enum TipoDeOrganizacion {
        GUBERNAMENTAL,
        EMPRESA,
        ONG,
        INSTITUCION
    }
}
