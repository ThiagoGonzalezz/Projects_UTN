package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.UploadedFile;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.modelos.entities.incidentes.EstadoReporte;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.contactos.IContactosRepositorio;
import vianditasONG.modelos.repositorios.contactos.imp.ContactosRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IIncidentesRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IVisitaHeladeraRepositorio;
import vianditasONG.modelos.repositorios.incidentes.IncidentesRepositorio;
import vianditasONG.modelos.repositorios.incidentes.VisitaHeladeraRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.ITecnicosRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Builder
public class TecnicoBot extends TelegramLongPollingBot implements WithSimplePersistenceUnit {

    @Builder.Default
    private Map<Long, ProcesoRegistro> tecnicoEnProceso = new HashMap<>();
    @Builder.Default
    private NotificadorDeTelegram notificadorDeTelegram = ServiceLocator.getService(NotificadorDeTelegram.class);
    @Builder.Default
    private IIncidentesRepositorio incidentesRepositorio = ServiceLocator.getService(IncidentesRepositorio.class);
    @Builder.Default
    private ITecnicosRepositorio tecnicosRepositorio = ServiceLocator.getService(TecnicosRepositorio.class);
    @Builder.Default
    private IContactosRepositorio contactosRepositorio = ServiceLocator.getService(ContactosRepositorio.class);
    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private IVisitaHeladeraRepositorio visitaHeladeraRepositorio = ServiceLocator.getService(VisitaHeladeraRepositorio.class);

    @Override
    public String getBotToken() {
        return Config.getInstancia().obtenerDelConfig("tokenBot");
    }

    @Override
    public String getBotUsername() {
        return Config.getInstancia().obtenerDelConfig("usernameBot");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String mensaje = update.getMessage().hasText() ? update.getMessage().getText() : "";
        String foto = update.getMessage().hasPhoto() ? "Foto recibida" : "";

        if (!mensaje.isEmpty()) {
            manejarMensaje(mensaje, chatId);
        } else if (!foto.isEmpty()) {
            manejarFoto(update,chatId);
        }
    }

    private void manejarMensaje(String mensaje, Long chatId) {
        ProcesoRegistro registro = tecnicoEnProceso.get(chatId);

        if (registro != null) {
            switch (registro.getEstado()) {
                case "esperando_descripcion":
                    procesarDescripcion(mensaje, chatId, registro);
                    break;
                case "esperando_foto":
                    procesarFoto(mensaje, chatId, registro);
                    break;
                case "esperando_solucion":
                    procesarSolucion(mensaje, chatId, registro);
                    break;
            }
        } else {
            iniciarRegistro(mensaje, chatId);
        }
    }

    private void procesarDescripcion(String mensaje, Long chatId, ProcesoRegistro registro) {
        registro.getVisita().setDescripcion(mensaje);
        registro.setEstado("esperando_foto");
        enviarMensaje(chatId, "Descripción recibida. A continuación adjuntá una imagen si lo considerás necesario. Si no, podés responder con 'NO'.");
    }

    private void procesarFoto(String mensaje, Long chatId, ProcesoRegistro registro) {
        if (mensaje.equalsIgnoreCase("no")) {
            registro.setEstado("esperando_solucion");
            enviarMensaje(chatId, "¿Solucionaste el incidente? Responde 'SI' o 'NO'.");
        }
    }

    private void procesarSolucion(String mensaje, Long chatId, ProcesoRegistro registro) {
        Incidente incidente = registro.getIncidente();
        VisitaHeladera visitaHeladera = registro.getVisita();
        Tecnico tecnico = registro.getTecnico();
        Heladera heladera = incidente.getHeladeraAsociada();

        heladera.registrarVisita(visitaHeladera);

        if (mensaje.equalsIgnoreCase("SI")) {
            incidente.setFechaResolucion(LocalDateTime.now());
            incidente.setEstadoReporte(EstadoReporte.RESUELTO);
            incidente.setTecnicoResolutor(registro.getTecnico());
            heladera.activarHeladera();
            visitaHeladera.setSolucionoElIncidente(true);
            tecnico.setDisponible(true);

        } else if (mensaje.equalsIgnoreCase("NO")) {
            incidente.setEstadoReporte(EstadoReporte.EN_PROCESO);
            visitaHeladera.setSolucionoElIncidente(false);
            withTransaction(() -> {
                visitaHeladeraRepositorio.guardar(visitaHeladera);
            });
        } else {
            enviarMensaje(chatId, "Por favor, responde 'SI' o 'NO' para indicar si solucionaste el incidente.");
            return;
        }


        tecnicoEnProceso.remove(chatId);
        enviarMensaje(chatId, "Visita registrada exitosamente. Gracias por tu colaboración. No olvides que podés registrar otra visita si es necesario enviando REGISTRAR VISITA");
        withTransaction(() -> {
            incidentesRepositorio.actualizar(incidente);
            tecnicosRepositorio.actualizar(tecnico);
            heladerasRepositorio.actualizar(incidente.getHeladeraAsociada());
        });
    }

