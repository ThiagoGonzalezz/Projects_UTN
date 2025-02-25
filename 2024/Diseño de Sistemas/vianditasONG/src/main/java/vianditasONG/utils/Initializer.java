package vianditasONG.utils;

import com.twilio.twiml.voice.Prompt;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.colaboradores.personasJuridicas.RubrosPersonasJuridicasController;
import vianditasONG.modelos.entities.colaboraciones.FormaDeColaboracion;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
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
import vianditasONG.modelos.entities.heladeras.EstadoHeladera;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.TipoDeAccion;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.entities.heladeras.suscripciones.SuscripcionDesperfectos;
import vianditasONG.modelos.entities.incidentes.EstadoReporte;
import vianditasONG.modelos.entities.incidentes.FallaTecnica;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.entities.tecnicos.AreaDeCobertura;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.IAccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.IHacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.DonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.HacerseCargoDeHeladerasRepositorio;
import vianditasONG.modelos.repositorios.falla_tecnica.IFallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.falla_tecnica.imp.FallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.formularios.IFormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.localidades.ILocalidadesRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.modelos.IModelosRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;
import vianditasONG.modelos.repositorios.ofertas.IOfertasRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.IPersonasEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.imp.PersonaEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.reportes.IReportesRepositorio;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;
import vianditasONG.modelos.repositorios.rubros.IRubrosOfertasRepositorio;
import vianditasONG.modelos.repositorios.rubros.IRubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosOfertasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosPersonaJuridicaRepositorio;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.repositorios.suscripciones.imp.SuscripcionDesperfectosRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasColaboradorRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.ITecnicosRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;
import vianditasONG.modelos.repositorios.usuarios.IUsuariosRepositorio;
import vianditasONG.modelos.repositorios.usuarios.imp.UsuariosRepositorio;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotTelegramAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.reportes.Reporte;
import vianditasONG.modelos.servicios.reportes.TipoReporte;
import vianditasONG.modelos.servicios.seguridad.encriptadores.Encriptador;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorMD5;
import vianditasONG.utils.usuarioRolesYPermisos.Rol;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Initializer implements WithSimplePersistenceUnit {

    /////////////////////////REPOSITORIOS//////////////////////////////
    IOfertasRepositorio repoOfertas = ServiceLocator.getService(OfertasRepositorio.class);
    IRubrosOfertasRepositorio repoRubroOfertas = ServiceLocator.getService(RubrosOfertasRepositorio.class);
    ITarjetasDePersonasVulnerablesRepositorio repoTarjetasPersonasVulnerables = ServiceLocator.getService(TarjetasDePersonasVulnerablesRepositorio.class);
    ITarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = ServiceLocator.getService(TarjetasColaboradorRepositorio.class);
    ILocalidadesRepositorio repoLocalidades = ServiceLocator.getService(LocalidadesRepositorio.class);

    ISensoresDeTemperaturaRepositorio sensoresDeTemperaturaRepositorio = ServiceLocator.getService(SensoresDeTemperaturaRepositorio.class);
    ISensoresDeMovimientoRepositorio sensoresDeMovimientoRepositorio = ServiceLocator.getService(SensoresDeMovimientoRepositorio.class);
    PersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    IPersonasEnSituacionVulnerableRepositorio personasEnSituacionVulnerableRepositorio = ServiceLocator.getService(PersonaEnSituacionVulnerableRepositorio.class);
    IHeladerasRepositorio repoHeladeras = ServiceLocator.getService(HeladerasRepositorio.class);
    IUsuariosRepositorio repoUsuarios = ServiceLocator.getService(UsuariosRepositorio.class);
    Encriptador encriptadorMD5 = ServiceLocator.getService(EncriptadorMD5.class);
    IHumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);
    IReportesRepositorio repoReportes = ServiceLocator.getService(ReportesRepositorio.class);
    IFormulariosRepositorio formulariosRepositorio = ServiceLocator.getService(FormulariosRepositorio.class);
    IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio = ServiceLocator.getService(AccionesSolicitadasRepositorio.class);
    IFallasTecnicasRepositorio fallasTecnicasRepositorio = ServiceLocator.getService(FallasTecnicasRepositorio.class);
    IHacerseCargoDeHeladerasRepositorio hacerseCargoDeHeladerasRepositorio = ServiceLocator.getService(HacerseCargoDeHeladerasRepositorio.class);
    IDonacionesDeViandasRepositorio donacionesDeViandasRepositorio = ServiceLocator.getService(DonacionesDeViandasRepositorio.class);
    IDonacionesDeDineroRepositorio donacionesDeDineroRepositorio = ServiceLocator.getService(DonacionesDeDineroRepositorio.class);

    ITecnicosRepositorio tecnicosRepositorio = ServiceLocator.getService(TecnicosRepositorio.class);

    public static void init() {
        Initializer initializer = new Initializer();
        initializer.persistirEntidades();  // Llamada para persistir las entidades al iniciar
    }

    private void persistirEntidades() {
        /////////////////////////OBJETOS MAS UTILIZADOS//////////////////////////////
        IModelosRepositorio repoModelos = ServiceLocator.getService(ModelosRepositorio.class);
        IRubrosPersonaJuridicaRepositorio repoRubrosPJ = ServiceLocator.getService(RubrosPersonaJuridicaRepositorio.class);
        RubroPersonaJuridica rubroPersonaJuridica = RubroPersonaJuridica.builder().descripcion("Tecnología").build();
        Localidad localidad = Localidad.builder().nombre("Almagro").provincia(Provincia.BUENOS_AIRES).build();
        Direccion direccion = Direccion.builder().calle("Avenida Medrano").altura("1900").localidad(localidad).build();
        Contacto contacto = Contacto.builder().contacto("utnba@frba.utn.edu.ar").tipoDeContacto(TipoDeContacto.CORREO).build();
        FormaDeColaboracion formaDeColaboracion = FormaDeColaboracion.DONACION_VIANDAS;
        List<FormaDeColaboracion> formasDeColaboracion = new ArrayList<>();
        formasDeColaboracion.add(formaDeColaboracion);
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);


        Usuario usuario = Usuario.builder()
                .nombreDeUsuario("Pedrito17")
                .hashContrasenia(encriptadorMD5.encriptar("Disenio17!!a"))
                .rol(Rol.ADMIN)
                .build();

        PersonaJuridica personaJuridicaAPersistir = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.ACEPTADO)
                .fechaDeSolicitud(LocalDateTime.now())
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS))
                .direccion(direccion)
                .razonSocial("UTN")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.INSTITUCION)
                .contactos(contactos)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Educacion").build())
                .build();


        Modelo modeloHeladeraHoneker = Modelo.builder()
                .capacidadMaximaViandas(50)
                .tempMaximaEnGradosCelsius(30f)
                .tempMinimaEnGradosCelsius(-10f)
                .descripcion("Honeker12")
                .build();

        //////////////////////////////LO QUE INTERESA PERSISTIR/////////////////////////////////


        Heladera heladeraMedrano = Heladera.builder()
                .nombre("Heladera Medrano UTN")
                .cantidadViandas(27)
                .puntoEstrategico(PuntoEstrategico.builder()
                        .direccion(Direccion.builder()
                                .altura("951")
                                .calle("Av. Medrano")
                                .localidad(Localidad.builder().nombre("Balvanera").provincia(Provincia.CABA).build())
                                .build())
                        .nombre("Heladera UTN Medrano")
                        .puntoGeografico(PuntoGeografico.builder().latitud(-34.6037).longitud(-58.4206).build())
                        .build())
                .mesesActiva(2)
                .modeloHeladera(modeloHeladeraHoneker)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .diasDesactivada(0)
                .temperaturaDeseadaEnGradosCelsius(10f)
                .ultimaTemperaturaRegistradaEnGradosCelsius(10f)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .fechaUltimoCalculo(LocalDateTime.now())
                .build();


        Heladera heladeraLugano = Heladera.builder()
                .nombre("Heladera Lugano UTN")
                .cantidadViandas(18)
                .puntoEstrategico(PuntoEstrategico.builder()
                        .direccion(Direccion.builder()
                                .altura("2300")
                                .calle("Mozart")
                                .localidad(Localidad.builder().nombre("Lugano").provincia(Provincia.CABA).build())
                                .build())
                        .nombre("Heladera UTN Lugano")
                        .puntoGeografico(PuntoGeografico.builder().latitud(-34.65951).longitud(-58.46789).build())
                        .build())
                .mesesActiva(3)
                .modeloHeladera(modeloHeladeraHoneker)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.BLOQUEADA_POR_FRAUDE)
                .diasDesactivada(7)
                .temperaturaDeseadaEnGradosCelsius(10f)
                .ultimaTemperaturaRegistradaEnGradosCelsius(10f)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .fechaUltimoCalculo(LocalDateTime.now())
                .build();

        Heladera heladeraHaedo = Heladera.builder()
                .nombre("Heladera Haedo UTN")
                .cantidadViandas(26)
                .puntoEstrategico(PuntoEstrategico.builder()
                        .direccion(Direccion.builder()
                                .altura("532")
                                .calle("París")
                                .localidad(Localidad.builder().nombre("Haedo").provincia(Provincia.BUENOS_AIRES).build())
                                .build())
                        .nombre("Heladera UTN Haedo")
                        .puntoGeografico(PuntoGeografico.builder().latitud(-34.64055556).longitud(-58.60194444).build())
                        .build())
                .mesesActiva(2)
                .modeloHeladera(modeloHeladeraHoneker)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .diasDesactivada(0)
                .temperaturaDeseadaEnGradosCelsius(10f)
                .ultimaTemperaturaRegistradaEnGradosCelsius(12f)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .fechaUltimoCalculo(LocalDateTime.now())
                .build();

        Heladera heladeraUTNCordoba = Heladera.builder()
                .nombre("Heladera Cordoba UTN")
                .cantidadViandas(26)
                .puntoEstrategico(PuntoEstrategico.builder()
                        .direccion(Direccion.builder()
                                .altura("245")
                                .calle("Maestro M. Lopez")
                                .localidad(Localidad.builder().nombre("Cdad de Córdoba").provincia(Provincia.CORDOBA).build())
                                .build())
                        .nombre("Heladera UTN Cordoba")
                        .puntoGeografico(PuntoGeografico.builder().latitud(-31.442198889695014).longitud(-64.19319436085152).build())
                        .build())
                .mesesActiva(3)
                .modeloHeladera(modeloHeladeraHoneker)
                .fechaDeRegistro(LocalDateTime.now())
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .diasDesactivada(0)
                .temperaturaDeseadaEnGradosCelsius(10f)
                .ultimaTemperaturaRegistradaEnGradosCelsius(12f)
                .fechaUltimatemperaturaRegistrada(LocalDateTime.now())
                .fechaUltimoCalculo(LocalDateTime.now())
                .build();

        RubroOferta rubroOferta1 = RubroOferta.builder().descripcion("Tecnología").build();
        RubroOferta rubroOferta2 = RubroOferta.builder().descripcion("Autos").build();
        RubroOferta rubroOferta3 = RubroOferta.builder().descripcion("Electrodomésticos").build();


        Oferta ofertaAPersistir1 = Oferta.builder()
                .imagen("img/catalogoOfertas/bicicleta.jpg")
                .rubro(RubroOferta.builder().descripcion("Bicicletas").build())
                .puntosNecesarios(300d)
                .nombre("Bicicleta BMX")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir2 = Oferta.builder()
                .imagen("img/catalogoOfertas/lavarropas.png")
                .rubro(rubroOferta3)
                .puntosNecesarios(500d)
                .nombre("Lavarropas Whirpool Premium")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir3 = Oferta.builder()
                .imagen("img/catalogoOfertas/valePorComida.jpg")
                .rubro(RubroOferta.builder().descripcion("ValesRestaurantes").build())
                .puntosNecesarios(20d)
                .nombre("Vale por cena en Pepitos")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir4 = Oferta.builder()
                .imagen("img/catalogoOfertas/estufa.png")
                .rubro(rubroOferta3)
                .puntosNecesarios(430d)
                .nombre("Estufa eléctrica")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir5 = Oferta.builder()
                .imagen("img/catalogoOfertas/destornillador.jpg")
                .rubro(RubroOferta.builder().descripcion("Herramientas").build())
                .puntosNecesarios(100d)
                .nombre("Vale por cena en Pepitos")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir6 = Oferta.builder()
                .imagen("img/catalogoOfertas/raspberry-pi.jpg")
                .rubro(RubroOferta.builder().descripcion(rubroOferta1.getDescripcion()).build())
                .puntosNecesarios(1000d)
                .nombre("Raspberry Pi 4")
                .ofertante(personaJuridicaAPersistir)
                .build();

        Oferta ofertaAPersistir7 = Oferta.builder()
                .imagen("img/catalogoOfertas/celular.jpg")
                .rubro(RubroOferta.builder().descripcion(rubroOferta1.getDescripcion()).build())
                .puntosNecesarios(1500d)
                .nombre("Celular A57")
                .ofertante(personaJuridicaAPersistir)
                .build();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = PersonaEnSituacionVulnerable.builder()
                .fechaDeNacimiento(LocalDate.of(2003,8,23))
                .fechaDeRegistro(LocalDateTime.now())
                .esAdulto(true)
                .nombre("Pablo")
                .apellido("Perez")
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDocumento("20.943.124")
                .poseeVivienda(false)
                .build();


        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable = TarjetaDePersonaVulnerable.builder()
                .codigo("0000aaaa000")
                .build();

        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable2 = TarjetaDePersonaVulnerable.builder()
                .codigo("0000bbbb000")
                .build();

        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable3 = TarjetaDePersonaVulnerable.builder()
                .codigo("0000cccc000")
                .personaAsociada(personaEnSituacionVulnerable)
                .build();


        Usuario usuarioEmpresa = Usuario.builder()
                .nombreDeUsuario("empresa")
                .hashContrasenia(encriptadorMD5.encriptar("Empresa2024!"))
                .rol(Rol.PERSONA_JURIDICA)
                .build();

        Usuario usuarioEmpresa2 = Usuario.builder()
                .nombreDeUsuario("empresa2")
                .hashContrasenia(encriptadorMD5.encriptar("Empresa2024!"))
                .rol(Rol.PERSONA_JURIDICA)
                .build();

        Usuario usuarioHumano = Usuario.builder()
                .nombreDeUsuario("humano")
                .hashContrasenia(encriptadorMD5.encriptar("Humano2024!"))
                .rol(Rol.PERSONA_HUMANA)
                .build();

        Usuario usuarioHumano2 = Usuario.builder()
                .nombreDeUsuario("humano2")
                .hashContrasenia(encriptadorMD5.encriptar("Humano2024!"))
                .rol(Rol.PERSONA_HUMANA)
                .build();

        Usuario usuarioAdmin = Usuario.builder()
                .nombreDeUsuario("admin")
                .hashContrasenia(encriptadorMD5.encriptar("Admin2024!"))
                .rol(Rol.ADMIN)
                .build();


        Humano humanoUserHumano = Humano.builder()
                .nombre("Juan")
                .apellido("Perez")
                .usuario(usuarioHumano)
                .fechaDeSolicitud(LocalDate.now())
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .estadoDeSolicitud(EstadoDeSolicitud.ACEPTADO)
                .numeroDocumento("49212304")
                .direccion(direccion)
                .puntoGeografico(PuntoGeografico.builder().latitud(-34.6037d).longitud(-58.4206d).build())
                .puntos(26d)
                .build();

        Contacto contacto1 = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("lukitamodric222@gmail.com").build();
        Contacto contacto3 = Contacto.builder().tipoDeContacto(TipoDeContacto.WHATSAPP).contacto("+5491134789345").build();

        Contacto contactoTecnico = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("lukitamodric222@gmail.com").build();

        humanoUserHumano.agregarContacto(contacto1);
        humanoUserHumano.agregarContacto(contacto3);

        Contacto contacto2 = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("lucacesari2002@gmail.com").build();
        humanoUserHumano.agregarContacto(contacto1);

        Contacto contacto4 = Contacto.builder().tipoDeContacto(TipoDeContacto.CORREO).contacto("lucacesari2002@gmail.com").build();

        humanoUserHumano.agregarFormasDeColab(
                FormaDeColaboracion.DONACION_DINERO,
                FormaDeColaboracion.DONACION_VIANDAS,
                FormaDeColaboracion.ENTREGA_TARJETAS);

        Humano humanoUserHumano2 = Humano.builder()
                .nombre("Gonzalo")
                .apellido("Martinez")
                .fechaNacimiento(LocalDate.now())
                .usuario(usuarioHumano2)
                .fechaDeSolicitud(LocalDate.now())
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .estadoDeSolicitud(EstadoDeSolicitud.ACEPTADO)
                .numeroDocumento("49212304")
                .puntos(20000d)
                .build();

        PersonaJuridica personaJuridicaUserEmpresa = PersonaJuridica.builder()
                .razonSocial("UBA")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.EMPRESA)
                .fechaDeSolicitud(LocalDateTime.now())
                .usuario(usuarioEmpresa)
                .estadoDeSolicitud(EstadoDeSolicitud.ACEPTADO)
                .puntos(10d)
                .build();
        personaJuridicaUserEmpresa.agregarFormasDeColab(
                FormaDeColaboracion.DONACION_DINERO);

        personaJuridicaUserEmpresa.agregarContacto(contacto4);

        PersonaJuridica personaJuridicaUserEmpresa2 = PersonaJuridica.builder()
                .razonSocial("Banco Galicia S.A.")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.EMPRESA)
                .fechaDeSolicitud(LocalDateTime.now())
                .usuario(usuarioEmpresa2)
                .estadoDeSolicitud(EstadoDeSolicitud.ACEPTADO)
                .puntos(50000d)
                .direccion(direccion)
                .build();
        personaJuridicaUserEmpresa2.agregarFormasDeColab(
                FormaDeColaboracion.DONACION_DINERO);

        Localidad localidad1 = Localidad.builder()
                .provincia(Provincia.BUENOS_AIRES)
                .nombre("Villa Adelina")
                .build();

        RubroPersonaJuridica rubro1 = RubroPersonaJuridica.builder()
                .descripcion("Comercio")
                .build();

        RubroPersonaJuridica rubro2 = RubroPersonaJuridica.builder()
                .descripcion("Construcción")
                .build();

        RubroPersonaJuridica rubro3 = RubroPersonaJuridica.builder()
                .descripcion("Tecnología e Informática")
                .build();

        RubroPersonaJuridica rubro4 = RubroPersonaJuridica.builder()
                .descripcion("Manufactura e Industria")
                .build();

        RubroPersonaJuridica rubro5 = RubroPersonaJuridica.builder()
                .descripcion("Educación")
                .build();

