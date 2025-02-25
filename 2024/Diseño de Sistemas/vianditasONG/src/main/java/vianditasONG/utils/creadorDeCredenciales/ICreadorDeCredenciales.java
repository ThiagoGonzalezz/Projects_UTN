package vianditasONG.utils.creadorDeCredenciales;

import vianditasONG.modelos.entities.colaboradores.Humano;

public interface ICreadorDeCredenciales {
    public Credencial crearCredencial(String mail, Humano colaborador);

}
