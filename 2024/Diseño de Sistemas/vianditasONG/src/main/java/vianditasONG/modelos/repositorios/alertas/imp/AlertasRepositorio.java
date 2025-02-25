package vianditasONG.modelos.repositorios.alertas.imp;

import lombok.Builder;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.repositorios.RepositorioGenerico;
import vianditasONG.modelos.repositorios.alertas.IAlertasRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertasRepositorio extends RepositorioGenerico implements IAlertasRepositorio {

    private List<Alerta> alertas;

    public AlertasRepositorio(){
        this.alertas = new ArrayList<>();
    }
    public Optional<Alerta> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(Alerta.class, id));
    }
    @SuppressWarnings("unchecked")
    public List<Alerta> buscarTodos(){
        return entityManager()
                .createQuery("from " + Alerta.class.getName())
                .getResultList();
    }

}
