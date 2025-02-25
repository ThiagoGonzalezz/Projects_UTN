package vianditasONG.modelos.entities.heladeras;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;

import java.util.List;
@Builder
public class CalculadorDeHeladerasCercanas {

    @Builder.Default
    private VerificadorDeCercania verificadorDeCercania = ServiceLocator.getService(VerificadorDeCercania.class);
    @Builder.Default
    private HeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);

    public List<Heladera> obtenerHeladerasCercanas(Heladera heladera) {
        List<Heladera> otrasHeladeras = heladerasRepositorio.buscarTodos().stream().filter(h->h!=heladera).toList();

        return otrasHeladeras.stream().
                filter(h->verificadorDeCercania.estanCerca(heladera.getPuntoEstrategico().getPuntoGeografico(),
                        h.getPuntoEstrategico().getPuntoGeografico()))
                .toList();
    }
}
