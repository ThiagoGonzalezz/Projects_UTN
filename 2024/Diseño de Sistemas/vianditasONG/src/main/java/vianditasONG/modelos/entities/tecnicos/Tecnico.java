package vianditasONG.modelos.entities.tecnicos;

import vianditasONG.converters.dbconverters.MedioDeAvisoConverter;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import lombok.*;
import vianditasONG.utils.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="tecnico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tecnico extends Persistente {
    @Column(name="cuil", columnDefinition = "VARCHAR(50)")
    private String cuil;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name="nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name="apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;
    @Enumerated(EnumType.STRING)
    @Column(name="tipoDeDocumento")
    private TipoDeDocumento tipoDeDocumento;
    @Column(name="numeroDeDocumento", columnDefinition = "VARCHAR(50)")
    private String numeroDeDocumento;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    @Builder.Default
    private List<Contacto> contactos = new ArrayList<>();
    @Embedded
    private AreaDeCobertura areaDeCobertura;
    @Convert(converter = MedioDeAvisoConverter.class)
    @Column(name = "medioDeNotificacion")
    @Getter
    private MedioDeAviso medioDeAviso;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    @Builder.Default
    private List<Incidente> incidentes = new ArrayList<>();
    @Column(name = "disponible")
    @Builder.Default
    private boolean disponible=true;

    public void agregarContacto(Contacto ... contactos) {
        Collections.addAll(this.contactos, contactos);
    }

    public void agregarIncidente(Incidente ... incidentes) {
        Collections.addAll(this.incidentes, incidentes);
    }
}
