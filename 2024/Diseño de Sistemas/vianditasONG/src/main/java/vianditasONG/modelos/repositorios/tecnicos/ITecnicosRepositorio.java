package vianditasONG.modelos.repositorios.tecnicos;

import vianditasONG.utils.IPersistente;
import vianditasONG.modelos.entities.tecnicos.Tecnico;

import java.util.List;
import java.util.Optional;

public interface ITecnicosRepositorio {
        void guardar(Object objeto);
        void actualizar(Object objeto);
        void eliminarLogico(IPersistente persistente);
        Optional<Tecnico> buscarPorCuil(String cuil);
        public Optional<Tecnico> buscarPorId(Long id);
        List<Tecnico> buscarTodos();
}
