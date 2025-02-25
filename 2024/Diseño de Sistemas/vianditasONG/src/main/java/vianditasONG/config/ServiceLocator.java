package vianditasONG.config;

import com.google.gson.Gson;
import vianditasONG.controllers.*;
import vianditasONG.controllers.canjes.CanjesController;
import vianditasONG.controllers.colaboraciones.*;
import vianditasONG.controllers.colaboradores.RecomendadorColaboradoresController;
import vianditasONG.controllers.colaboradores.SolicitudesColaboradoresController;
import vianditasONG.controllers.colaboradores.humanos.HumanosController;
import vianditasONG.controllers.colaboradores.personasJuridicas.PersonasJuridicasController;
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
import vianditasONG.controllers.modelos.ModelosController;
import vianditasONG.controllers.receptores.ReceptorDeMovimientos;
import vianditasONG.controllers.receptores.ReceptorDeTemperaturas;
import vianditasONG.controllers.reporteDeFallas.HistorialFallasController;
import vianditasONG.controllers.reporteDeFallas.ReporteFallaTecnicaController;
import vianditasONG.controllers.tecnicos.GestionDeTecnicosController;
import vianditasONG.controllers.tecnicos.MostrarTecnicosController;
import vianditasONG.converters.dtoconverters.*;
import vianditasONG.modelos.entities.heladeras.CalculadorDeHeladerasCercanas;
import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.modelos.repositorios.alertas.imp.AlertasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFallidas.imp.AperturasFallidasRepositorio;
import vianditasONG.modelos.repositorios.aperturas.aperturasFehacientes.imp.AperturasFehacientesRepositorio;
import vianditasONG.modelos.repositorios.canjes.imp.CanjesRepositorio;
import vianditasONG.modelos.repositorios.colaboracion.imp.*;
import vianditasONG.modelos.repositorios.contactos.imp.ContactosRepositorio;
import vianditasONG.modelos.repositorios.credenciales.imp.CredencialesRepositorio;
import vianditasONG.modelos.repositorios.falla_tecnica.imp.FallasTecnicasRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.FormulariosRespondidosRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.OpcionesRepositorio;
import vianditasONG.modelos.repositorios.formularios.imp.PreguntasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IncidentesRepositorio;
import vianditasONG.modelos.repositorios.incidentes.VisitaHeladeraRepositorio;
import vianditasONG.modelos.repositorios.localidades.imp.LocalidadesRepositorio;
import vianditasONG.modelos.repositorios.modelos.imp.ModelosRepositorio;
import vianditasONG.modelos.repositorios.ofertas.imp.OfertasRepositorio;
import vianditasONG.modelos.repositorios.personas_en_situacion_vulnerable.imp.PersonaEnSituacionVulnerableRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.reportes.imp.ReportesRepositorio;
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
import vianditasONG.modelos.repositorios.usuarios.imp.UsuariosRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPunto;
import vianditasONG.modelos.servicios.conversorDirecAPunto.OpenCageAdapterAPI;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.SendGridAdapterAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotInitializer;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotTelegramAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.TecnicoBot;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWPP;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWppAdapter;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.TwilioAdapterAPI;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeWhatsapp;
import vianditasONG.modelos.servicios.migracionDeColaboraciones.ImportadorDeColaboraciones;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradores;
import vianditasONG.modelos.servicios.recomendadorDeColaboradores.RecomendadorDeColaboradoresAdapterAPI;
import vianditasONG.modelos.servicios.recomendadorDePuntos.PuntosRecomendadosAdapterAPI;
import vianditasONG.modelos.servicios.seguridad.ValidadorCreacionPassword;
import vianditasONG.modelos.servicios.seguridad.ValidadorPasswordAutorizada;
import vianditasONG.modelos.servicios.seguridad.distribuidorDeTarjetas.DistribudorDeTarjetas;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorMD5;
import vianditasONG.modelos.servicios.seguridad.encriptadores.EncriptadorSHA1;
import vianditasONG.modelos.servicios.seguridad.estandares.*;
import vianditasONG.modelos.servicios.seguridad.validaciones.CumpleEstandaresNIST;
import vianditasONG.modelos.servicios.seguridad.validaciones.NoEsComun;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.recomendadorDePuntos.ServicioRecomendadorDePuntos;
import vianditasONG.utils.creadorDeCredenciales.CreadorDeCredenciales;
import vianditasONG.utils.mqtt.MQTTHandler;
import vianditasONG.utils.verificadorDeCercania.CalculadorDeDistancias;
import vianditasONG.utils.verificadorDeCercania.VerificadorDeCercania;

