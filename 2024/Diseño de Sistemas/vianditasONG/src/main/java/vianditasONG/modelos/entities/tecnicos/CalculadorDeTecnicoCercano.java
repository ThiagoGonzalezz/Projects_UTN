package vianditasONG.modelos.entities.tecnicos;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;
import vianditasONG.utils.verificadorDeCercania.CalculadorDeDistancias;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;

import java.util.Comparator;
import java.util.List;

@Builder
public class CalculadorDeTecnicoCercano {
    @Builder.Default
    private VerificadorDeCercania verificadorDeCercania = ServiceLocator.getService(VerificadorDeCercania.class);
    @Builder.Default
    private TecnicosRepositorio tecnicosRepositorio = ServiceLocator.getService(TecnicosRepositorio.class);
    @Builder.Default
    private CalculadorDeDistancias calculadorDeDistancias = ServiceLocator.getService(CalculadorDeDistancias.class);

    private List<Tecnico> tecnicosCercanos(Heladera heladera) {
        List<Tecnico> tecnicos = tecnicosRepositorio.buscarTodos();

        List<Tecnico> tecnicosCercanos= tecnicos.stream().
                                        filter(t -> verificadorDeCercania.estanCerca(
                                                heladera.getPuntoEstrategico().getPuntoGeografico(),
                                                t.getAreaDeCobertura().getPuntoGeografico(),
                                                t.getAreaDeCobertura().getRadioEnKM()
                                                ) && t.isDisponible()).toList();
        if(tecnicosCercanos.isEmpty()){
            throw new ExcepcionTecnicoNoDisponible("mTecnicoNoDisponible");
        }
        else {
            return tecnicosCercanos;
        }
    }

    public Tecnico obtenerTecnicoMasCercano(Heladera heladera) {
        List<Tecnico> tecnicosCercanos = this.tecnicosCercanos(heladera);
        return tecnicosCercanos.stream()
                .min(Comparator.comparingDouble(t -> calculadorDeDistancias.calcularDistancia(
                        t.getAreaDeCobertura().getPuntoGeografico(),
                        heladera.getPuntoEstrategico().getPuntoGeografico()
                ))).get();
    }

}
