/*package vianditasONG.services.imp.colaboraciones;

import vianditasONG.dtos.inputs.colaboraciones.PersonaVulnerableInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.RegistroPersonaVulnerableOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.ICalculadorTarjetasDistribuidas;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.repositorios.colaboracion.IRegistrosPersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.IPersonasEnSituacionVulnerableRepositorio;
import vianditasONG.services.IRegistrosDePersonasVulnerablesService;
import vianditasONG.utils.permisos.IVerificadorDePermisos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistrosDePersonasVulnerablesService implements IRegistrosDePersonasVulnerablesService {
    ICalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas;
    IRegistrosPersonasVulnerablesRepositorio registrosPersonasVulnerablesRepositorio;
    IPersonasEnSituacionVulnerableRepositorio personasEnSituacionVulnerableRepositorio;
    IHumanosRepositorio humanosRepositorio;
    IVerificadorDePermisos verificadorDePermisos;
    @Override
    public RegistroPersonaVulnerableOutputDTO registrar(PersonaVulnerableInputDTO registroPersonaVulnerableInputDTO, Usuario usuario) {
        verificadorDePermisos.verificarSiUsuarioPuede("REGISTRAR_PERSONA_VULNERABLE", usuario);
        Humano humano = this.humanosRepositorio.buscar(Long.valueOf(registroPersonaVulnerableInputDTO.getIdHumanoHabilitador())).orElse(null);

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = PersonaEnSituacionVulnerable.builder()
                .fechaDeRegistro(LocalDateTime.now()).
                fechaDeNacimiento(LocalDate.parse(registroPersonaVulnerableInputDTO.getFechaDeNacimiento())).
                nombre(registroPersonaVulnerableInputDTO.getNombre()).
                numeroDocumento(registroPersonaVulnerableInputDTO.getNumeroDocumento()).
                tipoDeDocumento(TipoDeDocumento.fromInt(registroPersonaVulnerableInputDTO.getTipoDeDocumento())).
                poseeVivienda(registroPersonaVulnerableInputDTO.isPoseeVivienda()).
                esAdulto(registroPersonaVulnerableInputDTO.isEsAdulto()).
                build();

        /*registroPersonaVulnerableInputDTO.getHijos().stream().forEach(h->{


        });*/
/*
        RegistroPersonasVulnerables registroPersonasVulnerables = RegistroPersonasVulnerables.builder().
                personaVulnerable(personaEnSituacionVulnerable).
                fecha(LocalDate.now()).colaborador(humano).
                build();

        double puntosASumar = this.calculadorTarjetasDistribuidas.calcularPuntos(registroPersonasVulnerables);
        humano.sumarPuntos(puntosASumar);
        this.registrosPersonasVulnerablesRepositorio.guardar(registroPersonasVulnerables);
        this.personasEnSituacionVulnerableRepositorio.guardar(personaEnSituacionVulnerable);

        return null;//todo
    }

}
*/