// Buenos Aires
        Localidad localidadBsAs1 = Localidad.builder()
                .nombre("La Plata")
                .provincia(Provincia.BUENOS_AIRES)
                .build();

        Localidad localidadBsAs2 = Localidad.builder()
                .nombre("Mar del Plata")
                .provincia(Provincia.BUENOS_AIRES)
                .build();

        Localidad localidadBsAs3 = Localidad.builder()
                .nombre("Bahía Blanca")
                .provincia(Provincia.BUENOS_AIRES)
                .build();

// CABA
        Localidad localidadCABA1 = Localidad.builder()
                .nombre("Almagro")
                .provincia(Provincia.CABA)
                .build();

        Localidad localidadCABA2 = Localidad.builder()
                .nombre("Palermo")
                .provincia(Provincia.CABA)
                .build();

        Localidad localidadCABA3 = Localidad.builder()
                .nombre("Recoleta")
                .provincia(Provincia.CABA)
                .build();

// Catamarca
        Localidad localidadCatamarca1 = Localidad.builder()
                .nombre("San Fernando del Valle de Catamarca")
                .provincia(Provincia.CATAMARCA)
                .build();

        Localidad localidadCatamarca2 = Localidad.builder()
                .nombre("Belén")
                .provincia(Provincia.CATAMARCA)
                .build();

        Localidad localidadCatamarca3 = Localidad.builder()
                .nombre("Santa María")
                .provincia(Provincia.CATAMARCA)
                .build();

