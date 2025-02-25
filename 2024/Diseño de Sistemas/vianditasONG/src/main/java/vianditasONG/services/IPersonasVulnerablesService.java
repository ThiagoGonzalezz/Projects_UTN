package vianditasONG.services;

import vianditasONG.dtos.inputs.PersonaVulnerableInputDTO;
import vianditasONG.dtos.outputs.PersonaVulnerableOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

public interface IPersonasVulnerablesService {

    PersonaVulnerableOutputDTO registrar(PersonaVulnerableInputDTO personaVulnerableInputDTO, Usuario usuario);

}
