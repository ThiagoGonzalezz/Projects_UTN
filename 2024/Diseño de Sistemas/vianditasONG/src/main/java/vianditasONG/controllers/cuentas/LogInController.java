package vianditasONG.controllers.cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.javalin.http.Context;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.usuario.UsuarioDTO;
import vianditasONG.exceptions.login.*;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.usuarios.IUsuariosRepositorio;
import vianditasONG.modelos.repositorios.usuarios.imp.UsuariosRepositorio;
import vianditasONG.modelos.servicios.seguridad.ValidadorPasswordAutorizada;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

import java.util.Optional;

@Builder
public class LogInController {
    @Builder.Default
    private Gson gson = ServiceLocator.getService(Gson.class);

    @Builder.Default
    IUsuariosRepositorio repoUsuarios = ServiceLocator.getService(UsuariosRepositorio.class);

    @Builder.Default
    ValidadorPasswordAutorizada validadorPasswordAutorizada = ServiceLocator.getService(ValidadorPasswordAutorizada.class);

    @Builder.Default
    IHumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);

    @Builder.Default
    IPersonasJuridicasRepositorio repoPersonasJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);

    public void index(Context context) {
        String tipoRol = context.sessionAttribute("tipo-rol");

        if(tipoRol == null){
            context.render("sinUsuario/login.hbs");
        }else {

            switch (Rol.valueOf(tipoRol)) {
                case ADMIN -> context.redirect("/admin/menu");
                case PERSONA_HUMANA -> context.redirect("/menu-persona");
                case PERSONA_JURIDICA -> context.redirect("menu-personaJuridica");
            }
        }
    }

    public void login(Context context) throws JsonProcessingException {
        UsuarioDTO usuarioDTO = gson.fromJson(context.body(), UsuarioDTO.class);
        Usuario usuario = this.obtenerUsuario(usuarioDTO.username);
        Rol rol = usuario.getRol();

        this.validadorPasswordAutorizada.validarPasswordAutorizada(usuario, usuarioDTO.password);

        context.sessionAttribute("nombre-usuario", usuario.getNombreDeUsuario());
        context.sessionAttribute("usuario-id", usuario.getId());
        context.sessionAttribute("tipo-rol", String.valueOf(rol));

        this.recepcionarSegunRol(context, rol, usuario.getId());

    }

    private Usuario obtenerUsuario(String nombreUsuario) {
        Optional<Usuario> usuario = this.repoUsuarios.buscarPorNombreDeUsuario(nombreUsuario);

        return usuario.orElseThrow(UserNotFoundException::new);
    }

    private void recepcionarSegunRol(Context context, Rol rol, Long userId) {
        switch (rol) {
            case PERSONA_HUMANA:
                this.recepcionarPersonaHumana(context, userId);
                break;
            case PERSONA_JURIDICA:
                this.recepcionarPersonaJuridica(context, userId);
                break;
            case ADMIN:
                context.redirect("/admin/menu");
                break;
            default:
                break;
        }
    }

    private void recepcionarPersonaJuridica(Context context, Long userId){
        PersonaJuridica persoJuridica = this.repoPersonasJuridicas.buscarPorUsuario(userId).orElseThrow(PersonaJuridicaConUsuarioInexistenteException::new);

        if(persoJuridica.getEstadoDeSolicitud() != EstadoDeSolicitud.ACEPTADO){
            throw new ColaboradorNoDadoDeAltaException();
        }

        context.sessionAttribute("persona-juridica-id", persoJuridica.getId());
        context.redirect("menu-personaJuridica");
    }

    private void recepcionarPersonaHumana(Context context, Long userId){
        Humano humano = this.repoHumanos.buscarPorUsuario(userId).orElseThrow(HumanoConUsuarioInexistenteException::new);

        if(humano.getEstadoDeSolicitud() != EstadoDeSolicitud.ACEPTADO){
            throw new ColaboradorNoDadoDeAltaException();
        }

        context.sessionAttribute("humano-id", humano.getId());
        context.redirect("/menu-persona");
    }

}
