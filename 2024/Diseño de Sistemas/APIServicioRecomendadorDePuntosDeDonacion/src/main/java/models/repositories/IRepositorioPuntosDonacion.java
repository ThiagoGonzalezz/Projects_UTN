package models.repositories;

import models.entities.puntosDeDonacion.PuntoDeDonacion;

import java.util.List;
import java.util.Optional;

public interface IRepositorioPuntosDonacion {

    List<PuntoDeDonacion> buscarTodos();
    void guardar(PuntoDeDonacion puntoDeDonacion);
    void actualizar(PuntoDeDonacion puntoDeDonacion);
    void eliminarLogico(PuntoDeDonacion puntoDeDonacion);
}
