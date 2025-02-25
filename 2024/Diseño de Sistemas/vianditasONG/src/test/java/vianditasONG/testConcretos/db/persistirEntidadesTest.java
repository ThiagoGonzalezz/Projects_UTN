package vianditasONG.testConcretos.db;

import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;

import vianditasONG.utils.usuarioRolesYPermisos.*;

import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.MotivoDeDistribucion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.EstadoDeSolicitud;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.TipoDeDocumento;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.modelos.entities.formulario.*;
import vianditasONG.modelos.entities.formulario.*;
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;

import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AperturaFehaciente;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.IntentoAperturaFallida;

import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMax;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionCantViandasMin;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotTelegramAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.RegistroUsoTarjeta;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.entities.tecnicos.AreaDeCobertura;
import vianditasONG.modelos.entities.tecnicos.Tecnico;

import vianditasONG.modelos.repositorios.alertas.imp.AlertasRepositorio;

import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.imp.AperturasFehacientesRepositorio;

import vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.imp.AperturasFallidasRepositorio;

import vianditasONG.modelos.repositorios.canjes.imp.CanjesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.*;
import vianditasONG.modelos.repositorios.colaboracion.imp.*;
import vianditasONG.modelos.repositorios.contactos.imp.ContactosRepositorio;

import vianditasONG.modelos.repositorios.credenciales.imp.CredencialesRepositorio;
import vianditasONG.modelos.repositorios.falla_tecnica.imp.FallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.*;
import vianditasONG.modelos.repositorios.formularios.imp.*;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;
import vianditasONG.modelos.repositorios.motivos_de_distribucion.imp.MotivosDeDistribucionRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.imp.PersonaEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosOfertasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMaxRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionCantViandasMinRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.RegistroUsoTarjetaRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.usuarios.imp.UsuariosRepositorio;
import vianditasONG.modelos.repositorios.viandas.imp.ViandasRepositorio;
import vianditasONG.config.ServiceLocator;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



/*public class persistirEntidadesTest implements WithSimplePersistenceUnit {

    @Test
    void persistirTecnico() throws Exception {
        TecnicosRepositorio repoTecnicos = TecnicosRepositorio.builder().build();
        NotificadorDeEmail notificadorDeEmail = NotificadorDeEmail.builder().build();

        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .latitud(100.234254124)
                .longitud(100.234422)
                .build();

        AreaDeCobertura areaDeCobertura = AreaDeCobertura.builder()
                .puntoGeografico(puntoGeografico)
                .radioEnKM(40)
                .build();

        Contacto contacto = Contacto.builder()
                .tipoDeContacto(TipoDeContacto.CORREO)
                .contacto("pedritoFdz@gmail.com")
                .build();

        Tecnico tecnicoAPersistir = Tecnico.builder()
                .nombre("Pedro")
                .cuil("20-45327426-3")
                .apellido("Fernandez")
                .activo(true)
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDeDocumento("45327426")
                .contacto(contacto)
                .areaDeCobertura(areaDeCobertura)
                .medioDeAviso(notificadorDeEmail)
                .build();

        withTransaction(() -> {
        repoTecnicos.guardar(tecnicoAPersistir);

        Tecnico tecnicoPersistido = repoTecnicos.buscarPorCuil("20-45327426-3").get();


        assertNotNull(tecnicoPersistido);
        assertEquals("Pedro", tecnicoPersistido.getNombre());
        });
    }

    @Test
    void persistirLocalidad() throws Exception {

        LocalidadesRepositorio repoLocalidades = LocalidadesRepositorio.builder().build();
        Localidad localidadAPersistir = Localidad.builder()
                .nombre("Ituzaingo")
                .provincia(Provincia.BUENOS_AIRES)
                .build();

        withTransaction(() -> {
            repoLocalidades.guardar(localidadAPersistir);

            Optional<Localidad> localidadPersistida = repoLocalidades.buscarPorId(localidadAPersistir.getId());

            assertNotNull(localidadPersistida);
            assertEquals("Ituzaingo", localidadPersistida.get().getNombre());
        });
    }
/*
    @Test
    void persistirPermiso() throws Exception {

        PermisosRepositorio repoPermisos = PermisosRepositorio.builder().build();
        Permiso permisoAPersistir = Permiso.builder()
                        .descripcion("DAR DE ALTA A COLABORADORES")
                        .build();

        withTransaction(() -> {
            repoPermisos.guardar(permisoAPersistir);

            Optional<Permiso> permisoPersistido = repoPermisos.buscarPorId(permisoAPersistir.getId());

            assertNotNull(permisoPersistido);
            assertEquals("DAR DE ALTA A COLABORADORES", permisoPersistido.get().getDescripcion());
        });
    }

    /*
    @Test
    void persistirRol() throws Exception {

        Permiso permiso = Permiso.builder()
                .descripcion("DAR DE ALTA A COLABORADORES")
                .build();

        List<Permiso> listaPermisos = new ArrayList<>();
        listaPermisos.add(permiso);

        RolesRepositorio rolesRepositorio = RolesRepositorio.builder().build();
        Rol rolAPersistir = Rol.builder()
                .nombre("ADMIN")
                .permisos(listaPermisos)
                .build();

        withTransaction(() -> {
            rolesRepositorio.guardar(rolAPersistir);

            Optional<Rol> rolPersistido = rolesRepositorio.buscarPorId(rolAPersistir.getId());

            assertNotNull(rolPersistido);
            assertEquals("ADMIN", rolPersistido.get().getNombre());
        });
    }*/
