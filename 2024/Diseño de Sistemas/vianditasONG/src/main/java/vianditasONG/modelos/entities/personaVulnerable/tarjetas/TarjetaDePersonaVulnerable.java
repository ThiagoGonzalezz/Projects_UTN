package vianditasONG.modelos.entities.personaVulnerable.tarjetas;

import vianditasONG.config.Config;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

import java.time.LocalDate;

@Entity
@Table(name = "tarjeta_de_persona_vulnerable")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarjetaDePersonaVulnerable extends Persistente {
    @Column(name = "codigo", columnDefinition = "VARCHAR(12)")
    private String codigo;
    @Builder.Default
    @Column(name = "usosRestantes")
    private Integer usosRestantes= Integer.valueOf(Config.getInstancia().obtenerDelConfig("usosDiariosMinimos"));
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "personaEnSituacionVulnerable_id")
    private PersonaEnSituacionVulnerable personaAsociada;

    @Builder.Default
    @Column(name = "usosDiarios")
    private Integer usosDiariosMinimos= Integer.valueOf(Config.getInstancia().obtenerDelConfig("usosDiariosMinimos"));
    @Builder.Default
    @Transient
    private Integer multiplicadorUsosPorHijo= Integer.valueOf(Config.getInstancia().obtenerDelConfig("multiplicadorUsosPorHijo"));

    public void registrarUso(Heladera heladera, Integer cantViandas){
        try {
            quedanUsos();
            heladera.quitarViandas(cantViandas);
            this.usosRestantes--;
        }
        catch (ExcepcionUsosTarjeta e){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean quedanUsos(){
        if (this.usosRestantes <= 0){
            throw new ExcepcionUsosTarjeta(Config.getInstancia().obtenerDelConfig("mensajeNoQuedanUsos"));
        }
        return true;
    }

    public Integer usosDiariosPorHijo(){
        return this.personaAsociada.hijosMenores().size()*multiplicadorUsosPorHijo;
    }

    public Integer usosDiarios(){
        return this.usosDiariosMinimos + this.usosDiariosPorHijo();
    }

    public void reiniciarUsosRestantes(){
        this.usosRestantes = this.usosDiarios();
    }

}