// Chaco
        Localidad localidadChaco1 = Localidad.builder()
                .nombre("Resistencia")
                .provincia(Provincia.CHACO)
                .build();

        Localidad localidadChaco2 = Localidad.builder()
                .nombre("Sáenz Peña")
                .provincia(Provincia.CHACO)
                .build();

        Localidad localidadChaco3 = Localidad.builder()
                .nombre("Villa Ángela")
                .provincia(Provincia.CHACO)
                .build();

// Chubut
        Localidad localidadChubut1 = Localidad.builder()
                .nombre("Trelew")
                .provincia(Provincia.CHUBUT)
                .build();

        Localidad localidadChubut2 = Localidad.builder()
                .nombre("Comodoro Rivadavia")
                .provincia(Provincia.CHUBUT)
                .build();

        Localidad localidadChubut3 = Localidad.builder()
                .nombre("Puerto Madryn")
                .provincia(Provincia.CHUBUT)
                .build();

// Córdoba
        Localidad localidadCordoba1 = Localidad.builder()
                .nombre("Córdoba")
                .provincia(Provincia.CORDOBA)
                .build();

        Localidad localidadCordoba2 = Localidad.builder()
                .nombre("Villa Carlos Paz")
                .provincia(Provincia.CORDOBA)
                .build();

        Localidad localidadCordoba3 = Localidad.builder()
                .nombre("Río Cuarto")
                .provincia(Provincia.CORDOBA)
                .build();

// Corrientes
        Localidad localidadCorrientes1 = Localidad.builder()
                .nombre("Corrientes")
                .provincia(Provincia.CORRIENTES)
                .build();

        Localidad localidadCorrientes2 = Localidad.builder()
                .nombre("Goya")
                .provincia(Provincia.CORRIENTES)
                .build();

        Localidad localidadCorrientes3 = Localidad.builder()
                .nombre("Paso de los Libres")
                .provincia(Provincia.CORRIENTES)
                .build();

// Entre Ríos
        Localidad localidadEntreRios1 = Localidad.builder()
                .nombre("Paraná")
                .provincia(Provincia.ENTRE_RIOS)
                .build();

        Localidad localidadEntreRios2 = Localidad.builder()
                .nombre("Concordia")
                .provincia(Provincia.ENTRE_RIOS)
                .build();

        Localidad localidadEntreRios3 = Localidad.builder()
                .nombre("Gualeguaychú")
                .provincia(Provincia.ENTRE_RIOS)
                .build();

// Formosa
        Localidad localidadFormosa1 = Localidad.builder()
                .nombre("Formosa")
                .provincia(Provincia.FORMOSA)
                .build();

        Localidad localidadFormosa2 = Localidad.builder()
                .nombre("Clorinda")
                .provincia(Provincia.FORMOSA)
                .build();

        Localidad localidadFormosa3 = Localidad.builder()
                .nombre("Pirané")
                .provincia(Provincia.FORMOSA)
                .build();

// Jujuy
        Localidad localidadJujuy1 = Localidad.builder()
                .nombre("San Salvador de Jujuy")
                .provincia(Provincia.JUJUY)
                .build();

        Localidad localidadJujuy2 = Localidad.builder()
                .nombre("Palpalá")
                .provincia(Provincia.JUJUY)
                .build();

        Localidad localidadJujuy3 = Localidad.builder()
                .nombre("Perico")
                .provincia(Provincia.JUJUY)
                .build();

// La Pampa
        Localidad localidadLaPampa1 = Localidad.builder()
                .nombre("Santa Rosa")
                .provincia(Provincia.LA_PAMPA)
                .build();

        Localidad localidadLaPampa2 = Localidad.builder()
                .nombre("General Pico")
                .provincia(Provincia.LA_PAMPA)
                .build();

        Localidad localidadLaPampa3 = Localidad.builder()
                .nombre("Toay")
                .provincia(Provincia.LA_PAMPA)
                .build();

// La Rioja
        Localidad localidadLaRioja1 = Localidad.builder()
                .nombre("La Rioja")
                .provincia(Provincia.LA_RIOJA)
                .build();

        Localidad localidadLaRioja2 = Localidad.builder()
                .nombre("Chilecito")
                .provincia(Provincia.LA_RIOJA)
                .build();

        Localidad localidadLaRioja3 = Localidad.builder()
                .nombre("Aimogasta")
                .provincia(Provincia.LA_RIOJA)
                .build();

// Mendoza
        Localidad localidadMendoza1 = Localidad.builder()
                .nombre("Mendoza")
                .provincia(Provincia.MENDOZA)
                .build();

        Localidad localidadMendoza2 = Localidad.builder()
                .nombre("San Rafael")
                .provincia(Provincia.MENDOZA)
                .build();

        Localidad localidadMendoza3 = Localidad.builder()
                .nombre("Godoy Cruz")
                .provincia(Provincia.MENDOZA)
                .build();

// Misiones
        Localidad localidadMisiones1 = Localidad.builder()
                .nombre("Posadas")
                .provincia(Provincia.MISIONES)
                .build();

        Localidad localidadMisiones2 = Localidad.builder()
                .nombre("Oberá")
                .provincia(Provincia.MISIONES)
                .build();

        Localidad localidadMisiones3 = Localidad.builder()
                .nombre("Eldorado")
                .provincia(Provincia.MISIONES)
                .build();

// Neuquén
        Localidad localidadNeuquen1 = Localidad.builder()
                .nombre("Neuquén")
                .provincia(Provincia.NEUQUEN)
                .build();

        Localidad localidadNeuquen2 = Localidad.builder()
                .nombre("San Martín de los Andes")
                .provincia(Provincia.NEUQUEN)
                .build();

        Localidad localidadNeuquen3 = Localidad.builder()
                .nombre("Plottier")
                .provincia(Provincia.NEUQUEN)
                .build();

// Río Negro
        Localidad localidadRioNegro1 = Localidad.builder()
                .nombre("San Carlos de Bariloche")
                .provincia(Provincia.RIO_NEGRO)
                .build();

        Localidad localidadRioNegro2 = Localidad.builder()
                .nombre("General Roca")
                .provincia(Provincia.RIO_NEGRO)
                .build();

        Localidad localidadRioNegro3 = Localidad.builder()
                .nombre("Viedma")
                .provincia(Provincia.RIO_NEGRO)
                .build();

// Salta
        Localidad localidadSalta1 = Localidad.builder()
                .nombre("Salta")
                .provincia(Provincia.SALTA)
                .build();

        Localidad localidadSalta2 = Localidad.builder()
                .nombre("Tartagal")
                .provincia(Provincia.SALTA)
                .build();

        Localidad localidadSalta3 = Localidad.builder()
                .nombre("Orán")
                .provincia(Provincia.SALTA)
                .build();

// San Juan
        Localidad localidadSanJuan1 = Localidad.builder()
                .nombre("San Juan")
                .provincia(Provincia.SAN_JUAN)
                .build();

        Localidad localidadSanJuan2 = Localidad.builder()
                .nombre("Rivadavia")
                .provincia(Provincia.SAN_JUAN)
                .build();

        Localidad localidadSanJuan3 = Localidad.builder()
                .nombre("Chimbas")
                .provincia(Provincia.SAN_JUAN)
                .build();

// San Luis
        Localidad localidadSanLuis1 = Localidad.builder()
                .nombre("San Luis")
                .provincia(Provincia.SAN_LUIS)
                .build();

        Localidad localidadSanLuis2 = Localidad.builder()
                .nombre("Villa Mercedes")
                .provincia(Provincia.SAN_LUIS)
                .build();

        Localidad localidadSanLuis3 = Localidad.builder()
                .nombre("Merlo")
                .provincia(Provincia.SAN_LUIS)
                .build();

