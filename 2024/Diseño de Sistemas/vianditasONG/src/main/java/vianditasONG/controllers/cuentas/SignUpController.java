package vianditasONG.controllers.cuentas;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.usuario.UsuarioDTO;
import vianditasONG.exceptions.BadRequestException;
import vianditasONG.exceptions.signUp.HumanoVinculadoInexistenteException;
import vianditasONG.exceptions.signUp.PersonaJuridicaVinculadaInexistenteException;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.usuarios.IUsuariosRepositorio;
import vianditasONG.modelos.repositorios.usuarios.imp.UsuariosRepositorio;
import vianditasONG.modelos.servicios.seguridad.ValidadorCreacionPassword;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

@Builder

public class SignUpController implements WithSimplePersistenceUnit {
    @Builder.Default
    IUsuariosRepositorio repoUsuarios = ServiceLocator.getService(UsuariosRepositorio.class);
    @Builder.Default
    IHumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    IPersonasJuridicasRepositorio repoPersonasJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    ValidadorCreacionPassword validadorCreacionPasswordDePasswords = ServiceLocator.getService(ValidadorCreacionPassword.class);
    @Builder.Default
    private Gson gson = ServiceLocator.getService(Gson.class);


    public void checkUsername(Context context) {
        if (this.validarNombreUsuarioDisponible(context.queryParam("username"))) {
            context.status(200);
            System.out.println("OK");
        } else {
            context.status(400);
        }
    }

    public void checkPassword(Context context) {
        String password = context.queryParam("password");

        ResultadoValidacion resultadoValidacion = this.validadorCreacionPasswordDePasswords.validarCreacionContrasenia(password);

        if (resultadoValidacion.esValido()) {
            context.status(200).result("OK");
        } else {
            context.status(400).result(resultadoValidacion.getMensajeError());
        }
    }

    public void createUser(Context context) {
        UsuarioDTO usuarioDTO = gson.fromJson(context.body(), UsuarioDTO.class);

        if (this.validarNombreUsuarioDisponible(usuarioDTO.username) && this.validadorCreacionPasswordDePasswords.validarCreacionContrasenia(usuarioDTO.password).esValido()) {
            Rol rol = Rol.valueOf(context.sessionAttribute("tipo-rol-a-crear"));

            Usuario usuario = this.crearUsuario(usuarioDTO.username, usuarioDTO.password, rol);

            Long idColaborador = Long.parseLong(context.sessionAttribute("colaborador-creado-id"));

            this.vincularUsuarioConColaborador(usuario, rol, idColaborador);

            context.status(200);
        }else {
            throw new BadRequestException();
        }
    }

    private Boolean validarNombreUsuarioDisponible(String nombreUsuario) {
        return !this.repoUsuarios.buscarPorNombreDeUsuario(nombreUsuario).isPresent();
    }

    private Usuario crearUsuario(String username, String password, Rol rol) {
        Usuario usuarioNuveo = Usuario.of(username, password, rol);

        withTransaction(() -> {
            repoUsuarios.guardar(usuarioNuveo);
        });

        return usuarioNuveo;
    }

    private void vincularUsuarioConColaborador(Usuario usuario, Rol rol, Long idColaborador) {
        switch (rol) {
            case PERSONA_HUMANA:
                this.vincularUsuarioConHumano(usuario, idColaborador);
                break;
            case PERSONA_JURIDICA:
                this.vincularUsuarioConPersonaJuridica(usuario, idColaborador);
                break;
        }
    }

    private void vincularUsuarioConHumano(Usuario usuario, Long idHumano){
        try {

            Humano humano = this.repoHumanos.buscar(idHumano).get();
            humano.setUsuario(usuario);
            withTransaction(() -> {
                this.repoHumanos.actualizar(humano);
            });

        } catch (RuntimeException e) {
            throw new HumanoVinculadoInexistenteException();
        }
    }

    private void vincularUsuarioConPersonaJuridica(Usuario usuario, Long idPersonaJuridica){
        try {
            PersonaJuridica personaJuridica = this.repoPersonasJuridicas.buscarPorId(idPersonaJuridica).get();
            personaJuridica.setUsuario(usuario);
            withTransaction(() -> {
                this.repoPersonasJuridicas.actualizar(personaJuridica);
            });

        } catch (RuntimeException e) {
            throw new PersonaJuridicaVinculadaInexistenteException();
        }
    }
}

