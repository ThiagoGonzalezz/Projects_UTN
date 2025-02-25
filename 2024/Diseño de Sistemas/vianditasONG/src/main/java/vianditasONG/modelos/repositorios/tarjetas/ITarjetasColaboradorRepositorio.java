package vianditasONG.modelos.repositorios.tarjetas;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;

import java.util.List;
import java.util.Optional;

public interface ITarjetasColaboradorRepositorio {
    Optional<TarjetaColaborador> buscarPorCodigo(String codigo);
    Optional<TarjetaColaborador> buscarPorHumano(Humano humano);
    void guardar(Object objeto);
    void actualizar(Object objeto);
    List<DistribucionDeVianda> buscarTodos();
    void eliminarLogico(IPersistente persistente);

}
