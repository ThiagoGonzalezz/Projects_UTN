package vianditasONG.services;

import vianditasONG.dtos.inputs.colaboraciones.PersonaVulnerableInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.RegistroPersonaVulnerableOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IRegistrosDePersonasVulnerablesService {
    public RegistroPersonaVulnerableOutputDTO registrar(PersonaVulnerableInputDTO registroPersonaVulnerableInputDTO, Usuario usuario);
}
