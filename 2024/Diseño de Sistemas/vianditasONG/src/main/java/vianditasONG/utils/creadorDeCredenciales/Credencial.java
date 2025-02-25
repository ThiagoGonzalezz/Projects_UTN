package vianditasONG.utils.creadorDeCredenciales;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="credencial")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credencial implements IPersistente {

    @Id
    @Column(name="id", columnDefinition = "VARCHAR(50)")
    private String id;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name="mail", columnDefinition = "VARCHAR(60)")
    private String mail;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaborador;

    public static Credencial of(String idCredencial, String mail, Humano colaborador) {
        return Credencial.builder()
                .id(idCredencial)
                .mail(mail)
                .colaborador(colaborador)
                .build();
    }

    public String getNombre() {
        return colaborador.getNombre();
    }
}