    private void iniciarRegistro(String mensaje, Long chatId) {
        if (mensaje.equalsIgnoreCase("REGISTRAR VISITA")) {
            Tecnico tecnico = obtenerTecnicoPorChatId(chatId);
            Incidente incidente = obtenerIncidente(tecnico);
            VisitaHeladera visitaHeladera= VisitaHeladera.builder()
                    .tecnico(tecnico)
                    .fecha(LocalDateTime.now())
                    .build();

            ProcesoRegistro registro = ProcesoRegistro.builder().
                    estado("esperando_descripcion").
                    visita(visitaHeladera).
                    incidente(incidente).
                    tecnico(tecnico).build();

            tecnicoEnProceso.put(chatId, registro);
            enviarMensaje(chatId, "Por favor, proporciona una descripción de la visita.");
        } else {
            enviarMensaje(chatId, "Comando no reconocido. Responde 'REGISTRAR VISITA' para registrar tu visita.");
        }
    }


    private void enviarMensaje(Long chatId, String texto) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(texto);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Tecnico obtenerTecnicoPorChatId(Long chatId) {
        Long tecnico_id = this.contactosRepositorio.buscarTecnicoId(chatId.toString()).orElseThrow();
        return this.tecnicosRepositorio.buscarPorId(tecnico_id).orElseThrow();
    }

    private Incidente obtenerIncidente(Tecnico tecnico) {
        int aux = tecnico.getIncidentes().size();
        return tecnico.getIncidentes().get(aux - 1);
    }

    private void manejarFoto(Update update, Long chatId) {
        ProcesoRegistro registro = tecnicoEnProceso.get(chatId);
        if (registro != null) {
            List<PhotoSize> fotos = update.getMessage().getPhoto();
            if (fotos != null && !fotos.isEmpty()) {
                PhotoSize foto = fotos.get(fotos.size() - 1);
                String fileId = foto.getFileId();
                descargarYGuardarFoto(fileId, chatId);
            } else {
                enviarMensaje(chatId, "No se detectaron fotos en tu mensaje.");
            }
        }
    }

    private void descargarYGuardarFoto(String fileId, Long chatId) {
        try {
            // Obtener el registro en proceso
            ProcesoRegistro registro = tecnicoEnProceso.get(chatId);
            GetFile getFileRequest = new GetFile();
            getFileRequest.setFileId(fileId);
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);

            // Verifica si obtuviste correctamente la información del archivo
            String filePath = file.getFilePath();
            System.out.println("Ruta del archivo en Telegram: " + filePath);

            // Descargar el archivo desde Telegram
            InputStream inputStream = new URL("https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath).openStream();

            if (inputStream == null) {
                System.out.println("Error: No se pudo obtener el InputStream para el archivo.");
                enviarMensaje(chatId, "Error al descargar la foto.");
                return;
            }

            // Información del nombre de la heladera y la fecha
            String nombreHeladera = registro.getIncidente().getHeladeraAsociada().nombre().replace(" ", "-");
            String fecha = LocalDate.now().toString().replace("-", "_");

            // Crear la ruta donde se guardará la imagen
            String rutaImagenesReportes = "uploads/incidentes/" + nombreHeladera + "_" + fecha + ".jpg";
            System.out.println("Ruta de la imagen a guardar: " + rutaImagenesReportes);

            // Verificar si el directorio de la ruta existe, si no, crearlo
            java.io.File dir = new java.io.File("uploads/incidentes/");
            if (!dir.exists()) {
                System.out.println("El directorio no existe. Creando directorio: " + dir.getAbsolutePath());
                boolean created = dir.mkdirs();  // Crea los directorios si no existen
                if (!created) {
                    System.out.println("Error al crear el directorio.");
                    enviarMensaje(chatId, "No se pudo crear el directorio para guardar la foto.");
                    return;
                }
            }

            // Crear el archivo y copiar la imagen descargada
            java.io.File imagenGuardada = new java.io.File(rutaImagenesReportes);
            System.out.println("Intentando guardar la imagen en: " + imagenGuardada.getAbsolutePath());

            Files.copy(inputStream, imagenGuardada.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Actualizar el registro de la visita
            VisitaHeladera visita = registro.getVisita();
            visita.setFoto(rutaImagenesReportes);
            registro.getIncidente().setFotoResolucion(rutaImagenesReportes);
            registro.setEstado("esperando_solucion");

            // Enviar mensaje de éxito
            enviarMensaje(chatId, "Foto recibida y guardada exitosamente. Solucionaste el incidente? Responde 'SI' o 'NO'");
        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
            // Imprimir el error para saber más sobre el fallo
            enviarMensaje(chatId, "Hubo un error al guardar la foto: " + e.getMessage());
        }
    }


}