package vianditasONG.controllers.tecnicos;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import lombok.Builder;
import lombok.SneakyThrows;
import vianditasONG.config.ServiceLocator;
import vianditasONG.converters.dtoconverters.ConverterTecnicoDTO;
import vianditasONG.dtos.inputs.tecnicos.EditarTecnicoInputDTO;
import vianditasONG.dtos.inputs.tecnicos.SumarTecnicoInputDTO;
import vianditasONG.dtos.outputs.tecnicos.TecnicoOutputDTO;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.contactos.imp.ContactosRepositorio;
import vianditasONG.modelos.repositorios.tecnicos.imp.TecnicosRepositorio;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;
import vianditasONG.utils.ICrudViewsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public class GestionDeTecnicosController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Builder.Default
    private ServicioOpenCage servicioOpenCage = ServiceLocator.getService(ServicioOpenCage.class);

    @Builder.Default
    private ConverterTecnicoDTO converterTecnicoDTO =  ServiceLocator.getService(ConverterTecnicoDTO.class);

    @Builder.Default
    private TecnicosRepositorio tecnicosRepositorio = ServiceLocator.getService(TecnicosRepositorio.class);

    @Builder.Default
    private ContactosRepositorio contactosRepositorio = ServiceLocator.getService(ContactosRepositorio.class);


    @Builder.Default
    private final Gson gson = new Gson();

    @SneakyThrows
    @Override
    public void index(Context context) {
        context.render("admin/gestion_de_tecnicos.hbs");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {

        SumarTecnicoInputDTO dto = SumarTecnicoInputDTO.builder()
                .areaDeCoberturaKM(Integer.valueOf(Objects.requireNonNull(context.formParam("areaCoberturaKM"))))
                .provincia(Objects.requireNonNull(context.formParam("provincia")))
                .localidad(Objects.requireNonNull(context.formParam("localidad")))
                .calle(context.formParam("calle"))
                .altura(Integer.valueOf(Objects.requireNonNull(context.formParam("altura"))))
                .nombre(context.formParam("nombreTecnico"))
                .apellido(context.formParam("apellidoTecnico"))
                .tipoDeDocumento(context.formParam("tipoDocumento"))
                .numeroDeDocumento(context.formParam("numeroDocumento"))
                .contacto(context.formParam("contacto"))
                .tipoContacto(context.formParam("tipoContacto"))
                .cuil(context.formParam("cuilTecnico"))
                .build();

        Tecnico tecnicoNuevo = converterTecnicoDTO.convertToTecnicoNuevo(dto);

        withTransaction(() -> {
            contactosRepositorio.guardar(tecnicoNuevo.getContactos().get(0));
            tecnicosRepositorio.guardar(tecnicoNuevo);
        });

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Tecnico añadido con éxito");

        String respuestaJSON = gson.toJson(responseMessage);

        context.status(200).json(respuestaJSON);


    }

    @Override
    public void edit(Context context) throws IOException {
        String cuil = context.pathParam("cuil");
        Tecnico tecnico = tecnicosRepositorio.buscarPorCuil(cuil)
                .orElseThrow(() -> new RuntimeException("Tecnico no encontrado"));

        PuntoGeografico puntoGeo = new PuntoGeografico(tecnico.getAreaDeCobertura().getPuntoGeografico().getLatitud(), tecnico.getAreaDeCobertura().getPuntoGeografico().getLongitud());
        RespuestaOpenCage direccionObtenida = servicioOpenCage.obtenerDireccionDesdePunto(puntoGeo);
        List<Resultado> resultadosDireccion = direccionObtenida.getResults();

        String direccionString = resultadosDireccion.get(0).getFormatted();



        EditarTecnicoInputDTO dto = EditarTecnicoInputDTO.builder()
                .cuil(tecnico.getCuil())
                .activo(tecnico.getActivo())
                .nombre(tecnico.getNombre())
                .apellido(tecnico.getApellido())
                .areaDeCoberturaKM(tecnico.getAreaDeCobertura().getRadioEnKM())
                .tipoDeDocumento(tecnico.getTipoDeDocumento().name())
                .numeroDeDocumento(tecnico.getNumeroDeDocumento())
                .contacto(tecnico.getContactos().get(0).getContacto())
                .tipoDeContacto(tecnico.getContactos().get(0).getTipoDeContacto().name())
                .build();

        String respuestaJSON = gson.toJson(dto);

        context.json(respuestaJSON);

    }

    @Override
    public void update(Context context) throws IOException {
        String cuil = context.pathParam("cuil");
        Tecnico tecnico = tecnicosRepositorio.buscarPorCuil(cuil)
                .orElseThrow(() -> new RuntimeException("Tecnico no encontrado"));


        EditarTecnicoInputDTO dto = EditarTecnicoInputDTO.builder()
                .activo(Boolean.valueOf(context.formParam("activoTecnico")))
                .areaDeCoberturaKM(Integer.valueOf(Objects.requireNonNull(context.formParam("areaCoberturaKM"))))
                .nombre(context.formParam("nombre"))
                .apellido(context.formParam("apellido"))
                .tipoDeDocumento(context.formParam("tipoDocumento"))
                .numeroDeDocumento(context.formParam("numeroDocumento"))
                .tipoDeContacto(context.formParam("tipoContacto"))
                .contacto(context.formParam("contacto"))
                .provincia(context.formParam("provincia"))
                .localidad(context.formParam("localidad"))
                .calle(context.formParam("calle"))
                .altura(Integer.valueOf(Objects.requireNonNull(context.formParam("altura"))))
                .build();

        Tecnico tecnicoActualizado = converterTecnicoDTO.convertToTecnicoActualizado(dto, tecnico);

        if(!Objects.equals(tecnicoActualizado.getContactos().get(0).getContacto(), tecnico.getContactos().get(0).getContacto())){
            withTransaction(() -> {
                contactosRepositorio.guardar(tecnicoActualizado.getContactos().get(0));
                tecnicosRepositorio.guardar(tecnicoActualizado);
            });
        }else{
            withTransaction(() -> {
                tecnicosRepositorio.guardar(tecnicoActualizado);
            });
        }

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Tecnico actualizado con éxito");

        String respuestaJSON = gson.toJson(responseMessage);


        context.status(200).json(respuestaJSON);

    }

    @Override
    public void delete(Context context) {
        String cuil = context.pathParam("cuil");
        Tecnico tecnico = tecnicosRepositorio.buscarPorCuil(cuil)
                .orElseThrow(() -> new RuntimeException("Tecnico no encontrado"));

        withTransaction(() -> tecnicosRepositorio.eliminarLogico(tecnico));

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Tecnico eliminada con éxito");

        String respuestaJSON = gson.toJson(responseMessage);

        context.status(200).json(respuestaJSON);
    }


}