// Santa Cruz
        Localidad localidadSantaCruz1 = Localidad.builder()
                .nombre("Río Gallegos")
                .provincia(Provincia.SANTA_CRUZ)
                .build();

        Localidad localidadSantaCruz2 = Localidad.builder()
                .nombre("El Calafate")
                .provincia(Provincia.SANTA_CRUZ)
                .build();

        Localidad localidadSantaCruz3 = Localidad.builder()
                .nombre("Puerto Deseado")
                .provincia(Provincia.SANTA_CRUZ)
                .build();

// Santa Fe
        Localidad localidadSantaFe1 = Localidad.builder()
                .nombre("Santa Fe")
                .provincia(Provincia.SANTA_FE)
                .build();

        Localidad localidadSantaFe2 = Localidad.builder()
                .nombre("Rosario")
                .provincia(Provincia.SANTA_FE)
                .build();

        Localidad localidadSantaFe3 = Localidad.builder()
                .nombre("Rafaela")
                .provincia(Provincia.SANTA_FE)
                .build();

// Santiago del Estero
        Localidad localidadSantiago1 = Localidad.builder()
                .nombre("Santiago del Estero")
                .provincia(Provincia.SANTIAGO_DEL_ESTERO)
                .build();

        Localidad localidadSantiago2 = Localidad.builder()
                .nombre("La Banda")
                .provincia(Provincia.SANTIAGO_DEL_ESTERO)
                .build();

        Localidad localidadSantiago3 = Localidad.builder()
                .nombre("Termas de Río Hondo")
                .provincia(Provincia.SANTIAGO_DEL_ESTERO)
                .build();

// Tierra del Fuego
        Localidad localidadTierraFuego1 = Localidad.builder()
                .nombre("Ushuaia")
                .provincia(Provincia.TIERRA_DEL_FUEGO)
                .build();

        Localidad localidadTierraFuego2 = Localidad.builder()
                .nombre("Rio Grande")
                .provincia(Provincia.TIERRA_DEL_FUEGO)
                .build();

        Localidad localidadTierraFuego3 = Localidad.builder()
                .nombre("Tolhuin")
                .provincia(Provincia.TIERRA_DEL_FUEGO)
                .build();

