package vianditasONG.modelos.repositorios;

import vianditasONG.utils.IPersistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioGenerico implements WithSimplePersistenceUnit {
    public void guardar(Object objeto){
        entityManager().persist(objeto);
    }

    public void actualizar(Object objeto){
        entityManager().merge(objeto);
    }

    public void eliminarLogico(IPersistente persistente){
        persistente.setActivo(false);
        this.actualizar(persistente);
    }
}
