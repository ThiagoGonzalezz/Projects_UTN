package vianditasONG.modelos.servicios.recomendadorDeColaboradores;

import vianditasONG.modelos.entities.colaboradores.Humano;

import java.io.IOException;
import java.util.List;

public interface RecomendadorDeColaboradoresAdapter {

    void recomendarColaboradores(List<Humano> humanosRecomendados) throws IOException;

}
