package vianditasONG.services.imp;

import vianditasONG.dtos.inputs.PersonaVulnerableInputDTO;
import vianditasONG.dtos.outputs.PersonaVulnerableOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.IPersonasEnSituacionVulnerableRepositorio;
import vianditasONG.services.IPersonasVulnerablesService;
import vianditasONG.utils.permisos.IVerificadorDePermisos;
/*import vianditasONG.utils.permisos.VerificadorDePermisos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonasVulnerablesService implements IPersonasVulnerablesService {

    private IPersonasEnSituacionVulnerableRepositorio personasEnSituacionVulnerableRepositorio;
    private IVerificadorDePermisos verificadorDePermisos;

    public PersonasVulnerablesService(IPersonasEnSituacionVulnerableRepositorio personasEnSituacionVulnerableRepositorio, VerificadorDePermisos verificadorDePermisos){
        this.personasEnSituacionVulnerableRepositorio = personasEnSituacionVulnerableRepositorio;
        this.verificadorDePermisos = verificadorDePermisos;
    }
    public PersonaVulnerableOutputDTO registrar(PersonaVulnerableInputDTO personaVulnerableInputDTO, Usuario usuario){

        verificadorDePermisos.verificarSiUsuarioPuede("REGISTRAR_PERSONA_VULNERABLE", usuario);

        PersonaEnSituacionVulnerable nuevaPersonaVulnerable = PersonaEnSituacionVulnerable.builder()
                .nombre(personaVulnerableInputDTO.getNombre())
                .fechaDeNacimiento(LocalDate.parse(personaVulnerableInputDTO.getFechaDeNacimiento()))
                .fechaDeRegistro(LocalDateTime.parse(personaVulnerableInputDTO.getFechaDeRegistro()))
                .tipoDeDocumento(personaVulnerableInputDTO.getTipoDeDocumento())
                .numeroDocumento(personaVulnerableInputDTO.getNumeroDocumento())
                .build();
                //falta la direccion en caso de que se ingrese

        this.personasEnSituacionVulnerableRepositorio.guardar(nuevaPersonaVulnerable);

        PersonaVulnerableOutputDTO personaVulnerableOutputDTO = PersonaVulnerableOutputDTO.builder()
                .nombre(nuevaPersonaVulnerable.getNombre())
                .fechaDeNacimiento(nuevaPersonaVulnerable.getFechaDeNacimiento().toString())
                .fechaDeRegistro(nuevaPersonaVulnerable.getFechaDeRegistro().toString())
                .tipoDeDocumento(nuevaPersonaVulnerable.getTipoDeDocumento())
                .numeroDocumento(nuevaPersonaVulnerable.getNumeroDocumento())
                .build();

        return personaVulnerableOutputDTO;
    }

}
*/