// Tucumán
        Localidad localidadTucuman1 = Localidad.builder()
                .nombre("San Miguel de Tucumán")
                .provincia(Provincia.TUCUMAN)
                .build();

        Localidad localidadTucuman2 = Localidad.builder()
                .nombre("Tafí Viejo")
                .provincia(Provincia.TUCUMAN)
                .build();

        Localidad localidadTucuman3 = Localidad.builder()
                .nombre("Concepción")
                .provincia(Provincia.TUCUMAN)
                .build();

        SensorDeMovimiento sensorDeMovimiento1 = SensorDeMovimiento.builder()
                .heladera(heladeraHaedo)
                .build();

        SensorDeMovimiento sensorDeMovimiento2 = SensorDeMovimiento.builder()
                .heladera(heladeraLugano)
                .build();

        SensorDeMovimiento sensorDeMovimiento3 = SensorDeMovimiento.builder()
                .heladera(heladeraMedrano)
                .build();

        SensorDeMovimiento sensorDeMovimiento4 = SensorDeMovimiento.builder()
                .heladera(heladeraUTNCordoba)
                .build();

        SensorDeTemperatura sensorDeTemperatura1 = SensorDeTemperatura.builder()
                .heladera(heladeraHaedo)
                .build();

        SensorDeTemperatura sensorDeTemperatura2 = SensorDeTemperatura.builder()
                .heladera(heladeraLugano)
                .build();

        SensorDeTemperatura sensorDeTemperatura3 = SensorDeTemperatura.builder()
                .heladera(heladeraMedrano)
                .build();

        SensorDeTemperatura sensorDeTemperatura4 = SensorDeTemperatura.builder()
                .heladera(heladeraUTNCordoba)
                .build();


        Opcion opcionDinamica1 = Opcion.builder()
                .descripcion("Colaborar")
                .build();

        Opcion opcionDinamica2 = Opcion.builder()
                .descripcion("Desarrollar")
                .build();

        Opcion opcionDinamica3 = Opcion.builder()
                .descripcion("Vender")
                .build();


        Pregunta preguntaDinamica1 = Pregunta.builder()
                .enunciado("Inspiraciones para contribuir")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(Boolean.TRUE)
                .placeholder("Ingrese sus inspiraciones")
                .build();

        Pregunta preguntaDinamica3 = Pregunta.builder()
                .enunciado("Como descubrió la ONG")
                .tipo(TipoPregunta.MULTIPLE_CHOICE)
                .esEsencial(Boolean.FALSE)
                .placeholder("Seleccione las formas de descubrimiento")
                .opciones(List.of(Opcion.builder().descripcion("Me la mostró un amigo").build(),
                        Opcion.builder().descripcion("Vi sus obras beneficas").build(),
                        Opcion.builder().descripcion("Publicidad Online").build()
                ))
                .build();

        Pregunta preguntaDinamica5 = Pregunta.builder()
                .enunciado("¿Para que desea realizar esto?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(Boolean.FALSE)
                .placeholder("Ingrese su motivo")
                .build();

        Pregunta preguntaDinamica4 = Pregunta.builder()
                .enunciado("Intención en el sistema")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .opciones(List.of(opcionDinamica1, opcionDinamica2, opcionDinamica3))
                .esEsencial(Boolean.TRUE)
                .placeholder("Ingrese su intención")
                .esEstatica(false)
                .build();

        Pregunta preguntaDinamica6 = Pregunta.builder()
                .enunciado("Información adicional")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(Boolean.FALSE)
                .placeholder("Ingrese la información que considere apropiada")
                .build();

        Pregunta preguntaDinamica7 = Pregunta.builder()
                .enunciado("DNI")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEsencial(Boolean.FALSE)
                .placeholder("Ingrese el numero de DNI")
                .build();

        Pregunta preguntaDinamica8 = Pregunta.builder()
                .enunciado("Fecha de creación de la organización")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.DATE)
                .esEsencial(Boolean.FALSE)
                .build();

        Pregunta preguntaDinamicaPJ = Pregunta.builder()
                .enunciado("CUIT")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .placeholder("Ingrese numero de CUIT")
                .esEsencial(Boolean.FALSE)
                .esEstatica(false)
                .build();

        Pregunta pregunta1 = Pregunta.builder()
                .enunciado("¿Cuál es tu principal motivación para colaborar con nuestra ONG?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(true)
                .placeholder("Describe tu motivación")
                .build();

        Pregunta pregunta2 = Pregunta.builder()
                .enunciado("¿Cuántas horas semanales podrías dedicar?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEsencial(true)
                .placeholder("Ingrese el número de horas")
                .build();

        Pregunta pregunta3 = Pregunta.builder()
                .enunciado("¿Tienes experiencia previa en voluntariado?")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .opciones(List.of(
                        Opcion.builder().descripcion("Sí").build(),
                        Opcion.builder().descripcion("No").build()
                ))
                .esEsencial(true)
                .placeholder("Seleccione una opción")
                .build();

        Pregunta pregunta4 = Pregunta.builder()
                .enunciado("¿Qué habilidades específicas podrías aportar?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(false)
                .placeholder("Describe tus habilidades")
                .build();

        Pregunta preguntaDinaHum1 = Pregunta.builder()
                .enunciado("¿Tienes algún requisito especial?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(false)
                .esEstatica(false)
                .placeholder("Escribe tus requisitos si los tienes")
                .build();

        Pregunta HumEstat1 = Pregunta.builder()
                .enunciado("Nombre")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese su nombre")
                .build();

        Pregunta HumEstat2 = Pregunta.builder()
                .enunciado("Apellido")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese su apellido")
                .build();

        Pregunta HumEstat3 = Pregunta.builder()
                .enunciado("Fecha de nacimiento")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.DATE)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("dd/mm/aaaa")
                .build();

        Pregunta HumEstat4 = Pregunta.builder()
                .enunciado("Provincia")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Seleccione la provincia de su domicilio")
                .build();

        Pregunta HumEstat5 = Pregunta.builder()
                .enunciado("Localidad")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Seleccione la localidad de su domicilio")
                .build();

        Pregunta HumEstat6 = Pregunta.builder()
                .enunciado("Calle")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Ingrese la calle de su domicilio")
                .build();

        Pregunta HumEstat7 = Pregunta.builder()
                .enunciado("Altura")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Ingrese la altura de su domicilio")
                .build();

        Pregunta HumEstat8 = Pregunta.builder()
                .enunciado("Formas de colaboración")
                .tipo(TipoPregunta.MULTIPLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Seleccione su forma de colaboracion")
                .build();

        HumEstat8.agregarOpciones(Opcion.builder().descripcion("Donacion de dinero").build());
        HumEstat8.agregarOpciones((Opcion.builder().descripcion("Donacion de viandas").build()));
        HumEstat8.agregarOpciones((Opcion.builder().descripcion("Distribución de viandas").build()));
        HumEstat8.agregarOpciones(Opcion.builder().descripcion("Entrega de tarjetas").build());

        Pregunta HumEstat9 = Pregunta.builder()
                .enunciado("Contactos")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEL)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese sus contactos")
                .build();

        Pregunta PJEstat1 = Pregunta.builder()
                .enunciado("Razon Social")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese la razon social")
                .build();

        Pregunta PJEstat2 = Pregunta.builder()
                .enunciado("Rubro")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese el rubro")
                .build();

        Pregunta PJEstat3 = Pregunta.builder()
                .enunciado("Tipo de organización")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("dd/mm/aaaa")
                .build();

        PJEstat3.agregarOpciones(Opcion.builder().descripcion("Gubernamental").build());
        PJEstat3.agregarOpciones((Opcion.builder().descripcion("ONG").build()));
        PJEstat3.agregarOpciones((Opcion.builder().descripcion("Empresa").build()));
        PJEstat3.agregarOpciones(Opcion.builder().descripcion("Institución").build());

        Pregunta PJEstat4 = Pregunta.builder()
                .enunciado("Provincia")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Seleccione la provincia de su domicilio")
                .build();

        Pregunta PJEstat5 = Pregunta.builder()
                .enunciado("Localidad")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Seleccione la localidad de su domicilio")
                .build();

        Pregunta PJEstat6 = Pregunta.builder()
                .enunciado("Calle")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Ingrese la calle de su domicilio")
                .build();

        Pregunta PJEstat7 = Pregunta.builder()
                .enunciado("Altura")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .placeholder("Ingrese la altura de su domicilio")
                .build();

        Pregunta PJEstat8 = Pregunta.builder()
                .enunciado("Formas de colaboración")
                .tipo(TipoPregunta.MULTIPLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Seleccione su forma de colaboracion")
                .build();

        PJEstat8.agregarOpciones(Opcion.builder().descripcion("Donacion de dinero").build());
        PJEstat8.agregarOpciones((Opcion.builder().descripcion("Hacerse cargo de una heladera").build()));
        PJEstat8.agregarOpciones((Opcion.builder().descripcion("Oferta de productos y servicios").build()));

        Pregunta pregDineroEstat1 = Pregunta.builder()
                .enunciado("Monto")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese el monto a donar")
                .build();

        Pregunta pregDineroEstat2 = Pregunta.builder()
                .enunciado("Frecuencia")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Seleccione su forma de colaboracion")
                .build();

        pregDineroEstat2.agregarOpciones(Opcion.builder().descripcion("Sólo por esta vez").build());
        pregDineroEstat2.agregarOpciones((Opcion.builder().descripcion("Cada una semana").build()));
        pregDineroEstat2.agregarOpciones((Opcion.builder().descripcion("Cada tres semanas").build()));
        pregDineroEstat2.agregarOpciones((Opcion.builder().descripcion("Cada cuatro semanas").build()));

        Pregunta pregDonViandaEstat1 = Pregunta.builder()
                .enunciado("Heladera")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese la heladera destino")
                .build();

        Pregunta pregDonViandaEstat2 = Pregunta.builder()
                .enunciado("Dirección de la heladera")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese la heladera destino")
                .build();

        Pregunta pregDonViandaEstat3 = Pregunta.builder()
                .enunciado("Comida")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDonViandaEstat4 = Pregunta.builder()
                .enunciado("Calorias")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregDonViandaEstat5 = Pregunta.builder()
                .enunciado("Fecha de caducidad")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.DATE)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDonViandaEstat6 = Pregunta.builder()
                .enunciado("Peso")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat1 = Pregunta.builder()
                .enunciado("Cantidad de Viandas")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat2 = Pregunta.builder()
                .enunciado("Motivo de distribución")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat3 = Pregunta.builder()
                .enunciado("Heladera Origen")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat4 = Pregunta.builder()
                .enunciado("Dirección heladera origen")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat5 = Pregunta.builder()
                .enunciado("Heladera Destino")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregDistViandaEstat6 = Pregunta.builder()
                .enunciado("Dirección heladera Destino")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregCanjeEstat1 = Pregunta.builder()
                .enunciado("Descripcion")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregCanjeEstat2 = Pregunta.builder()
                .enunciado("Rubro")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregCanjeEstat3 = Pregunta.builder()
                .enunciado("Rubro")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregCanjeEstat4 = Pregunta.builder()
                .enunciado("Rubro")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        pregDineroEstat2.agregarOpciones(Opcion.builder().descripcion("Productos").build());
        pregDineroEstat2.agregarOpciones((Opcion.builder().descripcion("Servicios").build()));


        Pregunta pregCargoHeladeraEstat1 = Pregunta.builder()
                .enunciado("Modelo Heladera")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();


        Pregunta pregCargoHeladeraEstat2 = Pregunta.builder()
                .enunciado("Nombre Heladera")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregCargoHeladeraEstat3 = Pregunta.builder()
                .enunciado("Dirección de la Heladera")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();


        Pregunta pregMenorVuln1 = Pregunta.builder()
                .enunciado("Nombre")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregMenorVuln2 = Pregunta.builder()
                .enunciado("Apellido")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregMenorVuln3 = Pregunta.builder()
                .enunciado("Fecha de Nacimiento")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.DATE)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregMenorVuln4 = Pregunta.builder()
                .enunciado("Tipo de Documento")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        pregMenorVuln4.agregarOpciones(Opcion.builder().descripcion("DNI").build());
        pregMenorVuln4.agregarOpciones((Opcion.builder().descripcion("Libreta Cívica").build()));
        pregMenorVuln4.agregarOpciones((Opcion.builder().descripcion("Libreta de Enrolamiento").build()));


        Pregunta pregMenorVuln5= Pregunta.builder()
                .enunciado("Numero de documento")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregPersoVuln1 = Pregunta.builder()
                .enunciado("Nombre")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregPersoVuln2 = Pregunta.builder()
                .enunciado("Apellido")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregPersoVuln3 = Pregunta.builder()
                .enunciado("Fecha de Nacimiento")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.DATE)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregPersoVuln4 = Pregunta.builder()
                .enunciado("Tipo de Documento")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        pregPersoVuln4.agregarOpciones(Opcion.builder().descripcion("DNI").build());
        pregPersoVuln4.agregarOpciones((Opcion.builder().descripcion("Libreta Cívica").build()));
        pregPersoVuln4.agregarOpciones((Opcion.builder().descripcion("Libreta de Enrolamiento").build()));

        Pregunta pregPersoVuln5 = Pregunta.builder()
                .enunciado("Número de documento")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregPersoVuln6 = Pregunta.builder()
                .enunciado("Provincia")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregPersoVuln7 = Pregunta.builder()
                .enunciado("Localidad")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregPersoVuln8 = Pregunta.builder()
                .enunciado("Calle")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregPersoVuln9 = Pregunta.builder()
                .enunciado("Altura")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEstatica(true)
                .esEsencial(false)
                .build();

        Pregunta pregPersoVuln10 = Pregunta.builder()
                .enunciado("Código de Tarjeta")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(true)
                .build();

        Pregunta pregPersoVuln11 = Pregunta.builder()
                .enunciado("Cantidad de menores a cargo")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEstatica(true)
                .esEsencial(false)
                .build();



        Pregunta PJEstat9 = Pregunta.builder()
                .enunciado("Contactos")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEL)
                .esEstatica(true)
                .esEsencial(true)
                .placeholder("Ingrese sus contactos")
                .build();

        Pregunta pregunta1PJ = Pregunta.builder()
                .enunciado("¿Cuál es la principal motivación de su organización para colaborar con nuestra ONG?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(true)
                .placeholder("Describe la motivación de su organización")
                .build();

        Pregunta pregunta2PJ = Pregunta.builder()
                .enunciado("¿Cuántas horas de voluntariado podría ofrecer su organización por semana?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.NUMBER)
                .esEsencial(true)
                .placeholder("Ingrese el número de horas")
                .build();

        Pregunta pregunta3PJ = Pregunta.builder()
                .enunciado("¿Su organización tiene experiencia previa en colaboraciones similares?")
                .tipo(TipoPregunta.SINGLE_CHOICE)
                .opciones(List.of(
                        Opcion.builder().descripcion("Sí").build(),
                        Opcion.builder().descripcion("No").build()
                ))
                .esEsencial(true)
                .placeholder("Seleccione una opción")
                .build();

        Pregunta pregunta4PJ = Pregunta.builder()
                .enunciado("¿Qué recursos específicos podría aportar su organización?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(false)
                .placeholder("Describe los recursos que puede aportar")
                .build();

        Pregunta pregunta5PJ = Pregunta.builder()
                .enunciado("¿Tiene algún requisito o consideración especial para colaborar?")
                .tipo(TipoPregunta.PREGUNTA_A_DESARROLLAR)
                .tipoEntrada(TipoEntradaPregunta.TEXT)
                .esEsencial(false)
                .placeholder("Escriba sus requisitos si los tiene")
                .build();

        Formulario formHumano = Formulario.builder()
                .preguntas(List.of(HumEstat1, HumEstat2, HumEstat3, HumEstat4, HumEstat5, HumEstat6, HumEstat7, HumEstat8, HumEstat9, preguntaDinaHum1))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormHumano"))
                .build();

        Formulario formPersoJuridica = Formulario.builder()
                .preguntas(List.of(PJEstat1, PJEstat2, PJEstat3, PJEstat4, PJEstat5, PJEstat6, PJEstat7, PJEstat8, PJEstat9, preguntaDinamicaPJ))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormPersonasJuridicas"))
                .build();

        Formulario formDonacionDeDinero = Formulario.builder()
                .preguntas(List.of(pregDineroEstat1, pregDineroEstat2))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormDonacionDeDinero"))
                .build();

        Formulario formDistribViandas = Formulario.builder()
                .preguntas(List.of(pregDistViandaEstat1, pregDistViandaEstat2, pregDistViandaEstat3, pregDistViandaEstat4, pregDistViandaEstat5, pregDistViandaEstat6))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormDistribucionDeViandas"))
                .build();

        Formulario formDonacionViandas = Formulario.builder()
                .preguntas(List.of(pregDonViandaEstat1, pregDonViandaEstat2, pregDonViandaEstat3, pregDonViandaEstat4, pregDonViandaEstat5, pregDonViandaEstat6))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormDonacionDeViandas"))
                .build();

        Formulario formPersonaVulnerable = Formulario.builder()
                .preguntas(List.of(pregPersoVuln1, pregPersoVuln2, pregPersoVuln3, pregPersoVuln4, pregPersoVuln5, pregPersoVuln6, pregPersoVuln7, pregPersoVuln8, pregPersoVuln9, pregPersoVuln10, pregPersoVuln11))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormPersonaVulnerable"))
                .build();

        Formulario formMenorVulnerable = Formulario.builder()
                .preguntas(List.of(pregMenorVuln1, pregMenorVuln2, pregMenorVuln3, pregMenorVuln4, pregMenorVuln5))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormMenorVulnerable"))
                .build();

        Formulario formOfrecerCanje = Formulario.builder()
                .preguntas(List.of(pregCanjeEstat1, pregCanjeEstat2, pregCanjeEstat3, pregCanjeEstat4))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormCanje"))
                .build();

        Formulario formHacerseCargoDeHeladera = Formulario.builder()
                .preguntas(List.of(pregCargoHeladeraEstat1, pregCargoHeladeraEstat2, pregCargoHeladeraEstat3))
                .nombre(Config.getInstancia().obtenerDelConfig("nombreFormHacerseCargoDeHeladera"))
                .build();

        FormularioRespondido formRespondidoHumano1 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(Respuesta.builder().preguntaAsociada(preguntaDinamica1).respuestaLibre("Contribuir a un mundo mejor").build(), Respuesta.builder().preguntaAsociada(preguntaDinamica6).respuestaLibre("Me gustaria implementar nuevas formas de colaborar").build()))
                .build();

        FormularioRespondido formRespondidoHumano2 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(Respuesta.builder().preguntaAsociada(preguntaDinamica1).respuestaLibre("Contribuir a un mundo mejor").build(), Respuesta.builder().preguntaAsociada(preguntaDinamica6).respuestaLibre("Me gustaria implementar nuevas formas de colaborar").build(), Respuesta.builder().preguntaAsociada(preguntaDinamica7).respuestaLibre("45315429").build()))
                .build();


        FormularioRespondido formRespondidoPJ1 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(Respuesta.builder().preguntaAsociada(preguntaDinamica1).respuestaLibre("Contribuir a un mundo mejor").build(), Respuesta.builder().preguntaAsociada(preguntaDinamica6).respuestaLibre("Me gustaria implementar nuevas formas de colaborar").build()))
                .build();

        FormularioRespondido formRespondidoHumano11 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1).respuestaLibre("Quiero contribuir al desarrollo social de mi comunidad.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2).respuestaLibre("10").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("Sí").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4).respuestaLibre("Soy buena en organización de eventos y tengo habilidades de comunicación.").build()
                ))
                .build();

        FormularioRespondido formRespondidoHumano22 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1).respuestaLibre("Deseo ayudar a personas en situaciones vulnerables.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2).respuestaLibre("5").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("Sí").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4).respuestaLibre("Soy médico, puedo ofrecer atención de salud básica.").build()))
                .build();

        FormularioRespondido formRespondidoHumano3 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1).respuestaLibre("Me interesa apoyar a la ONG en su misión de sostenibilidad.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2).respuestaLibre("8").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("No").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4).respuestaLibre("Tengo experiencia en reciclaje y manejo de residuos.").build())
                )
                .build();

        FormularioRespondido formRespondidoHumano4 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formHumano)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1).respuestaLibre("Quiero colaborar en proyectos educativos para niños.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2).respuestaLibre("4").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("No").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4).respuestaLibre("Tengo conocimientos en pedagogía y educación infantil.").build()
                ))
                .build();

        FormularioRespondido formRespondidoPJ3 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1PJ).respuestaLibre("Nuestra misión es contribuir al bienestar social.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2PJ).respuestaLibre("10").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3PJ).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("Sí").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4PJ).respuestaLibre("Podemos ofrecer instalaciones y recursos financieros.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta5PJ).respuestaLibre("Preferimos colaborar en horarios de oficina.").build()
                ))
                .build();

        FormularioRespondido formRespondidoPJ4 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1PJ).respuestaLibre("Queremos mejorar nuestra imagen corporativa mediante acciones sociales.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2PJ).respuestaLibre("15").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3PJ).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("No").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4PJ).respuestaLibre("Contamos con un equipo de voluntarios capacitados.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta5PJ).respuestaLibre("Necesitamos un acuerdo de colaboración formal.").build()
                ))
                .build();

        FormularioRespondido formRespondidoPJ5 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1PJ).respuestaLibre("Queremos involucrarnos en la comunidad y contribuir a causas sociales.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2PJ).respuestaLibre("20").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3PJ).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("Sí").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4PJ).respuestaLibre("Podemos ofrecer financiamiento y materiales para proyectos.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta5PJ).respuestaLibre("Preferimos trabajar en iniciativas relacionadas con la educación.").build()
                ))
                .build();

        FormularioRespondido formRespondidoPJ6 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1PJ).respuestaLibre("Nuestro objetivo es generar un impacto positivo en la comunidad.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2PJ).respuestaLibre("12").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3PJ).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("Sí").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4PJ).respuestaLibre("Contamos con expertos en sostenibilidad y medio ambiente.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta5PJ).respuestaLibre("Requerimos una comunicación fluida y transparente.").build()
                ))
                .build();

        FormularioRespondido formRespondidoPJ7 = FormularioRespondido.builder()
                .fechaRealizacion(LocalDate.now())
                .formularioAsociado(formPersoJuridica)
                .listaRespuestas(List.of(
                        Respuesta.builder().preguntaAsociada(pregunta1PJ).respuestaLibre("Queremos colaborar para apoyar a comunidades vulnerables.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta2PJ).respuestaLibre("25").build(),
                        Respuesta.builder().preguntaAsociada(pregunta3PJ).opcionesSeleccionadas(List.of(Opcion.builder().descripcion("No").build())).build(),
                        Respuesta.builder().preguntaAsociada(pregunta4PJ).respuestaLibre("Podemos ofrecer materiales de construcción y apoyo logístico.").build(),
                        Respuesta.builder().preguntaAsociada(pregunta5PJ).respuestaLibre("Deseamos firmar un convenio de colaboración.").build()
                ))
                .build();

        Humano humanoSolicitud0 = Humano.builder()
                .nombre("Alfredo")
                .apellido("Rodriguez")
                .fechaDeSolicitud(LocalDate.now())
                .fechaNacimiento(LocalDate.of(2003, 10, 23))
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(Direccion.builder().calle("Colonia").altura("3033").localidad(localidadCatamarca1).build())
                .contactos(List.of(Contacto.builder().contacto("1138085884").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.ENTREGA_TARJETAS))
                .formularioRespondido(formRespondidoHumano11)
                .build();

        Humano humanoSolicitud2 = Humano.builder()
                .nombre("Carla")
                .apellido("Mendez")
                .fechaDeSolicitud(LocalDate.now())
                .fechaNacimiento(LocalDate.of(1998, 7, 14))
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(Direccion.builder().calle("San Martin").altura("556").localidad(localidadMendoza2).build())
                .contactos(List.of(Contacto.builder().contacto("carlaMendez777@gmail.com").tipoDeContacto(TipoDeContacto.CORREO).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.DONACION_VIANDAS))
                .formularioRespondido(formRespondidoHumano22)
                .build();

        Humano humanoSolicitud3 = Humano.builder()
                .nombre("Roberto")
                .apellido("Fernandez")
                .fechaDeSolicitud(LocalDate.now())
                .fechaNacimiento(LocalDate.of(1985, 4, 20))
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(Direccion.builder().calle("Belgrano").altura("1289").localidad(localidadBsAs1).build())
                .contactos(List.of(Contacto.builder().contacto("1145678901").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.ENTREGA_TARJETAS))
                .formularioRespondido(formRespondidoHumano3)
                .build();

        Humano humanoSolicitud4 = Humano.builder()
                .nombre("Sofia")
                .apellido("Lopez")
                .fechaDeSolicitud(LocalDate.now())
                .fechaNacimiento(LocalDate.of(2001, 12, 1))
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(Direccion.builder().calle("Rivadavia").altura("102").localidad(localidadCordoba1).build())
                .contactos(List.of(Contacto.builder().contacto("1198765432").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.DISTRIBUCION_VIANDAS))
                .formularioRespondido(formRespondidoHumano4)
                .build();


        Humano humanoSolicitud1 = Humano.builder().nombre("Alfredo")
                .apellido("Rodriguez")
                .fechaDeSolicitud(LocalDate.now())
                .fechaNacimiento(LocalDate.of(2003, 10, 23))
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .direccion(Direccion.builder().calle("Colonia").altura("3033").localidad(localidadCatamarca1).build())
                .contactos(List.of(Contacto.builder().contacto("1138085884").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.ENTREGA_TARJETAS))
                .formularioRespondido(formRespondidoHumano1)
                .build();

        Humano humanoSolicitud6 = Humano.builder().nombre("Lebron")
                .apellido("James")
                .fechaDeSolicitud(LocalDate.now())
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaNacimiento(LocalDate.of(2007, 8, 16))
                .direccion(Direccion.builder().calle("Pehuajo").altura("201").localidad(localidadChaco2).build())
                .contactos(List.of(Contacto.builder().contacto("6190209796").tipoDeContacto(TipoDeContacto.TELEGRAM).build()))
                .formasDeColaboracion(List.of(FormaDeColaboracion.DONACION_DINERO))
                .formularioRespondido(formRespondidoHumano2)
                .build();


        PersonaJuridica personaJuridicaPendiente2 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now())
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS, FormaDeColaboracion.DONACION_DINERO))
                .direccion(Direccion.builder().calle("Av. Lopez").altura("2011").localidad(localidadBsAs2).build())
                .razonSocial("Mercado Libre")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.EMPRESA)
                .contactos(List.of(Contacto.builder().contacto("1138085884").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formularioRespondido(formRespondidoPJ1)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Comercio Electrónico").build())
                .build();

        PersonaJuridica personaJuridicaPendiente3 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now().minusDays(1))
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS, FormaDeColaboracion.DONACION_DINERO))
                .direccion(Direccion.builder().calle("Av. Corrientes").altura("456").localidad(localidadCABA1).build())
                .razonSocial("Fundación Ayuda")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.ONG)
                .contactos(List.of(Contacto.builder().contacto("thiago23gonzalez@gmail.com").tipoDeContacto(TipoDeContacto.CORREO).build()))
                .formularioRespondido(formRespondidoPJ3)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Asistencia Social").build())
                .build();

        PersonaJuridica personaJuridicaPendiente4 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now().minusHours(3))
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS, FormaDeColaboracion.DONACION_DINERO))
                .direccion(Direccion.builder().calle("Av. de Mayo").altura("999").localidad(localidadCABA2).build())
                .razonSocial("Comunidad Unida")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.ONG)
                .contactos(List.of(Contacto.builder().contacto("1176543210").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formularioRespondido(formRespondidoPJ4)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Comunidad").build())
                .build();

        PersonaJuridica personaJuridicaPendiente5 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now().minusDays(5))
                .formasDeColaboracion(List.of(FormaDeColaboracion.DONACION_DINERO))
                .direccion(Direccion.builder().calle("Calle San Martín").altura("1200").localidad(localidadBsAs1).build())
                .razonSocial("Alimentos para Todos")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.ONG)
                .contactos(List.of(Contacto.builder().contacto("6190209796").tipoDeContacto(TipoDeContacto.TELEGRAM).build()))
                .formularioRespondido(formRespondidoPJ5)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Alimentación").build())
                .build();

        PersonaJuridica personaJuridicaPendiente6 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now().minusDays(2))
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS, FormaDeColaboracion.DONACION_DINERO))
                .direccion(Direccion.builder().calle("Calle Libertador").altura("320").localidad(localidadCABA3).build())
                .razonSocial("Proyecto Verde")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.ONG)
                .contactos(List.of(Contacto.builder().contacto("1187654321").tipoDeContacto(TipoDeContacto.WHATSAPP).build()))
                .formularioRespondido(formRespondidoPJ6)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Medio Ambiente").build())
                .build();

        PersonaJuridica personaJuridicaPendiente7 = PersonaJuridica.builder()
                .estadoDeSolicitud(EstadoDeSolicitud.PENDIENTE)
                .fechaDeSolicitud(LocalDateTime.now().minusDays(4))
                .formasDeColaboracion(List.of(FormaDeColaboracion.HACERSE_CARGO_DE_HELADERAS))
                .direccion(Direccion.builder().calle("Calle 9 de Julio").altura("800").localidad(localidadBsAs2).build())
                .razonSocial("Educación para Todos")
                .tipoDeOrganizacion(PersonaJuridica.TipoDeOrganizacion.ONG)
                .contactos(List.of(Contacto.builder().contacto("educacionParaTodos@gmail.com").tipoDeContacto(TipoDeContacto.CORREO).build()))
                .formularioRespondido(formRespondidoPJ7)
                .usuario(usuario)
                .rubro(RubroPersonaJuridica.builder().descripcion("Educación").build())
                .build();


        SuscripcionDesperfectos suscripcionDesperfectos = SuscripcionDesperfectos.builder()
                .medioDeAviso(NotificadorDeEmail.builder().build())
                .contacto(contacto2)
                .colaborador(humanoUserHumano)
                .heladera(heladeraHaedo)
                .build();

        heladeraHaedo.agregarSuscripcionDesperfectos(suscripcionDesperfectos);

        TarjetaColaborador tarjetaColaborador = TarjetaColaborador.builder().codigo("00001111000").colaboradorAsociado(humanoUserHumano).build();
        TarjetaColaborador tarjetaColaborador2 = TarjetaColaborador.builder().codigo("00002222000").colaboradorAsociado(humanoUserHumano2).build();

        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .tarjetaColaborador(tarjetaColaborador2)
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now().plusHours(3))
                .tipoDeAccion(TipoDeAccion.APERTURA_PARA_INGRESAR_DONACION)
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .heladerasSolicitadas(List.of(heladeraHaedo))
                .build();


        FallaTecnica falla1 = FallaTecnica.builder()
                .estadoReporte(EstadoReporte.EN_PROCESO)
                .colaborador(humanoUserHumano)
                .foto("uploads/incidentes/heladeraRota1.jpg")
                .build();

        heladeraHaedo.setEstadoHeladera(EstadoHeladera.CON_FALLA_TECNICA);

        falla1.setHeladeraAsociada(heladeraHaedo);
        falla1.setDescripcion("Esta nadando en un rio");
        falla1.setFotoResolucion("uploads/incidentes/resolucion1.jpg");
        falla1.setFechaYHora(LocalDateTime.now());


        HacerseCargoDeHeladera deHeladeraMedrano = HacerseCargoDeHeladera.builder()
                .colaborador(personaJuridicaUserEmpresa)
                .heladera(heladeraMedrano)
                .puntosAcumulados(5d)
                .fecha(LocalDate.now())
                .build();

        HacerseCargoDeHeladera deHeladeraHaedo = HacerseCargoDeHeladera.builder()
                .colaborador(personaJuridicaUserEmpresa)
                .heladera(heladeraHaedo)
                .puntosAcumulados(5d)
                .fecha(LocalDate.now())
                .build();

        HacerseCargoDeHeladera deHeladeraLugano = HacerseCargoDeHeladera.builder()
                .colaborador(personaJuridicaUserEmpresa2)
                .heladera(heladeraLugano)
                .puntosAcumulados(5d)
                .fecha(LocalDate.now())
                .build();

        HacerseCargoDeHeladera deHeladeraCordoba = HacerseCargoDeHeladera.builder()
                .colaborador(personaJuridicaUserEmpresa2)
                .heladera(heladeraUTNCordoba)
                .puntosAcumulados(5d)
                .fecha(LocalDate.now())
                .build();

        Tecnico tecnico1 = Tecnico.builder()
                .cuil("20357894562")
                .nombre("Jose")
                .apellido("Perez")
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDeDocumento("42325532")
                .medioDeAviso(ServiceLocator.getService(NotificadorDeEmail.class))
                .activo(true)
                .areaDeCobertura(AreaDeCobertura.builder()
                        .radioEnKM(3)
                        .puntoGeografico(PuntoGeografico.builder().latitud(-34.6036).longitud(-58.4206).build())
                        .build())
                .build();
        tecnico1.agregarContacto(contactoTecnico);


        /*Tecnico tecnico2 = Tecnico.builder()
                .cuil("27363112419")
                .nombre("Pedro")
                .apellido("Lejos")
                .tipoDeDocumento(TipoDeDocumento.DNI)
                .numeroDeDocumento("4232554")
                .medioDeAviso(ServiceLocator.getService(NotificadorDeTelegram.class))
                .activo(true)
                .areaDeCobertura(AreaDeCobertura.builder()
                        .radioEnKM(2)
                        .puntoGeografico(PuntoGeografico.builder().latitud(-31.442198889695014).longitud(-64.19319436085152).build())
                        .build())
                .build();
        tecnico2.agregarContacto(contactoTelegram2);*/

        DonacionDeDinero donacionDeDinero = DonacionDeDinero.builder()
                .frecuenciaDonacion(FrecuenciaDonacion.UNA_CADA_MES)
                .fecha(LocalDate.now().minusDays(1))
                .monto(30d)
                .humano(humanoUserHumano)
                .build();

        DonacionDeVianda donacionDeVianda = DonacionDeVianda.builder()
                .fecha(LocalDate.now().minusDays(2))
                .viandas(List.of(Vianda.builder().build()))
                .colaborador(humanoUserHumano)
                .build();

        DonacionDeDinero donacionDeDinero2 = DonacionDeDinero.builder()
                .frecuenciaDonacion(FrecuenciaDonacion.UNA_CADA_MES)
                .fecha(LocalDate.now().minusDays(3))
                .monto(20d)
                .humano(humanoUserHumano)
                .build();


        //////////////////////////////PERSISTIR ENTIDADES/////////////////////////////////
        withTransaction(() -> {


            formulariosRepositorio.guardar(formHumano);
            formulariosRepositorio.guardar(formPersoJuridica);

            formulariosRepositorio.guardar(formDonacionDeDinero);
            formulariosRepositorio.guardar(formDistribViandas);
            formulariosRepositorio.guardar(formDonacionViandas);
            formulariosRepositorio.guardar(formPersonaVulnerable);
            formulariosRepositorio.guardar(formMenorVulnerable);

            formulariosRepositorio.guardar(formOfrecerCanje);
            formulariosRepositorio.guardar(formHacerseCargoDeHeladera);

            repoModelos.guardar(modeloHeladeraHoneker);

            repoRubrosPJ.guardar(rubro1);
            repoRubrosPJ.guardar(rubro2);
            repoRubrosPJ.guardar(rubro3);
            repoRubrosPJ.guardar(rubro4);
            repoRubrosPJ.guardar(rubro5);

            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura1);
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura2);
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura3);
            sensoresDeTemperaturaRepositorio.guardar(sensorDeTemperatura4);
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento1);
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento2);
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento3);
            sensoresDeMovimientoRepositorio.guardar(sensorDeMovimiento4);

            // Buenos Aires
            repoLocalidades.guardar(localidadBsAs1);
            repoLocalidades.guardar(localidadBsAs2);
            repoLocalidades.guardar(localidadBsAs3);

