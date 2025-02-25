package vianditasONG.services;

import vianditasONG.dtos.inputs.AccionSolicitadaInputDTO;
import vianditasONG.dtos.outputs.AccionSolicitadaOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IAccionesSolicitadasService {

    AccionSolicitadaOutputDTO crear(AccionSolicitadaInputDTO accionSolicitadaInputDTO, Usuario usuario);

}
