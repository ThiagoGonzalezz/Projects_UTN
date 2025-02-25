package vianditasONG.utils.usuarioRolesYPermisos;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.seguridad.encriptadores.Encriptador;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorMD5;
import vianditasONG.utils.Persistente;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="usuario")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario extends Persistente {
    private static final Encriptador encriptadorMD5 = ServiceLocator.getService(EncriptadorMD5.class);

    @Column(name="nombreUsuario", columnDefinition = "VARCHAR(50)")
    private String nombreDeUsuario;
    @Column(name="hashContraseña", columnDefinition = "VARCHAR(60)")
    private String hashContrasenia;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name="rol_id")
    private Rol rol;

    public static Usuario of(String nombreDeUsuario, String contrasenia, Rol rol) {


        return Usuario.builder()
                .nombreDeUsuario(nombreDeUsuario)
                .hashContrasenia(encriptadorMD5.encriptar(contrasenia))
                .rol(rol)
                .build();
    }
}

