package vianditasONG.services;

import vianditasONG.dtos.inputs.colaboraciones.DistribucionDeViandaInputDTO;
import vianditasONG.dtos.outputs.DistribucionDeViandaOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IDistribucionesDeViandasService {
    DistribucionDeViandaOutputDTO crear(DistribucionDeViandaInputDTO distribucionDeViandaInputDTO, Usuario usuario);
}
