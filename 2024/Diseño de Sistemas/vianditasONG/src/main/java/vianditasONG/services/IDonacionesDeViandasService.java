package vianditasONG.services;

import vianditasONG.dtos.inputs.colaboraciones.DonacionDeViandaInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.DonacionDeViandaOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IDonacionesDeViandasService {
    DonacionDeViandaOutputDTO crear(DonacionDeViandaInputDTO donacionDeViandaInputDTO, Usuario usuario);
}
