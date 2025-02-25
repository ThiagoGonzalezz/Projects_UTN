package vianditasONG.modelos.servicios.recomendadorDeColaboradores;

import lombok.Builder;
import lombok.Setter;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.colaboradores.Humano;


import java.io.IOException;
import java.util.List;

@Builder
public class RecomendadorDeColaboradores {

    @Builder.Default
    private RecomendadorDeColaboradoresAdapter recomendadorDeColaboradoresAdapter = ServiceLocator.getService(RecomendadorDeColaboradoresAdapterAPI.class);

    public void recomendarColaboradores(List<Humano> humanosRecomendados) throws IOException {
        recomendadorDeColaboradoresAdapter.recomendarColaboradores(humanosRecomendados);
    }

}
