package vianditasONG.modelos.entities.colaboradores.tarjeta;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AperturaFehaciente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.IntentoAperturaFallida;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarjeta_colaborador")
public class TarjetaColaborador{
    @Id @Getter
    private String codigo;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="humano_id", columnDefinition = "BIGINT")
    private Humano colaboradorAsociado;
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjetaColaborador_id")
    private List<AperturaFehaciente> aperturasFehacientes = new ArrayList<>();
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjetaColaborador_id")
    private List<IntentoAperturaFallida> aperturasFallidas = new ArrayList<>();
    @Column(name = "fechaAlta", columnDefinition = "DATE")
    private LocalDate fechaAlta;

    public void registrarAperturaDeHeladera(AperturaFehaciente aperturaFehaciente){
     this.aperturasFehacientes.add(aperturaFehaciente);
    }

    public void registrarIntentoDeAperturaFallida(IntentoAperturaFallida intentoAperturaFallida){

        this.aperturasFallidas.add(intentoAperturaFallida);
    }


}
