package vianditasONG.modelos.repositorios.motivos_de_distribucion;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.MotivoDeDistribucion;

import java.util.List;
import java.util.Optional;

public interface IMotivosDeDistribucionRepositorio {

    void guardar(Object objeto);
    void actualizar(Object objeto);
    Optional<MotivoDeDistribucion> buscarPorId(Long id);
    List<MotivoDeDistribucion> buscarTodos();
    void eliminarLogico(IPersistente persistente);

}