// CABA
            repoLocalidades.guardar(localidadCABA1);
            repoLocalidades.guardar(localidadCABA2);
            repoLocalidades.guardar(localidadCABA3);

// Catamarca
            repoLocalidades.guardar(localidadCatamarca1);
            repoLocalidades.guardar(localidadCatamarca2);
            repoLocalidades.guardar(localidadCatamarca3);

// Chaco
            repoLocalidades.guardar(localidadChaco1);
            repoLocalidades.guardar(localidadChaco2);
            repoLocalidades.guardar(localidadChaco3);

// Chubut
            repoLocalidades.guardar(localidadChubut1);
            repoLocalidades.guardar(localidadChubut2);
            repoLocalidades.guardar(localidadChubut3);

// Córdoba
            repoLocalidades.guardar(localidadCordoba1);
            repoLocalidades.guardar(localidadCordoba2);
            repoLocalidades.guardar(localidadCordoba3);

// Corrientes
            repoLocalidades.guardar(localidadCorrientes1);
            repoLocalidades.guardar(localidadCorrientes2);
            repoLocalidades.guardar(localidadCorrientes3);

// Entre Ríos
            repoLocalidades.guardar(localidadEntreRios1);
            repoLocalidades.guardar(localidadEntreRios2);
            repoLocalidades.guardar(localidadEntreRios3);