/*
    @Test
    void persistirCredencial() throws Exception {

        CredencialesRepositorio repoCredenciales = CredencialesRepositorio.builder().build();
        Credencial credencialAPersistir = Credencial.builder()
                .id("da39a3ee5e6b4b0d3255bfef95601890afd80709")
                .mail("pedrito@gmail.com.ar")
                .build();
                //toDo: @Thiago agregar humano

        withTransaction(() -> {
            repoCredenciales.guardar(credencialAPersistir);

            Optional<Credencial> credencialPersistida = repoCredenciales.buscarPorId(credencialAPersistir.getId());

            assertNotNull(credencialPersistida);
            assertEquals("pedrito@gmail.com.ar", credencialPersistida.get().getMail());
        });
    }


    @Test
    void persistirUsuario() throws Exception {

        UsuariosRepositorio repoUsuarios = UsuariosRepositorio.builder().build();

        Permiso permiso = Permiso.builder()
                .descripcion("DAR DE ALTA A COLABORADORES")
                .build();

        List<Permiso> listaPermisos = new ArrayList<>();
        listaPermisos.add(permiso);


        Usuario usuarioAPersistir = Usuario.builder()
                        .nombreDeUsuario("Pedrito17")
                        .contrasenia("Disenio17!!a")
                        .rol(Rol.PERSONA_HUMANA)
                        .build();

        withTransaction(() -> {
            repoUsuarios.guardar(usuarioAPersistir);

            Optional<Usuario> usuarioPersistido = repoUsuarios.buscarPorId(usuarioAPersistir.getId());

            assertNotNull(usuarioPersistido);
            assertEquals("Pedrito17", usuarioPersistido.get().getNombreDeUsuario());
        });
    }

    @Test
    void persistirContacto() throws Exception {

        ContactosRepositorio repoContactos = ContactosRepositorio.builder().build();
        Contacto contactoAPersistir = Contacto.builder()
                        .contacto("1138085884")
                        .tipoDeContacto(TipoDeContacto.TELEFONO)
                        .build();

        withTransaction(() -> {
            repoContactos.guardar(contactoAPersistir);

            Optional<Contacto> contactoPersistido = repoContactos.buscarPorId(contactoAPersistir.getId());

            assertNotNull(contactoPersistido);
            assertEquals("1138085884", contactoPersistido.get().getContacto());
        });
    }

    @Test
    void persistirOpcion() throws Exception {

        OpcionesRepositorio repoOpciones = OpcionesRepositorio.builder().build();

        Opcion opcionAPersistir = Opcion.builder()
                .descripcion("Verdadero")
                .build();

        withTransaction(() -> {
            repoOpciones.guardar(opcionAPersistir);

            Optional<Opcion> opcionPersistida = repoOpciones.buscarPorId(opcionAPersistir.getId());

            assertNotNull(opcionPersistida);
            assertEquals("Verdadero", opcionPersistida.get().getDescripcion());
        });
    }

    @Test
    void persistirPregunta() throws Exception {

        PreguntasRepositorio repoPreguntas = PreguntasRepositorio.builder().build();

        Opcion opcion = Opcion.builder()
                .descripcion("Verdadero")
                .build();

        List<Opcion> listaDeOpciones = new ArrayList<>();

        listaDeOpciones.add(opcion);

        Pregunta preguntaAPersistir = Pregunta.builder()
                .enunciado("vamos a aprobar la entrega?")
                .esEsencial(true)
                .tipo(TipoPregunta.PREGUNTA_MULTIPLE_RESPUESTA_UNICA)
                .opciones(listaDeOpciones)
                .build();

        withTransaction(() -> {
            repoPreguntas.guardar(preguntaAPersistir);

            Optional<Pregunta> preguntaPersistida = repoPreguntas.buscarPorId(preguntaAPersistir.getId());

            assertNotNull(preguntaPersistida);
            assertEquals("vamos a aprobar la entrega?", preguntaPersistida.get().getEnunciado());
        });
    }

    @Test
    void persistirFormulario() throws Exception {

        FormulariosRepositorio repoFormularios = FormulariosRepositorio.builder().build();

        Opcion opcion = Opcion.builder()
                .descripcion("Verdadero")
                .build();

        List<Opcion> listaDeOpciones = new ArrayList<>();

        listaDeOpciones.add(opcion);

        Pregunta pregunta = Pregunta.builder()
                .enunciado("vamos a aprobar la entrega?")
                .esEsencial(true)
                .tipo(TipoPregunta.PREGUNTA_MULTIPLE_RESPUESTA_UNICA)
                .opciones(listaDeOpciones)
                .build();

        List<Pregunta> listaPreguntas = new ArrayList<>();

        listaPreguntas.add(pregunta);
        listaPreguntas.add(pregunta);

        Formulario formularioAPersistir = Formulario.builder()
                .nombre("Formulario Aprobacion")
                .preguntas(listaPreguntas)
                .build();


        withTransaction(() -> {
            repoFormularios.guardar(formularioAPersistir);

            Optional<Formulario> formPersistido = repoFormularios.buscarPorId(formularioAPersistir.getId());

            assertNotNull(formPersistido);
            assertEquals("Formulario Aprobacion", formPersistido.get().getNombre());
        });
    }

    @Test
    void persistirRespuestas() throws Exception {

        RespuestasRepositorio repoRespuestas = RespuestasRepositorio.builder().build();

        Opcion opcion = Opcion.builder()
                .descripcion("Verdadero")
                .build();

        List<Opcion> listaDeOpciones = new ArrayList<>();

        listaDeOpciones.add(opcion);

        Pregunta pregunta = Pregunta.builder()
                .enunciado("vamos a aprobar la entrega?")
                .esEsencial(true)
                .tipo(TipoPregunta.PREGUNTA_MULTIPLE_RESPUESTA_UNICA)
                .opciones(listaDeOpciones)
                .build();

        Respuesta respuestaAPersistir = Respuesta.builder()
                        .preguntaAsociada(pregunta)
                        .opcionesSeleccionadas(listaDeOpciones)
                        .build();


        withTransaction(() -> {
            repoRespuestas.guardar(respuestaAPersistir);

            Optional<Respuesta> respuestaPersistida = repoRespuestas.buscarPorId(respuestaAPersistir.getId());

            assertNotNull(respuestaPersistida);
            assertEquals(respuestaAPersistir.getId(), respuestaPersistida.get().getId());
        });
    }

    @Test
    void persistirFormulariosRespondios() throws Exception {

        FormulariosRespondidosRepositorio repoFormsRespondidos = FormulariosRespondidosRepositorio.builder().build();

        Opcion opcion = Opcion.builder()
                .descripcion("Verdadero")
                .build();

        List<Opcion> listaDeOpciones = new ArrayList<>();

        listaDeOpciones.add(opcion);

        Pregunta pregunta = Pregunta.builder()
                .enunciado("vamos a aprobar la entrega?")
                .esEsencial(true)
                .tipo(TipoPregunta.PREGUNTA_MULTIPLE_RESPUESTA_UNICA)
                .opciones(listaDeOpciones)
                .build();

        Respuesta respuesta = Respuesta.builder()
                .preguntaAsociada(pregunta)
                .opcionesSeleccionadas(listaDeOpciones)
                .build();

        List<Pregunta> listaPreguntas = new ArrayList<>();

        listaPreguntas.add(pregunta);
        listaPreguntas.add(pregunta);

        Formulario formulario = Formulario.builder()
                .nombre("Formulario Aprobacion")
                .preguntas(listaPreguntas)
                .build();

        List<Respuesta> listaRespuestas = new ArrayList<>();
        listaRespuestas.add(respuesta);

        FormularioRespondido formularioRespondidoAPersistir = FormularioRespondido.builder()
                .formularioAsociado(formulario)
                .fechaRealizacion(LocalDate.now())
                .listaRespuestas(listaRespuestas)
                .build();


        withTransaction(() -> {
            repoFormsRespondidos.guardar(formularioRespondidoAPersistir);

            Optional<FormularioRespondido> fromRespPersistida = repoFormsRespondidos.buscarPorId(formularioRespondidoAPersistir.getId());

            assertNotNull(formularioRespondidoAPersistir);
            assertEquals(formularioRespondidoAPersistir.getId(), fromRespPersistida.get().getId());
        });
    }



    @Test
    void persistirVianda() throws Exception {

        ViandasRepositorio viandasRepositorio = new ViandasRepositorio();

        //No le puse ni colaborador ni heladera todavia
        Vianda viandaAPersistir = Vianda.builder().comida("Ravioles").
                fechaDeCaducidad(LocalDateTime.now())
                .fechaDeDonacion(LocalDateTime.now())
                .entregada(Boolean.FALSE)
                .calorias(100)
                .peso(40)
                .build();



        withTransaction(() -> {
            viandasRepositorio.guardar(viandaAPersistir);
            Optional<Vianda> viandaPersistida = viandasRepositorio.buscarPorId(viandaAPersistir.getId());

            assertNotNull(viandaPersistida);
            assertEquals("Ravioles", viandaPersistida.get().getComida());
        });

    }

    @Test
    void persistirRegistroPersonaVulnerable() throws Exception {

        RegistrosPersonasVulnerablesRepositorio registrosPersonasVulnerablesRepositorio =  RegistrosPersonasVulnerablesRepositorio.builder().build();

        RegistroPersonasVulnerables registroPersonasVulnerablesAPersistir = RegistroPersonasVulnerables.builder()
                .fecha(LocalDate.now())
                .build();

        withTransaction(() -> {
            registrosPersonasVulnerablesRepositorio.guardar(registroPersonasVulnerablesAPersistir);
            Optional<RegistroPersonasVulnerables> registroPersonasVulnerablesPersistida = registrosPersonasVulnerablesRepositorio.buscarPorId(registroPersonasVulnerablesAPersistir.getId());

            assertNotNull(registroPersonasVulnerablesPersistida);
            assertEquals(registroPersonasVulnerablesAPersistir.getId(), registroPersonasVulnerablesPersistida.get().getId());
        });

    }

    @Test
    void persistirDonacionDeVianda() throws Exception {

        DonacionesDeViandasRepositorio donacionesDeViandasRepositorio =  DonacionesDeViandasRepositorio.builder().build();
        Vianda vianda1 = Vianda.builder()
                .fechaDeCaducidad(LocalDateTime.now())
                .fechaDeDonacion(LocalDateTime.now())
                .entregada(Boolean.FALSE)
                .calorias(100)
                .peso(40)
                .build();
        Vianda vianda2 = Vianda.builder()
                .fechaDeCaducidad(LocalDateTime.now())
                .fechaDeDonacion(LocalDateTime.now())
                .entregada(Boolean.FALSE)
                .calorias(100)
                .peso(40)
                .build();

        List<Vianda> viandas = new ArrayList<>();
        viandas.add(vianda1);
        viandas.add(vianda2);

        DonacionDeVianda donacionDeViandaAPersistir = DonacionDeVianda.builder()
                .fecha(LocalDate.now())
                .viandas(viandas)
                .build();

        withTransaction(() -> {
            donacionesDeViandasRepositorio.guardar(donacionDeViandaAPersistir);
            Optional<DonacionDeVianda> donacionDeViandaPersistida = donacionesDeViandasRepositorio.buscarPorId(donacionDeViandaAPersistir.getId());

            assertNotNull(donacionDeViandaPersistida);
            assertEquals(donacionDeViandaAPersistir.getId(), donacionDeViandaPersistida.get().getId());
        });

    }

    @Test
    void persistirIncidente() throws Exception {

        FallasTecnicasRepositorio fallasTecnicasRepositorio = new FallasTecnicasRepositorio();
        FallaTecnica fallaTecnicaAPersistir = FallaTecnica.of(null, "Falla refrigeracion", null, "src/foto/algo");

        AlertasRepositorio alertasRepositorio = new AlertasRepositorio();
        Alerta alertaAPersistir = Alerta.of(TipoDeAlerta.INTENTO_FRAUDE, null);


        withTransaction(() -> {
            fallasTecnicasRepositorio.guardar(fallaTecnicaAPersistir);
            Optional<FallaTecnica> fallaTecnicaPersistida = fallasTecnicasRepositorio.buscarPorId(fallaTecnicaAPersistir.getId());

            alertasRepositorio.guardar(alertaAPersistir);
            Optional<Alerta> alerta = alertasRepositorio.buscarPorId(alertaAPersistir.getId());

            assertNotNull(fallaTecnicaPersistida);
            assertEquals(fallaTecnicaAPersistir.getId(), fallaTecnicaPersistida.get().getId());
            assertNotNull(alerta);
            assertEquals(fallaTecnicaAPersistir.getId(), fallaTecnicaPersistida.get().getId());
        });
    }

    @Test
    void persistirTarjetaColaborador() throws Exception {

        TarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = TarjetasColaboradorRepositorio.builder().build();

        TarjetaColaborador tarjetaColaboradorAPersistir = TarjetaColaborador.builder()
                .codigo("12345678910")
                .fechaAlta(LocalDate.now())
                .build();

        withTransaction(() -> {
            tarjetasColaboradorRepositorio.guardar(tarjetaColaboradorAPersistir);
            Optional<TarjetaColaborador> tarjetaColaboradorPersistida = tarjetasColaboradorRepositorio.buscarPorCodigo("12345678910");

            assertNotNull(tarjetaColaboradorPersistida);
            assertEquals(tarjetaColaboradorAPersistir.getCodigo(), tarjetaColaboradorPersistida.get().getCodigo());
        });

    }

    @Test
    void persistirSuscripciones() throws Exception {

        SuscripcionCantViandasMaxRepositorio suscripcionCantViandasMaxRepositorio =  SuscripcionCantViandasMaxRepositorio.builder().build();
        SuscripcionCantViandasMax suscripcionCantViandasMaxAPersistir = SuscripcionCantViandasMax.builder()
                .cantViandasMax(20)
                .medioDeAviso(new NotificadorDeTelegram(new EnviadorDeTelegrams(new BotTelegramAPI("3423242"))))
                .build();

        SuscripcionCantViandasMinRepositorio suscripcionCantViandasMinRepositorio = SuscripcionCantViandasMinRepositorio.builder().build();
        SuscripcionCantViandasMin suscripcionCantViandasMin = SuscripcionCantViandasMin.builder()
                .cantViandasMin(10)
                .medioDeAviso(new NotificadorDeTelegram(new EnviadorDeTelegrams( new BotTelegramAPI("3423242"))))
                .build();

        SuscripcionDesperfectosRepositorio suscripcionDesperfectosRepositorio = SuscripcionDesperfectosRepositorio.builder().build();
        SuscripcionDesperfectos suscripcionDesperfectos = SuscripcionDesperfectos.builder()
                .medioDeAviso(new NotificadorDeTelegram(new EnviadorDeTelegrams(new BotTelegramAPI("3423242"))))
                .build();


        withTransaction(() -> {
            suscripcionCantViandasMaxRepositorio.guardar(suscripcionCantViandasMaxAPersistir);
            Optional<SuscripcionCantViandasMax> suscripcionCantViandasMaxPersistida = suscripcionCantViandasMaxRepositorio.buscarPorId(suscripcionCantViandasMaxAPersistir.getId());

            assertNotNull(suscripcionCantViandasMaxPersistida);
            assertEquals(suscripcionCantViandasMaxAPersistir.getId(), suscripcionCantViandasMaxPersistida.get().getId());

            suscripcionCantViandasMinRepositorio.guardar(suscripcionCantViandasMin);
            Optional<SuscripcionCantViandasMin> suscripcionCantViandasMinPersistida = suscripcionCantViandasMinRepositorio.buscarPorId(suscripcionCantViandasMin.getId());

            assertNotNull(suscripcionCantViandasMinPersistida);
            assertEquals(suscripcionCantViandasMin.getId(), suscripcionCantViandasMinPersistida.get().getId());

            suscripcionDesperfectosRepositorio.guardar(suscripcionDesperfectos);
            Optional<SuscripcionDesperfectos> suscripcionDesperfectosPersistida = suscripcionDesperfectosRepositorio.buscarPorId(suscripcionDesperfectos.getId());

            assertNotNull(suscripcionDesperfectosPersistida);
            assertEquals(suscripcionDesperfectos.getId(), suscripcionDesperfectosPersistida.get().getId());

        });

    }

    /*@Test
    void persistirMotivoDistribucion() throws Exception {

        MotivosDeDistribucionRepositorio motivosDeDistribucionRepositorio = new MotivosDeDistribucionRepositorio();

        MotivoDeDistribucion motivoDeDistribucionAPersistir = MotivoDeDistribucion.builder().descripcion("Falla técnica").build();

        withTransaction(() -> {
            motivosDeDistribucionRepositorio.guardar(motivoDeDistribucionAPersistir);
            Optional<MotivoDeDistribucion> motivoDeDistribucionPersistido = motivosDeDistribucionRepositorio.buscarPorId(motivoDeDistribucionAPersistir.getId());

            assertNotNull(motivoDeDistribucionPersistido);
            assertEquals(motivoDeDistribucionAPersistir.getId(), motivoDeDistribucionPersistido.get().getId());
        });

    }*/