import java.util.HashMap;
import java.util.Map;
public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();

    public static void registerService(String nombreClase, Object objeto){
        if(Object.class.getName() == nombreClase){
            instances.put(nombreClase, objeto);
        }else{
            throw new RuntimeException("El servicio a registrar no coincide con la clase esperada");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> serviceClass) {

        String componentName = serviceClass.getName();

        if (!instances.containsKey(componentName)) {
            if (componentName.equals(DonacionesDeDineroRepositorio.class.getName())) {
                DonacionesDeDineroRepositorio instance = DonacionesDeDineroRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(SolicitudesColaboradoresController.class.getName())) {
                SolicitudesColaboradoresController instance = SolicitudesColaboradoresController.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(EncriptadorMD5.class.getName())) {
                EncriptadorMD5 instance = new EncriptadorMD5();
                instances.put(componentName, instance);
            }else if (componentName.equals(FormularioRespondidoHumanoController.class.getName())) {
                FormularioRespondidoHumanoController instance = FormularioRespondidoHumanoController.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(DistribudorDeTarjetas.class.getName())) {
                DistribudorDeTarjetas instance = DistribudorDeTarjetas.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterPregEditDTO.class.getName())) {
                ConverterPregEditDTO instance = ConverterPregEditDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(FormulariosRespondidosRepositorio.class.getName())) {
                FormulariosRespondidosRepositorio instance = FormulariosRespondidosRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterFormularioRespondidoDTO.class.getName())) {
                ConverterFormularioRespondidoDTO instance = ConverterFormularioRespondidoDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(FormulariosController.class.getName())) {
                FormulariosController instance = new FormulariosController();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterFormEstadisticaDTO.class.getName())) {
                ConverterFormEstadisticaDTO instance = ConverterFormEstadisticaDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(FormularioRespondidoPJController.class.getName())) {
                FormularioRespondidoPJController instance = FormularioRespondidoPJController.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(OpcionesRepositorio.class.getName())) {
                OpcionesRepositorio instance = OpcionesRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(MQTTHandler.class.getName())) {
                MQTTHandler instance = MQTTHandler.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(RegistroUsoTarjetaRepositorio.class.getName())) {
                RegistroUsoTarjetaRepositorio instance = RegistroUsoTarjetaRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ReceptorDeMovimientos.class.getName())) {
                ReceptorDeMovimientos instance = ReceptorDeMovimientos.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ReceptorDeTemperaturas.class.getName())) {
                ReceptorDeTemperaturas instance = ReceptorDeTemperaturas.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterSolicitudPersonaJuridicaDTO.class.getName())) {
                ConverterSolicitudPersonaJuridicaDTO instance = ConverterSolicitudPersonaJuridicaDTO.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ConverterSolicitudHumanoDTO.class.getName())) {
                ConverterSolicitudHumanoDTO instance = ConverterSolicitudHumanoDTO.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HistorialDePuntosController.class.getName())) {
                HistorialDePuntosController instance = HistorialDePuntosController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(SensoresDeMovimientoRepositorio.class.getName())) {
                SensoresDeMovimientoRepositorio instance = SensoresDeMovimientoRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(SensoresDeTemperaturaRepositorio.class.getName())) {
                SensoresDeTemperaturaRepositorio instance = SensoresDeTemperaturaRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(PreguntasRepositorio.class.getName())) {
                PreguntasRepositorio instance = new PreguntasRepositorio();
                instances.put(componentName, instance);
            }else if (componentName.equals(TarjetasColaboradorRepositorio.class.getName())) {
                TarjetasColaboradorRepositorio instance = TarjetasColaboradorRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(AccionesSolicitadasRepositorio.class.getName())) {
                AccionesSolicitadasRepositorio instance = AccionesSolicitadasRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(AperturasFehacientesRepositorio.class.getName())) {
                AperturasFehacientesRepositorio instance = AperturasFehacientesRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(AlertasRepositorio.class.getName())) {
                AlertasRepositorio instance = new AlertasRepositorio();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(AperturasFallidasRepositorio.class.getName())) {
                AperturasFallidasRepositorio instance = AperturasFallidasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ConverterRubrosPersonaJuridicaDTO.class.getName())) {
                ConverterRubrosPersonaJuridicaDTO instance = ConverterRubrosPersonaJuridicaDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterItemHistorialDTO.class.getName())) {
                ConverterItemHistorialDTO instance = ConverterItemHistorialDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterLocalidadesDTO.class.getName())) {
                ConverterLocalidadesDTO instance = ConverterLocalidadesDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(LocalidadesController.class.getName())) {
                LocalidadesController instance = LocalidadesController.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(RubrosPersonasJuridicasController.class.getName())) {
                RubrosPersonasJuridicasController instance = RubrosPersonasJuridicasController.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(RubrosPersonaJuridicaRepositorio.class.getName())) {
                RubrosPersonaJuridicaRepositorio instance = RubrosPersonaJuridicaRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(OpenCageAdapterAPI.class.getName())) {
                OpenCageAdapterAPI instance = OpenCageAdapterAPI.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(LocalidadesRepositorio.class.getName())) {
                LocalidadesRepositorio instance = LocalidadesRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterPersonaJuridicaACrearDTO.class.getName())) {
                ConverterPersonaJuridicaACrearDTO instance = ConverterPersonaJuridicaACrearDTO.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConverterHumanoACrearDTO.class.getName())) {
                ConverterHumanoACrearDTO instance = ConverterHumanoACrearDTO.builder().build();
                    instances.put(componentName, instance);
            }else if (componentName.equals(ValidadorPasswordAutorizada.class.getName())) {
                ValidadorPasswordAutorizada instance = ValidadorPasswordAutorizada.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(ConversorDirecAPunto.class.getName())) {
                ConversorDirecAPunto instance = ConversorDirecAPunto.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(DistribucionesDeViandasRepositorio.class.getName())) {
                DistribucionesDeViandasRepositorio instance = DistribucionesDeViandasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(DonacionesDeViandasRepositorio.class.getName())) {
                DonacionesDeViandasRepositorio instance = DonacionesDeViandasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HacerseCargoDeHeladerasRepositorio.class.getName())) {
                HacerseCargoDeHeladerasRepositorio instance = HacerseCargoDeHeladerasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(RegistrosPersonasVulnerablesRepositorio.class.getName())) {
                RegistrosPersonasVulnerablesRepositorio instance = RegistrosPersonasVulnerablesRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladerasRepositorio.class.getName())) {
                HeladerasRepositorio instance = HeladerasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HumanosRepositorio.class.getName())) {
                HumanosRepositorio instance = HumanosRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonasJuridicasRepositorio.class.getName())) {
                PersonasJuridicasRepositorio instance = PersonasJuridicasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionCantViandasMaxRepositorio.class.getName())) {
                SuscripcionCantViandasMaxRepositorio instance = SuscripcionCantViandasMaxRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionCantViandasMinRepositorio.class.getName())) {
                SuscripcionCantViandasMinRepositorio instance = SuscripcionCantViandasMinRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionDesperfectosRepositorio.class.getName())) {
                SuscripcionDesperfectosRepositorio instance = SuscripcionDesperfectosRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(OfertasRepositorio.class.getName())) {
                OfertasRepositorio instance = OfertasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(UsuariosRepositorio.class.getName())) {
                UsuariosRepositorio instance = UsuariosRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CanjesRepositorio.class.getName())) {
                CanjesRepositorio instance = CanjesRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ModelosRepositorio.class.getName())) {
                ModelosRepositorio instance = ModelosRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(LogInController.class.getName())) {
                LogInController instance = LogInController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HumanosController.class.getName())) {
                HumanosController instance = HumanosController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonasJuridicasController.class.getName())) {
                PersonasJuridicasController instance = PersonasJuridicasController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(Gson.class.getName())) {
                Gson instance = new Gson();
                instances.put(componentName, instance);
            } else if (componentName.equals(ImportadorColaboracionesController.class.getName())) {
                ImportadorColaboracionesController instance = ImportadorColaboracionesController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(TarjetasDePersonasVulnerablesRepositorio.class.getName())) {
                TarjetasDePersonasVulnerablesRepositorio instance = TarjetasDePersonasVulnerablesRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(PersonaEnSituacionVulnerableRepositorio.class.getName())) {
                PersonaEnSituacionVulnerableRepositorio instance = PersonaEnSituacionVulnerableRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if(componentName.equals(ConverterPersonasVulnerablesDto.class.getName())){
                ConverterPersonasVulnerablesDto instance = ConverterPersonasVulnerablesDto.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonasVulnerablesController.class.getName())) {
                PersonasVulnerablesController instance = PersonasVulnerablesController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ImportadorDeColaboraciones.class.getName())) {
                ImportadorDeColaboraciones instance = ImportadorDeColaboraciones.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CreadorDeCredenciales.class.getName())) {
                CreadorDeCredenciales instance = CreadorDeCredenciales.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(EncriptadorSHA1.class.getName())) {
                EncriptadorSHA1 instance = EncriptadorSHA1.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CredencialesRepositorio.class.getName())) {
                CredencialesRepositorio instance = CredencialesRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(CalculadorDeTecnicoCercano.class.getName())) {
                CalculadorDeTecnicoCercano instance = CalculadorDeTecnicoCercano.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(VerificadorDeCercania.class.getName())) {
                VerificadorDeCercania instance = VerificadorDeCercania.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(TecnicosRepositorio.class.getName())) {
                TecnicosRepositorio instance = TecnicosRepositorio.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(CalculadorDeDistancias.class.getName())) {
                CalculadorDeDistancias instance = CalculadorDeDistancias.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(CalculadorDeHeladerasCercanas.class.getName())) {
                CalculadorDeHeladerasCercanas instance = CalculadorDeHeladerasCercanas.builder().build();
                instances.put(componentName, instance);
            }else if (componentName.equals(EnviadorDeTelegrams.class.getName())) {
                EnviadorDeTelegrams instance = EnviadorDeTelegrams.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(BotTelegramAPI.class.getName())) {
                BotTelegramAPI instance = new BotTelegramAPI(Config.getInstancia().obtenerDelConfig("tokenBot"));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(CalculadorPesosDonados.class.getName())) {
                CalculadorPesosDonados instance = CalculadorPesosDonados.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CalculadorViandasDistribuidas.class.getName())) {
                CalculadorViandasDistribuidas instance = CalculadorViandasDistribuidas.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CalculadorViandasDonadas.class.getName())) {
                CalculadorViandasDonadas instance = CalculadorViandasDonadas.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(CalculadorTarjetasDistribuidas.class.getName())) {
                CalculadorTarjetasDistribuidas instance = CalculadorTarjetasDistribuidas.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(EnviadorDeMails.class.getName())) {
                EnviadorDeMails instance = EnviadorDeMails.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(SendGridAdapterAPI.class.getName())) {
                SendGridAdapterAPI instance = SendGridAdapterAPI.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(CanjesController.class.getName())) {
                CanjesController instance = CanjesController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RubrosOfertasRepositorio.class.getName())) {
                RubrosOfertasRepositorio instance = RubrosOfertasRepositorio.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ConverterRubrosOfertasDto.class.getName())) {
                ConverterRubrosOfertasDto instance = ConverterRubrosOfertasDto.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterOfertasDto.class.getName())) {
                ConverterOfertasDto instance = ConverterOfertasDto.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(OfertasProdYServController.class.getName())) {
                OfertasProdYServController instance = OfertasProdYServController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(MenuHumanoController.class.getName())) {
                MenuHumanoController instance = MenuHumanoController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ConverterHeladerasLoginDto.class.getName())) {
                ConverterHeladerasLoginDto instance = ConverterHeladerasLoginDto.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladerasEnMapaController.class.getName())) {
                HeladerasEnMapaController instance = HeladerasEnMapaController.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(ValidadorCreacionPassword.class.getName())) {
                ValidadorCreacionPassword instance = ValidadorCreacionPassword.builder().build();
                instance.agregarValidaciones(ServiceLocator.getService(NoEsComun.class),
                        ServiceLocator.getService(CumpleEstandaresNIST.class));
            instances.put(componentName, instance);
            } else if (componentName.equals(CumpleEstandaresNIST.class.getName())) {
                CumpleEstandaresNIST instance = CumpleEstandaresNIST.builder().build();
                instance.agregarEstandaresNist(
                        ServiceLocator.getService(EstandarDeLongitud.class),
                        ServiceLocator.getService(EstandarDeRotacion.class),
                        ServiceLocator.getService(EstandarDeComplejidad.class));

                instances.put(componentName, instance);
            }
            else if (componentName.equals(NoEsComun.class.getName())) {
                NoEsComun instance = NoEsComun.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(SignUpController.class.getName())) {
                SignUpController instance = SignUpController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarDeComplejidad.class.getName())) {
                EstandarDeComplejidad instance = EstandarDeComplejidad.builder().build();
                instance.agregarEstandares(
                        ServiceLocator.getService(EstandarMinusculas.class),
                        ServiceLocator.getService(EstandarMayusculas.class),
                        ServiceLocator.getService(EstandarDigitos.class),
                        ServiceLocator.getService(EstandarCaracteresEspeciales.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarDeLongitud.class.getName())) {
                EstandarDeLongitud instance = EstandarDeLongitud.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarMinusculas.class.getName())) {
                EstandarMinusculas instance = EstandarMinusculas.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarMayusculas.class.getName())) {
                EstandarMayusculas instance = EstandarMayusculas.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarDigitos.class.getName())) {
                EstandarDigitos instance = EstandarDigitos.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarCaracteresEspeciales.class.getName())) {
                EstandarCaracteresEspeciales instance = EstandarCaracteresEspeciales.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(EstandarDeRotacion.class.getName())) {
                EstandarDeRotacion instance = EstandarDeRotacion.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(DonacionesDeViandasController.class.getName())) {
                DonacionesDeViandasController instance = DonacionesDeViandasController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ReportesController.class.getName())) {
                ReportesController instance = ReportesController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(MenuAdminController.class.getName())) {
                MenuAdminController instance = MenuAdminController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(MenuPersonaJuridicaController.class.getName())) {
                MenuPersonaJuridicaController instance = MenuPersonaJuridicaController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(GestionDeHeladerasController.class.getName())) {
                GestionDeHeladerasController instance = GestionDeHeladerasController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ModelosController.class.getName())) {
                ModelosController instance = ModelosController.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(DistribucionesDeViandasController.class.getName())) {
                DistribucionesDeViandasController instance = new DistribucionesDeViandasController();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterDonacionViandaDTO.class.getName())) {
                ConverterDonacionViandaDTO instance = ConverterDonacionViandaDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterDistribucionViandaDTO.class.getName())) {
                ConverterDistribucionViandaDTO instance = ConverterDistribucionViandaDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ReportesRepositorio.class.getName())) {
                ReportesRepositorio instance = ReportesRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterReportesDTO.class.getName())) {
                ConverterReportesDTO instance = ConverterReportesDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(FormasDeColaboracionRepositorio.class.getName())) {
                FormasDeColaboracionRepositorio instance = FormasDeColaboracionRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ConverterFormaDeColaboracionDTO.class.getName())) {
                ConverterFormaDeColaboracionDTO instance = ConverterFormaDeColaboracionDTO.builder().build();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(ConverterHeladeraDTO.class.getName())) {
                ConverterHeladeraDTO instance = ConverterHeladeraDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ServicioOpenCage.class.getName())) {
                ServicioOpenCage instanec = ServicioOpenCage.getInstancia();
                instances.put(componentName, instanec);
            }
            else if(componentName.equals(HeladerasAMostrarController.class.getName())) {
                HeladerasAMostrarController instance = HeladerasAMostrarController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(DonacionesDeDineroController.class.getName())) {
                DonacionesDeDineroController instance = DonacionesDeDineroController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterDonacionDineroDTO.class.getName())) {
                ConverterDonacionDineroDTO instance = ConverterDonacionDineroDTO.builder().build();
                instances.put(componentName, instance);
            } else if (componentName.equals(FallasTecnicasRepositorio.class.getName())) {
                FallasTecnicasRepositorio instance = FallasTecnicasRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterFallaDTO.class.getName())){
                ConverterFallaDTO instance = ConverterFallaDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ReporteFallaTecnicaController.class.getName())) {
                ReporteFallaTecnicaController instance = ReporteFallaTecnicaController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ColocarHeladeraController.class.getName())) {
                ColocarHeladeraController instance = ColocarHeladeraController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(FormulariosRepositorio.class.getName())) {
                FormulariosRepositorio instance = new FormulariosRepositorio();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterPreguntasDTO.class.getName())) {
                ConverterPreguntasDTO instance = ConverterPreguntasDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(SuscripcionesController.class.getName())) {
                SuscripcionesController instance = SuscripcionesController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterSuscripcionDTO.class.getName())) {
                ConverterSuscripcionDTO instance = ConverterSuscripcionDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(NotificadorDeWhatsapp.class.getName())) {
                NotificadorDeWhatsapp instance = NotificadorDeWhatsapp.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(NotificadorDeEmail.class.getName())) {
                NotificadorDeEmail instance = NotificadorDeEmail.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(NotificadorDeTelegram.class.getName())) {
                NotificadorDeTelegram instance = NotificadorDeTelegram.builder().build();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(HistorialFallasController.class.getName())) {
                HistorialFallasController instance = HistorialFallasController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(EnviadorDeWPP.class.getName())) {
                EnviadorDeWPP instance = EnviadorDeWPP.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(TwilioAdapterAPI.class.getName())) {
                TwilioAdapterAPI instance = TwilioAdapterAPI.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ContactosRepositorio.class.getName())) {
                ContactosRepositorio instance = ContactosRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterContactosDTO.class.getName())) {
                ConverterContactosDTO instance = ConverterContactosDTO.builder().build();

                instances.put(componentName, instance);
            }
            else if(componentName.equals(RecomendadorColaboradoresController.class.getName())) {
                RecomendadorColaboradoresController instance = RecomendadorColaboradoresController.builder().build();

                instances.put(componentName, instance);
            }
            else if(componentName.equals(ConverterColaboradorDTO.class.getName())) {
                ConverterColaboradorDTO instance = ConverterColaboradorDTO.builder().build();

                instances.put(componentName, instance);
            }
            else if(componentName.equals(RecomendadorDeColaboradoresAdapterAPI.class.getName())) {
                RecomendadorDeColaboradoresAdapterAPI instance = RecomendadorDeColaboradoresAdapterAPI.builder().build();

                instances.put(componentName, instance);
            }

            else if(componentName.equals(RecomendadorDeColaboradores.class.getName())) {
                RecomendadorDeColaboradores instance = RecomendadorDeColaboradores.builder().build();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(ConverterTecnicoDTO.class.getName())) {
                ConverterTecnicoDTO instance = ConverterTecnicoDTO.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(MostrarTecnicosController.class.getName())) {
                MostrarTecnicosController instance = MostrarTecnicosController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(GestionDeTecnicosController.class.getName())) {
                GestionDeTecnicosController instance = GestionDeTecnicosController.builder().build();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(RecomendadorPuntosController.class.getName())) {
                RecomendadorPuntosController instance = RecomendadorPuntosController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(PuntosRecomendadosAdapterAPI.class.getName())) {
                PuntosRecomendadosAdapterAPI instance = PuntosRecomendadosAdapterAPI.builder().build();
                instances.put(componentName, instance);
            }

            else if(componentName.equals(HeladerasACargoController.class.getName())) {
                HeladerasACargoController instance = HeladerasACargoController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(IncidentesRepositorio.class.getName())) {
                IncidentesRepositorio instance = IncidentesRepositorio.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(LogOutController.class.getName())) {
                LogOutController instance = LogOutController.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(TecnicoBot.class.getName())) {
                TecnicoBot instance = TecnicoBot.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(BotInitializer.class.getName())) {
                BotInitializer instance = BotInitializer.builder().build();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(VisitaHeladeraRepositorio.class.getName())) {
                VisitaHeladeraRepositorio instance = VisitaHeladeraRepositorio.builder().build();
                instances.put(componentName, instance);
            }

        }
        return (T) instances.get(componentName);

    }
}