// Formosa
            repoLocalidades.guardar(localidadFormosa1);
            repoLocalidades.guardar(localidadFormosa2);
            repoLocalidades.guardar(localidadFormosa3);

// Jujuy
            repoLocalidades.guardar(localidadJujuy1);
            repoLocalidades.guardar(localidadJujuy2);
            repoLocalidades.guardar(localidadJujuy3);

// La Pampa
            repoLocalidades.guardar(localidadLaPampa1);
            repoLocalidades.guardar(localidadLaPampa2);
            repoLocalidades.guardar(localidadLaPampa3);

// La Rioja
            repoLocalidades.guardar(localidadLaRioja1);
            repoLocalidades.guardar(localidadLaRioja2);
            repoLocalidades.guardar(localidadLaRioja3);

// Mendoza
            repoLocalidades.guardar(localidadMendoza1);
            repoLocalidades.guardar(localidadMendoza2);
            repoLocalidades.guardar(localidadMendoza3);

// Misiones
            repoLocalidades.guardar(localidadMisiones1);
            repoLocalidades.guardar(localidadMisiones2);
            repoLocalidades.guardar(localidadMisiones3);

// Neuquén
            repoLocalidades.guardar(localidadNeuquen1);
            repoLocalidades.guardar(localidadNeuquen2);
            repoLocalidades.guardar(localidadNeuquen3);