/*

    @Test
    void persistirRubroOferta() throws Exception {

        RubrosOfertasRepositorio rubrosOfertasRepositorio = RubrosOfertasRepositorio.builder().build();

        RubroOferta rubroOfertaAPersistir = RubroOferta.builder().descripcion("Estufa eléctrica").build();

        withTransaction(() -> {
            rubrosOfertasRepositorio.guardar(rubroOfertaAPersistir);
            Optional<RubroOferta> rubroOfertaPersistida = rubrosOfertasRepositorio.buscarPorId(rubroOfertaAPersistir.getId());

            assertNotNull(rubroOfertaAPersistir);
            assertEquals(rubroOfertaAPersistir.getId(), rubroOfertaPersistida.get().getId());
        });

    }

    @Test
    void persistirRubroPersonaJuridica() throws Exception {

        RubrosPersonaJuridicaRepositorio rubrosPersonaJuridicaRepositorio = new RubrosPersonaJuridicaRepositorio();

        RubroPersonaJuridica rubroPersonaJuridicaAPersistir = RubroPersonaJuridica.builder().descripcion("Tecnología").build();

        withTransaction(() -> {
            rubrosPersonaJuridicaRepositorio.guardar(rubroPersonaJuridicaAPersistir);
            Optional<RubroPersonaJuridica> rubroPersonaJuridicaPersistida = rubrosPersonaJuridicaRepositorio.buscarPorId(rubroPersonaJuridicaAPersistir.getId());

            assertNotNull(rubroPersonaJuridicaPersistida);
            assertEquals(rubroPersonaJuridicaAPersistir.getId(), rubroPersonaJuridicaPersistida.get().getId());
        });

    }


    @Test
    void persistirPersonaJuridica() throws Exception {

        PersonasJuridicasRepositorio personasJuridicasRepositorio = PersonasJuridicasRepositorio.builder().build();
        RubroPersonaJuridica rubroPersonaJuridica = RubroPersonaJuridica.builder().descripcion("Tecnología").build();
        Localidad localidad = Localidad.builder().nombre("Almagro").provincia(Provincia.BUENOS_AIRES).build();
        Direccion direccion = Direccion.builder().calle("Avenida Medrano").altura("1900").localidad(localidad).build();
        Contacto contacto = Contacto.builder().contacto("utnba@frba.utn.edu.ar").tipoDeContacto(TipoDeContacto.CORREO).build();
        FormaDeColaboracion formaDeColaboracion = FormaDeColaboracion.DONACION_VIANDAS;
        List<FormaDeColaboracion> formasDeColaboracion = new ArrayList<>();
        formasDeColaboracion.add(formaDeColaboracion);
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);

        Permiso permiso = Permiso.builder()
                .descripcion("DAR DE ALTA A COLABORADORES")
                .build();

        List<Permiso> listaPermisos = new ArrayList<>();
        listaPermisos.add(permiso);

        Usuario usuario = Usuario.builder()
                .nombreDeUsuario("Pedrito17")
                .contrasenia("Disenio17!!a")
                .rol(Rol.ADMIN)
                .build();

        PersonaJuridica personaJuridicaAPersistir = PersonaJuridica.builder()
                .rubro(rubroPersonaJuridica)
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now())
                .formasDeColaboracion(formasDeColaboracion)
                .direccion(direccion)
                .razonSocial("UTN")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.INSTITUCION)
                .contactos(contactos)
                .usuario(usuario)
                .build();

        withTransaction(() -> {
            personasJuridicasRepositorio.guardar(personaJuridicaAPersistir);
            Optional<PersonaJuridica> personaJuridicaPersistida = personasJuridicasRepositorio.buscarPorId(personaJuridicaAPersistir.getId());

            assertNotNull(personaJuridicaPersistida);
            assertEquals(personaJuridicaAPersistir.getId(), personaJuridicaPersistida.get().getId());
        });

    }

    @Test
    void persistirOferta() throws Exception {
        OfertasRepositorio ofertasRepositorio = OfertasRepositorio.builder().build();

        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        RubroOferta rubroOferta = RubroOferta.builder().descripcion("Destornillador plano").build();

        Oferta ofertaAPersistir = Oferta.builder()
                .imagen("src/../..")
                .rubro(rubroOferta)
                .puntosNecesarios(500d)
                .nombre("Oferta1")
                .ofertante(personaJuridica)
                .build();

        withTransaction(() -> {
            ofertasRepositorio.guardar(ofertaAPersistir);
            Optional<Oferta> ofertaPersistida = ofertasRepositorio.buscarPorId(ofertaAPersistir.getId());

            assertNotNull(ofertaPersistida);
            assertEquals(ofertaAPersistir.getId(), ofertaPersistida.get().getId());
        });

    }

    @Test
    void persistirCanje() throws Exception {
        CanjesRepositorio canjesRepositorio = CanjesRepositorio.builder().build();

        PersonaJuridica ofertante = PersonaJuridica.builder().razonSocial("UTN").build();
        PersonaJuridica canjeadorPersonaJuridica = PersonaJuridica.builder().build();
        Oferta oferta = Oferta.builder().nombre("Oferta1").build();


        Canje canjeAPersistir = Canje.builder()
                .puntos(360)
                .fechaYHora(LocalDateTime.now())
                .ofertante(ofertante)
                .canjeadorPersonaJuridica(canjeadorPersonaJuridica)
                .oferta(oferta)
                .build();

        withTransaction(() -> {
            canjesRepositorio.guardar(canjeAPersistir);
            Optional<Canje> canjePersistido = canjesRepositorio.buscarPorId(canjeAPersistir.getId());

            assertTrue(canjePersistido.isPresent(), "El canje no fue persistido correctamente");
        });
    }


    @Test
    void persistirColaboracionHeladera() throws Exception {

        HacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = HacerseCargoDeHeladerasRepositorio.builder().build();

        PersonaJuridica personaJuridica = PersonaJuridica.builder().razonSocial("UTNBA").build();
        HacerseCargoDeHeladera hacerseCargoDeHeladera = HacerseCargoDeHeladera
                .builder() //todo agregar heladera
                .colaborador(personaJuridica)
                .build();

        withTransaction(() -> {
            hacerseCargoDeHeladerasRepositorio.guardar(hacerseCargoDeHeladera);
            Optional<HacerseCargoDeHeladera> colaboracionPersistida = hacerseCargoDeHeladerasRepositorio.buscarPorId(hacerseCargoDeHeladera.getId());

            assertNotNull(colaboracionPersistida);
            assertEquals(hacerseCargoDeHeladera.getId(), colaboracionPersistida.get().getId());
        });

    }

    @Test
    void persistirPersonaEnSituacionVulnerable() throws Exception {

        PersonaEnSituacionVulnerableRepositorio personaEnSituacionVulnerableRepositorio = PersonaEnSituacionVulnerableRepositorio.builder().build();

        PersonaEnSituacionVulnerable hijo1 = PersonaEnSituacionVulnerable.builder().nombre("Juancito").apellido("Perez").build();
        PersonaEnSituacionVulnerable hijo2 = PersonaEnSituacionVulnerable.builder().nombre("Gonzalo").apellido("Perez").build();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerableAPersistir = PersonaEnSituacionVulnerable.
                builder()
                .fechaDeNacimiento(LocalDate.of(2003,8,23))
                .fechaDeRegistro(LocalDateTime.now())
                .esAdulto(true)
                .nombre("Pablo")
                .apellido("Perez")
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("20.943.124")
                .poseeVivienda(false)
                .build();

        personaEnSituacionVulnerableAPersistir.agregarHijos(hijo1, hijo2);
        withTransaction(() -> {
            personaEnSituacionVulnerableRepositorio.guardar(personaEnSituacionVulnerableAPersistir);
            Optional<PersonaEnSituacionVulnerable> personaEnSituacionVulnerablePersistida = personaEnSituacionVulnerableRepositorio.buscarPorId(personaEnSituacionVulnerableAPersistir.getId());

            assertEquals(2, personaEnSituacionVulnerablePersistida.get().getHijos().size());
        });

    }

    @Test
    void persistirTarjetaPersonaVulnerable() throws Exception {

        TarjetasDePersonasVulnerablesRepositorio tarjetasDePersonasVulnerablesRepositorio = TarjetasDePersonasVulnerablesRepositorio.builder().build();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = PersonaEnSituacionVulnerable.builder().nombre("P1").build();
        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerableAPersistir = TarjetaDePersonaVulnerable.builder()
                .codigo("2456yuo2pa46")
                .personaAsociada(personaEnSituacionVulnerable)
                .usosRestantes(0)
                .build();

        withTransaction(() -> {
            tarjetasDePersonasVulnerablesRepositorio.guardar(tarjetaDePersonaVulnerableAPersistir);
            Optional<TarjetaDePersonaVulnerable> tarjetaDePersonaVulnerablePersistida = tarjetasDePersonasVulnerablesRepositorio.buscarPorId(tarjetaDePersonaVulnerableAPersistir.getId());

            assertNotNull(tarjetaDePersonaVulnerablePersistida);
            assertEquals(tarjetaDePersonaVulnerableAPersistir.getId(), tarjetaDePersonaVulnerablePersistida.get().getId());
        });

    }


    @Test
    void persistirHeladera() throws Exception {

        ModelosRepositorio repoModelos = ModelosRepositorio.builder().build();

        Modelo modeloAPersistir = Modelo.builder()
                .tempMaximaEnGradosCelsius(10.0f)
                .tempMinimaEnGradosCelsius(-5.0f)
                .capacidadMaximaViandas(100)
                .descripcion("Modelo básico")
                .build();

        withTransaction(() -> {
            repoModelos.guardar(modeloAPersistir);
        });

        HeladerasRepositorio repoHeladeras = HeladerasRepositorio.builder().build();
        LocalidadesRepositorio repoLocalidad = LocalidadesRepositorio.builder().build();

        Localidad localidad = Localidad.builder()
                .provincia(Provincia.BUENOS_AIRES)
                .build();


        withTransaction(() -> {
            repoLocalidad.guardar(localidad);
        });

        Direccion direccion = Direccion.builder()
                .calle("martin fierro")
                .altura("742")
                .localidad(localidad)
                .build();

        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .latitud(100.234254124)
                .longitud(100.234422)
                .build();

        PuntoEstrategico puntoEstrategico = PuntoEstrategico.builder()
                .puntoGeografico(puntoGeografico)
                .direccion(direccion)
                .nombre("Punto Central")
                .build();


        Heladera heladeraAPersistir = Heladera.builder()
                .nombre("Heladera 1")
                .puntoEstrategico(puntoEstrategico)
                .cantidadViandas(10)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .modeloHeladera(modeloAPersistir)
                .diasDesactivada(0)
                .fechaUltimaDesactivacion(LocalDateTime.now())
                .temperaturaDeseadaEnGradosCelsius(4.0f)
                .ultimaTemperaturaRegistradaEnGradosCelsius(4.5f)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .build();

        withTransaction(() -> {
            repoHeladeras.guardar(heladeraAPersistir);

            Optional<Heladera> heladeraPersistida = repoHeladeras.buscar(heladeraAPersistir.getId());

            assertNotNull(heladeraPersistida);
            assertEquals("Heladera 1", heladeraPersistida.get().getNombre());
            assertEquals("Punto Central", heladeraPersistida.get().getPuntoEstrategico().getNombre());
        });
    }



    @Test
    void persistirAccionSolicitada() throws Exception {

        AccionesSolicitadasRepositorio repoAcciones = AccionesSolicitadasRepositorio.builder().build();
        TarjetasColaboradorRepositorio repoTarjetaColaborador = TarjetasColaboradorRepositorio.builder().build();

        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.builder()
                .codigo("123245678")  // Asigna el ID manualmente
                .build();

        withTransaction(() -> {
            repoTarjetaColaborador.guardar(tarjetaColaborador);  // Guardar la tarjeta con ID manual
        });

        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now())
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .build();


        withTransaction(() -> {
            repoAcciones.guardar(accionSolicitada);

            Optional<AccionSolicitada> accionPersistida = repoAcciones.buscar(accionSolicitada.getId());

            assertNotNull(accionPersistida);
        });}




    @Test
    void persistirRegistroUsoTarjeta() throws Exception{

        RegistroUsoTarjetaRepositorio registroUsoTarjetaRepositorio = new RegistroUsoTarjetaRepositorio();

        Heladera heladera = Heladera.builder().nombre("HeladeraPruebaParaUsoTarjeta").build();
        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable = TarjetaDePersonaVulnerable.builder().codigo("00000000000").build();

        RegistroUsoTarjeta registroUsoTarjetaAPersistir = RegistroUsoTarjeta.builder()
                .fechaUso(LocalDateTime.now())
                //.heladera(heladera)
                .tarjetaDePersonaVulnerable(tarjetaDePersonaVulnerable)
                .build();

        withTransaction(() -> {
            registroUsoTarjetaRepositorio.guardar(registroUsoTarjetaAPersistir);
            Optional<RegistroUsoTarjeta> registroUsoTarjetaPersistido = registroUsoTarjetaRepositorio.buscarPorId(registroUsoTarjetaAPersistir.getId());

            assertNotNull(registroUsoTarjetaPersistido);
            assertEquals(registroUsoTarjetaAPersistir.getId(), registroUsoTarjetaPersistido.get().getId());
        });


    }

    @Test
    void persistirAperturaFehaciente() throws Exception {


        HeladerasRepositorio repoHeladeras = HeladerasRepositorio.builder().build();


        AccionesSolicitadasRepositorio repoAccionesSolicitadas = AccionesSolicitadasRepositorio.builder().build();


        TarjetasColaboradorRepositorio repoTarjetasColaborador = TarjetasColaboradorRepositorio.builder().build();


        AperturasFehacientesRepositorio repoAperturasFehaciente = AperturasFehacientesRepositorio.builder().build();


        Heladera heladera = Heladera.builder()
                .nombre("Heladera Test")
                .cantidadViandas(50)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .fechaDeRegistro(LocalDateTime.now())
                .build();


        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now())
                .fechaYHoraDeSolicitud(LocalDateTime.now()).build();


        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.builder()
                .codigo("12345").build();


        withTransaction(() -> {
            repoHeladeras.guardar(heladera);
            repoAccionesSolicitadas.guardar(accionSolicitada);
            repoTarjetasColaborador.guardar(tarjetaColaborador);
        });


        AperturaFehaciente aperturaFehaciente = AperturaFehaciente.builder()
                .fechaYHora(LocalDateTime.now())
                .heladera(heladera)
                .accionSolicitada(accionSolicitada)
                .tarjetaColaborador(tarjetaColaborador)
                .build();


        withTransaction(() -> {
            repoAperturasFehaciente.guardar(aperturaFehaciente);

            Optional<AperturaFehaciente> aperturaPersistida = repoAperturasFehaciente.buscar(aperturaFehaciente.getId());
            assertNotNull(aperturaPersistida);
            assertEquals(heladera.getId(), aperturaPersistida.get().getHeladera().getId());
            assertEquals(accionSolicitada.getId(), aperturaPersistida.get().getAccionSolicitada().getId());
            assertEquals(tarjetaColaborador.getCodigo(), aperturaPersistida.get().getTarjetaColaborador().getCodigo());
        });
    }


    @Test
    void persistirHumano() throws Exception {

        UsuariosRepositorio repoUsuarios = UsuariosRepositorio.builder().build();
        FormulariosRespondidosRepositorio repoFormulariosRespondidos = FormulariosRespondidosRepositorio.builder().build();
        HumanosRepositorio repoHumanos = HumanosRepositorio.builder().build();


        Usuario usuarioAPersistir = Usuario.builder().nombreDeUsuario("usuariotest").contrasenia("messi999").build();

        FormularioRespondido formularioRespondidoAPersistir = FormularioRespondido.builder()
                .formularioAsociado(Formulario.builder().build())
                .build();


        withTransaction(() -> {
            repoUsuarios.guardar(usuarioAPersistir);
            repoFormulariosRespondidos.guardar(formularioRespondidoAPersistir);
        });

        Localidad localidad = Localidad.builder()
                .provincia(Provincia.BUENOS_AIRES)
                .build();

        Direccion direccion = Direccion.builder()
                .calle("Avenida Siempre Viva")
                .altura("742")
                .localidad(localidad)
                .build();


        Humano humanoAPersistir = Humano.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .usuario(usuarioAPersistir)
                .formularioRespondido(formularioRespondidoAPersistir)
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(direccion)
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("12345678")
                .fechaDeSolicitud(LocalDate.now())
                .puntos(10.0)
                .build();


        withTransaction(() -> {
            repoHumanos.guardar(humanoAPersistir);


            Optional<Humano> humanoPersistido = repoHumanos.buscar(humanoAPersistir.getId());
            assertNotNull(humanoPersistido);
            assertEquals("Juan", humanoPersistido.get().getNombre());
            assertEquals("Pérez", humanoPersistido.get().getApellido());
            assertEquals("usuariotest", humanoPersistido.get().getUsuario().getNombreDeUsuario());
            assertEquals("Avenida Siempre Viva", humanoPersistido.get().getDireccion().getCalle());
        });
    }

    @Test
    void persistirSensorDeMovimiento() throws Exception {
        SensoresDeMovimientoRepositorio sensoresDeMovimientoRepositorio = new SensoresDeMovimientoRepositorio();
        HeladerasRepositorio heladerasRepositorio = HeladerasRepositorio.builder().build();


        Heladera heladera = Heladera.builder()
                .nombre("Heladera Test")
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .build();


        SensorDeMovimiento sensorDeMovimiento = SensorDeMovimiento.builder()
                .heladera(heladera)
                .build();


        withTransaction(() -> {
            heladerasRepositorio.guardar(heladera);
        });


        withTransaction(() -> {
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento);
            Optional<SensorDeMovimiento> sensorPersistido = sensoresDeMovimientoRepositorio.buscarPorId(sensorDeMovimiento.getId());


            assertNotNull(sensorPersistido);
            assertEquals(sensorDeMovimiento.getId(), sensorPersistido.get().getId());
        });
    }

    @Test
    void persistirSensorDeTemperatura() throws Exception {
        SensoresDeTemperaturaRepositorio sensoresDeTemperaturaRepositorio = new SensoresDeTemperaturaRepositorio();
        HeladerasRepositorio heladerasRepositorio = HeladerasRepositorio.builder().build();


        Heladera heladera = Heladera.builder()
                .nombre("Heladera Test Temperatura")
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .build();


        SensorDeTemperatura sensorDeTemperatura = SensorDeTemperatura.builder()
                .heladera(heladera)
                .build();


        withTransaction(() -> {
            heladerasRepositorio.guardar(heladera); // Guardamos la heladera
        });


        withTransaction(() -> {
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura); // Guardamos el sensor
            Optional<SensorDeTemperatura> sensorPersistido = sensoresDeTemperaturaRepositorio.buscarPorId(sensorDeTemperatura.getId());


            assertNotNull(sensorPersistido);
            assertEquals(sensorDeTemperatura.getId(), sensorPersistido.get().getId());
        });
    }


    @Test
    void persistirModelo() throws Exception {
        ModelosRepositorio modelosRepositorio = ModelosRepositorio.builder().build();


        Modelo modelo = Modelo.builder()
                .tempMaximaEnGradosCelsius(10.0f)
                .tempMinimaEnGradosCelsius(-5.0f)
                .capacidadMaximaViandas(50)
                .descripcion("Modelo de heladera de prueba")
                .build();


        withTransaction(() -> {
            modelosRepositorio.guardar(modelo); // Guardamos el modelo
            Optional<Modelo> modeloPersistido = modelosRepositorio.buscarPorId(modelo.getId());

            assertNotNull(modeloPersistido);
            assertEquals(modelo.getId(), modeloPersistido.get().getId());
            assertEquals(modelo.getTempMaximaEnGradosCelsius(), modeloPersistido.get().getTempMaximaEnGradosCelsius());
            assertEquals(modelo.getTempMinimaEnGradosCelsius(), modeloPersistido.get().getTempMinimaEnGradosCelsius());
            assertEquals(modelo.getCapacidadMaximaViandas(), modeloPersistido.get().getCapacidadMaximaViandas());
            assertEquals(modelo.getDescripcion(), modeloPersistido.get().getDescripcion());
        });
    }

    @Test
    void persistirDonacionDeDinero() throws Exception {
        PersonasJuridicasRepositorio personasJuridicasRepositorio = PersonasJuridicasRepositorio.builder().build();
        HumanosRepositorio humanosRepositorio = HumanosRepositorio.builder().build();
        DonacionesDeDineroRepositorio donacionesDeDineroRepositorio = DonacionesDeDineroRepositorio.builder().build();

        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();

        Humano humano = Humano.builder()
                .numeroDocumento("45013679")
                .nombre("Ramiro")
                .apellido("Messi")
                .fechaDeSolicitud(LocalDate.now())
                .build();


        DonacionDeDinero donacionDeDinero = DonacionDeDinero.builder()
                .fecha(LocalDate.of(2024, 9, 20))
                .monto(1000.0)
                .frecuenciaDonacion(FrecuenciaDonacion.UNA_CADA_MES)
                .personaJuridica(personaJuridica)
                .humano(humano)
                .build();


        withTransaction(() -> {

            personasJuridicasRepositorio.guardar(personaJuridica);
            humanosRepositorio.guardar(humano);


            donacionesDeDineroRepositorio.guardar(donacionDeDinero);


            Optional<DonacionDeDinero> donacionPersistida = donacionesDeDineroRepositorio.buscarPorId(donacionDeDinero.getId());


            assertNotNull(donacionPersistida);
            assertEquals(donacionDeDinero.getId(), donacionPersistida.get().getId());
            assertEquals(donacionDeDinero.getMonto(), donacionPersistida.get().getMonto());
            assertEquals(donacionDeDinero.getFecha(), donacionPersistida.get().getFecha());
            assertEquals(donacionDeDinero.getFrecuenciaDonacion(), donacionPersistida.get().getFrecuenciaDonacion());
            assertEquals(donacionDeDinero.getPersonaJuridica().getId(), donacionPersistida.get().getPersonaJuridica().getId());
            assertEquals(donacionDeDinero.getHumano().getId(), donacionPersistida.get().getHumano().getId());
        });
    }







    void persistirIntentoAperturaFallida() throws Exception {

        AperturasFallidasRepositorio aperturasFallidasRepositorio = new AperturasFallidasRepositorio();

        Heladera heladera = Heladera.builder().nombre("HeladeraPruebaParaAperturaFallida").build();
        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.builder().codigo("00000000000").build();

        IntentoAperturaFallida intentoAperturaFallidaAPersistir = IntentoAperturaFallida.builder()
                .fechaYHora(LocalDateTime.now())
               //.heladera(heladera)
                .tarjetaColaborador(tarjetaColaborador)
                .build();

        withTransaction(() -> {
            aperturasFallidasRepositorio.guardar(intentoAperturaFallidaAPersistir);
            Optional<IntentoAperturaFallida> intentoAperturaFallidaPersistida =
                    aperturasFallidasRepositorio.buscarPorId(intentoAperturaFallidaAPersistir.getId());

            assertNotNull(intentoAperturaFallidaPersistida);
            assertEquals(intentoAperturaFallidaAPersistir.getId(), intentoAperturaFallidaPersistida.get().getId());
        });

    }
}*/