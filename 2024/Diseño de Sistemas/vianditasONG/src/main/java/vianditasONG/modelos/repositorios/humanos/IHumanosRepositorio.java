package vianditasONG.modelos.repositorios.humanos;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;

import java.util.List;
import java.util.Optional;

public interface IHumanosRepositorio {

    Optional<Humano> buscar(Long id);
    List<Humano> buscarTodos();
    void guardar(Object objeto);
    void actualizar(Object objeto);
    void eliminarLogico(IPersistente persistente);

    Optional<Humano> buscarPorDocumento(TipoDeDocumento tipoDoc, String numDoc);

    Optional<Humano> buscarPorUsuario(Long userId);
}