// Río Negro
            repoLocalidades.guardar(localidadRioNegro1);
            repoLocalidades.guardar(localidadRioNegro2);
            repoLocalidades.guardar(localidadRioNegro3);

// Salta
            repoLocalidades.guardar(localidadSalta1);
            repoLocalidades.guardar(localidadSalta2);
            repoLocalidades.guardar(localidadSalta3);

// San Juan
            repoLocalidades.guardar(localidadSanJuan1);
            repoLocalidades.guardar(localidadSanJuan2);
            repoLocalidades.guardar(localidadSanJuan3);

// San Luis
            repoLocalidades.guardar(localidadSanLuis1);
            repoLocalidades.guardar(localidadSanLuis2);
            repoLocalidades.guardar(localidadSanLuis3);

// Santa Cruz
            repoLocalidades.guardar(localidadSantaCruz1);
            repoLocalidades.guardar(localidadSantaCruz2);
            repoLocalidades.guardar(localidadSantaCruz3);

// Santa Fe
            repoLocalidades.guardar(localidadSantaFe1);
            repoLocalidades.guardar(localidadSantaFe2);
            repoLocalidades.guardar(localidadSantaFe3);

// Santiago del Estero
            repoLocalidades.guardar(localidadSantiago1);
            repoLocalidades.guardar(localidadSantiago2);
            repoLocalidades.guardar(localidadSantiago3);

// Tierra del Fuego
            repoLocalidades.guardar(localidadTierraFuego1);
            repoLocalidades.guardar(localidadTierraFuego2);
            repoLocalidades.guardar(localidadTierraFuego3);

// Tucumán
            repoLocalidades.guardar(localidadTucuman1);
            repoLocalidades.guardar(localidadTucuman2);
            repoLocalidades.guardar(localidadTucuman3);


            personasJuridicasRepositorio.guardar(personaJuridicaUserEmpresa);
            personasJuridicasRepositorio.guardar(personaJuridicaUserEmpresa2);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente2);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente3);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente4);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente5);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente6);
            personasJuridicasRepositorio.guardar(personaJuridicaPendiente7);


            repoLocalidades.guardar(localidad1);

            repoHumanos.guardar(humanoUserHumano);
            repoHumanos.guardar(humanoUserHumano2);
            repoHumanos.guardar(humanoSolicitud1);
            repoHumanos.guardar(humanoSolicitud2);
            repoHumanos.guardar(humanoSolicitud0);
            repoHumanos.guardar(humanoSolicitud3);
            repoHumanos.guardar(humanoSolicitud4);
            repoHumanos.guardar(humanoSolicitud6);

            repoHeladeras.guardar(heladeraMedrano);
            repoHeladeras.guardar(heladeraHaedo);
            repoHeladeras.guardar(heladeraLugano);
            repoHeladeras.guardar(heladeraUTNCordoba);

            hacerseCargoDeHeladerasRepositorio.guardar(deHeladeraMedrano);
            hacerseCargoDeHeladerasRepositorio.guardar(deHeladeraHaedo);
            hacerseCargoDeHeladerasRepositorio.guardar(deHeladeraLugano);
            hacerseCargoDeHeladerasRepositorio.guardar(deHeladeraCordoba);


            repoUsuarios.guardar(usuarioAdmin);
            repoUsuarios.guardar(usuarioEmpresa);
            repoUsuarios.guardar(usuarioEmpresa2);
            repoUsuarios.guardar(usuarioHumano);
            repoUsuarios.guardar(usuarioHumano2);

            repoOfertas.guardar(ofertaAPersistir1);
            repoOfertas.guardar(ofertaAPersistir2);
            repoOfertas.guardar(ofertaAPersistir3);
            repoOfertas.guardar(ofertaAPersistir4);
            repoOfertas.guardar(ofertaAPersistir5);
            repoOfertas.guardar(ofertaAPersistir6);
            repoOfertas.guardar(ofertaAPersistir7);

            repoRubroOfertas.guardar(rubroOferta1);
            repoRubroOfertas.guardar(rubroOferta2);


            repoTarjetasPersonasVulnerables.guardar(tarjetaDePersonaVulnerable);
            repoTarjetasPersonasVulnerables.guardar(tarjetaDePersonaVulnerable2);
            repoTarjetasPersonasVulnerables.guardar(tarjetaDePersonaVulnerable3);


            tarjetasColaboradorRepositorio.guardar(tarjetaColaborador);
            tarjetasColaboradorRepositorio.guardar(tarjetaColaborador2);

            accionesSolicitadasRepositorio.guardar(accionSolicitada);

            fallasTecnicasRepositorio.guardar(falla1);

            tecnicosRepositorio.guardar(tecnico1);

            donacionesDeDineroRepositorio.guardar(donacionDeDinero);
            donacionesDeDineroRepositorio.guardar(donacionDeDinero2);
            donacionesDeViandasRepositorio.guardar(donacionDeVianda);


        });

    }
}
