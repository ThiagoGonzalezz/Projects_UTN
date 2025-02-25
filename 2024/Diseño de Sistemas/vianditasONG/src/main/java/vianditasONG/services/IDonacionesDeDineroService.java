package vianditasONG.services;

import vianditasONG.dtos.inputs.colaboraciones.DonacionDeDineroInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.DonacionDeDineroOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IDonacionesDeDineroService {
    DonacionDeDineroOutputDTO crear(DonacionDeDineroInputDTO donacionDeDineroInputDTO, Usuario usuario);
}
