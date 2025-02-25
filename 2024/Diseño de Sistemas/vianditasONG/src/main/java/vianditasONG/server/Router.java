package vianditasONG.server;


import io.javalin.Javalin;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.*;
import vianditasONG.controllers.canjes.CanjesController;
import vianditasONG.controllers.colaboraciones.*;
import vianditasONG.controllers.colaboradores.RecomendadorColaboradoresController;
import vianditasONG.controllers.colaboradores.SolicitudesColaboradoresController;
import vianditasONG.controllers.colaboradores.personasJuridicas.PersonasJuridicasController;
import vianditasONG.controllers.colaboradores.humanos.HumanosController;

import vianditasONG.controllers.colaboradores.personasJuridicas.RubrosPersonasJuridicasController;
import vianditasONG.controllers.cuentas.LogInController;
import vianditasONG.controllers.cuentas.LogOutController;
import vianditasONG.controllers.cuentas.SignUpController;
import vianditasONG.controllers.formularios.FormularioRespondidoHumanoController;
import vianditasONG.controllers.formularios.FormularioRespondidoPJController;
import vianditasONG.controllers.formularios.FormulariosController;
import vianditasONG.controllers.heladeras.GestionDeHeladerasController;
import vianditasONG.controllers.heladeras.HeladerasAMostrarController;
import vianditasONG.controllers.heladeras.HeladerasEnMapaController;
import vianditasONG.controllers.menus.MenuAdminController;
import vianditasONG.controllers.menus.MenuHumanoController;
import vianditasONG.controllers.menus.MenuPersonaJuridicaController;
import vianditasONG.controllers.reporteDeFallas.HistorialFallasController;
import vianditasONG.controllers.reporteDeFallas.ReporteFallaTecnicaController;
import vianditasONG.controllers.tecnicos.GestionDeTecnicosController;
import vianditasONG.controllers.tecnicos.MostrarTecnicosController;
import vianditasONG.modelos.entities.formulario.Pregunta;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;

public class Router implements SimplePersistenceTest {

    public void init(Javalin app) {


        app.before(ctx -> {entityManager().clear();});

        // =========================================================================================================================
        // ================ SIN USUARIO ============================================================================================
        // =========================================================================================================================

          app.get("/login", ServiceLocator.getService(LogInController.class)::index);

          app.post("/login", ServiceLocator.getService(LogInController.class)::login);

          app.post("/humanos", ServiceLocator.getService(HumanosController.class)::save);

          app.post("/personas-juridicas", ServiceLocator.getService(PersonasJuridicasController.class)::save);

          app.get("/registro/personas-humanas", ServiceLocator.getService(HumanosController.class)::create);

          app.get("/registro/personas-juridicas", ServiceLocator.getService(PersonasJuridicasController.class)::create);

          app.get("/usuarios/validacion-password", ServiceLocator.getService(SignUpController.class)::checkPassword);

          app.get("/usuarios/validacion-nombre", ServiceLocator.getService(SignUpController.class)::checkUsername);

          app.post("/usuarios", ServiceLocator.getService(SignUpController.class)::createUser);

          app.get("/rubros-persona-juridica", ServiceLocator.getService(RubrosPersonasJuridicasController.class)::show);

          app.get("/errores/403NoDadoDeAlta", ctx -> {ctx.render("errores/403NoDadoDeAlta.hbs");});

          app.get("/localidades", ServiceLocator.getService(LocalidadesController.class)::show);

          // =========================================================================================================================
          // ================ COLABORADOR ============================================================================================
          // =========================================================================================================================

           app.get("/menu-persona", ServiceLocator.getService(MenuHumanoController.class)::getMenu, Rol.PERSONA_HUMANA);
           app.post("/menu-persona", ServiceLocator.getService(MenuHumanoController.class)::save, Rol.PERSONA_HUMANA);
            //Ver formulario para registrar una persona vulnerable
           app.get("/registro-persona-vulnerable", ServiceLocator.getService(PersonasVulnerablesController.class)::create, Rol.PERSONA_HUMANA);
            //Registrar una persona vulnerable
           app.post("/registro-persona-vulnerable", ServiceLocator.getService(PersonasVulnerablesController.class)::save, Rol.PERSONA_HUMANA);
            //Ver catálogo de productos y servicios
           app.get("/catalogo-productos-y-servicios", ServiceLocator.getService(OfertasProdYServController.class)::index, Rol.PERSONA_HUMANA, Rol.PERSONA_JURIDICA);
            //Ver formulario para ofrecer productos y servicios
           app.get("/oferta-productos-y-servicios", ServiceLocator.getService(OfertasProdYServController.class)::create, Rol.PERSONA_JURIDICA);
            //Guardar un nuevo producto o servicio
           app.post("/oferta-productos-y-servicios", ServiceLocator.getService(OfertasProdYServController.class)::save, Rol.PERSONA_JURIDICA);
           //Canjear un nuevo producto o servicio
           app.post("/canje-oferta/{id}", ServiceLocator.getService(CanjesController.class)::save, Rol.PERSONA_JURIDICA, Rol.PERSONA_HUMANA);

           app.get("/donacion-vianda", ServiceLocator.getService(DonacionesDeViandasController.class)::create, Rol.PERSONA_HUMANA);
           app.post("/donacion-vianda", ServiceLocator.getService(DonacionesDeViandasController.class)::save, Rol.PERSONA_HUMANA);

           app.get("/distribucion-viandas", ServiceLocator.getService(DistribucionesDeViandasController.class)::create, Rol.PERSONA_HUMANA);
           app.post("/distribucion-viandas", ServiceLocator.getService(DistribucionesDeViandasController.class)::save, Rol.PERSONA_HUMANA);

           app.get("/menu-personaJuridica", ServiceLocator.getService(MenuPersonaJuridicaController.class)::getMenu, Rol.PERSONA_JURIDICA);
           app.post("/menu-personaJuridica", ServiceLocator.getService(MenuPersonaJuridicaController.class)::save, Rol.PERSONA_JURIDICA);

            app.get("/donaciones-de-dinero", ServiceLocator.getService(DonacionesDeDineroController.class)::index, Rol.PERSONA_JURIDICA, Rol.PERSONA_HUMANA);

            app.post("/donaciones-de-dinero", ServiceLocator.getService(DonacionesDeDineroController.class)::save, Rol.PERSONA_JURIDICA, Rol.PERSONA_HUMANA);

            app.get("/reportar-falla-tecnica", ServiceLocator.getService(ReporteFallaTecnicaController.class)::index, Rol.PERSONA_HUMANA);

            app.post("/reportar-falla-tecnica", ServiceLocator.getService(ReporteFallaTecnicaController.class)::save, Rol.PERSONA_HUMANA);

            app.get("/colocar-heladera", ServiceLocator.getService(ColocarHeladeraController.class)::index, Rol.PERSONA_JURIDICA);

            app.post("/colocar-heladera", ServiceLocator.getService(ColocarHeladeraController.class)::save, Rol.PERSONA_JURIDICA);

            app.post("/colocar-heladera/puntos-recomendados", ServiceLocator.getService(RecomendadorPuntosController.class)::obtenerPuntosRecomendados, Rol.PERSONA_JURIDICA);
            app.get("/heladeras-a-cargo", ServiceLocator.getService(HeladerasACargoController.class)::index, Rol.PERSONA_JURIDICA);
            app.get("/heladeras/{id}/incidentes", ServiceLocator.getService(HeladerasACargoController.class)::obtenerIncidentesDeHeladera, Rol.PERSONA_JURIDICA);


        app.get("/historial-fallas", ServiceLocator.getService(HistorialFallasController.class)::index, Rol.PERSONA_HUMANA);
            app.post("/suscribirse-a-heladera", ServiceLocator.getService(SuscripcionesController.class)::save, Rol.PERSONA_HUMANA);
            app.delete("/anular-suscripcion", ServiceLocator.getService(SuscripcionesController.class)::anularSuscripcion, Rol.PERSONA_HUMANA);

            app.get("/historial-puntos", ServiceLocator.getService(HistorialDePuntosController.class)::getHistorial, Rol.PERSONA_JURIDICA, Rol.PERSONA_HUMANA);

            app.get("/errores/400HeladerasDup", ctx -> {ctx.render("errores/400HeladerasDup.hbs");});
            app.get("/errores/409CantViandasErroneas", ctx -> {ctx.render("errores/409CantViandasErroneas.hbs");});

            app.get("/logout", ServiceLocator.getService(LogOutController.class)::logout);
        // =========================================================================================================================
        // ================ ADMIN ============================================================================================
        // =========================================================================================================================

          app.get("/colaboraciones/importacion", ServiceLocator.getService(ImportadorColaboracionesController.class)::create, Rol.ADMIN);

          app.post("/colaboraciones/importacion", ServiceLocator.getService(ImportadorColaboracionesController.class)::save, Rol.ADMIN);

          app.get("/formularios", ServiceLocator.getService(FormulariosController.class)::index, Rol.ADMIN);

          app.post("/formularios", ServiceLocator.getService(FormulariosController.class)::create, Rol.ADMIN);

          app.get("/formularios/{id}/edicion", ServiceLocator.getService(FormulariosController.class)::edit, Rol.ADMIN);

          app.post("/formularios/{id}/edicion-nombre", ServiceLocator.getService(FormulariosController.class)::updateNombre, Rol.ADMIN);

          app.post("/formularios/{id}/edicion-pregunta-nueva", ServiceLocator.getService(FormulariosController.class)::createPregunta, Rol.ADMIN);

          app.post("/formularios/{id}/edicion-pregunta-modificada/{id-preg}", ServiceLocator.getService(FormulariosController.class)::updatePregunta, Rol.ADMIN);

          app.post("/formularios/{id}/edicion-pregunta-eliminada/{id-preg}", ServiceLocator.getService(FormulariosController.class)::deletePregunta, Rol.ADMIN);

          app.post("/formularios/{id}/eliminacion", ServiceLocator.getService(FormulariosController.class)::delete, Rol.ADMIN);

          app.get("admin/colaboradores/recomendacion", ServiceLocator.getService(RecomendadorColaboradoresController.class)::index, Rol.ADMIN);

          app.post("admin/colaboradores/recomendacion", ServiceLocator.getService(RecomendadorColaboradoresController.class)::save, Rol.ADMIN);

          app.get("admin/colaboradores/solicitudes", ServiceLocator.getService(SolicitudesColaboradoresController.class)::index, Rol.ADMIN);

          app.post("admin/colaboradores/solicitudes/personas-juridicas/habilitacion/{id}", ServiceLocator.getService(SolicitudesColaboradoresController.class)::aceptarSolicitudPj, Rol.ADMIN);

          app.post("admin/colaboradores/solicitudes/humanos/habilitacion/{id}", ServiceLocator.getService(SolicitudesColaboradoresController.class)::aceptarSolicitudHumana, Rol.ADMIN);

          app.post("admin/colaboradores/solicitudes/personas-juridicas/rechazo/{id}", ServiceLocator.getService(SolicitudesColaboradoresController.class)::rechazarSolicitudPj, Rol.ADMIN);

          app.post("admin/colaboradores/solicitudes/humanos/rechazo/{id}", ServiceLocator.getService(SolicitudesColaboradoresController.class)::rechazarSolicitudHumano, Rol.ADMIN);

          app.get("admin/colaboradores/formularios-respondidos/humanos/{id}", ServiceLocator.getService(FormularioRespondidoHumanoController.class)::show, Rol.ADMIN);

          app.get("admin/colaboradores/formularios-respondidos/personas-juridicas/{id}", ServiceLocator.getService(FormularioRespondidoPJController.class)::show, Rol.ADMIN);

          app.get("/admin/visualizacion-reportes", ServiceLocator.getService(ReportesController.class)::index, Rol.ADMIN);

          app.get("/admin/menu", ServiceLocator.getService(MenuAdminController.class)::getMenu, Rol.ADMIN);

//        app.get("/productos/{id}", ServiceLocator.getService(ProductosController.class)::show);
//
//        app.get("/productos/{id}/edicion", ServiceLocator.getService(ProductosController.class)::edit);
//
//        app.post("/productos/{id}/edicion", ServiceLocator.getService(ProductosController.class)::update);
//
//        app.post("/productos/{id}/eliminiacion", ServiceLocator.getService(ProductosController.class)::delete);
//
//        app.post("/productos", ServiceLocator.getService(ProductosController.class)::save);

        app.get("/heladeras", ServiceLocator.getService(HeladerasEnMapaController.class)::index);

        app.get("/admin/heladeras", ServiceLocator.getService(HeladerasAMostrarController.class)::index, Rol.ADMIN);

        app.get("/admin/gestion-de-heladeras", ServiceLocator.getService(GestionDeHeladerasController.class)::index, Rol.ADMIN);

        app.post("/admin/gestion-de-heladeras", ServiceLocator.getService(GestionDeHeladerasController.class)::save, Rol.ADMIN);

        app.get("/admin/heladeras/{id}", ServiceLocator.getService(GestionDeHeladerasController.class)::edit, Rol.ADMIN);

        app.get("/admin/heladeras/{id}/incidentes", ServiceLocator.getService(GestionDeHeladerasController.class)::obtenerIncidentesDeHeladera, Rol.ADMIN);


        // Ruta para actualizar una heladera existente
        app.put("/admin/heladeras/{id}", ServiceLocator.getService(GestionDeHeladerasController.class)::update, Rol.ADMIN);

        // Ruta para eliminar una heladera
        app.delete("/admin/heladeras/{id}", ServiceLocator.getService(GestionDeHeladerasController.class)::delete, Rol.ADMIN);


        app.get("/gestion-de-tecnicos",ServiceLocator.getService(GestionDeTecnicosController.class)::index, Rol.ADMIN);
        app.get("/admin/tecnicos", ServiceLocator.getService(MostrarTecnicosController.class)::index, Rol.ADMIN);
        app.post("/admin/gestion-de-tecnicos",ServiceLocator.getService(GestionDeTecnicosController.class)::save, Rol.ADMIN);
        app.get("/admin/gestion-de-tecnicos/{cuil}",ServiceLocator.getService(GestionDeTecnicosController.class)::edit, Rol.ADMIN);
        app.put("/admin/gestion-de-tecnicos/{cuil}",ServiceLocator.getService(GestionDeTecnicosController.class)::update, Rol.ADMIN);
        app.delete("/admin/gestion-de-tecnicos/{cuil}", ServiceLocator.getService(GestionDeTecnicosController.class)::delete, Rol.ADMIN);


    }


}
