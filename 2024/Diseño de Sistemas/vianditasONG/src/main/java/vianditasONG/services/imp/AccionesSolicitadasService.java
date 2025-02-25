package vianditasONG.services.imp;

import vianditasONG.dtos.inputs.AccionSolicitadaInputDTO;
import vianditasONG.dtos.outputs.AccionSolicitadaOutputDTO;
import vianditasONG.config.Config;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.PublicadorSolicitudApertura;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.IAccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasColaboradorRepositorio;
import vianditasONG.services.IAccionesSolicitadasService;
import vianditasONG.services.exceptions.HeladeraNoEncontradaException;
import vianditasONG.services.exceptions.TarjetaColaboradorNoEcontradaException;
import vianditasONG.utils.mqtt.MQTTHandler;
//import vianditasONG.utils.permisos.VerificadorDePermisos;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccionesSolicitadasService implements IAccionesSolicitadasService {

    private IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio;
    private ITarjetasColaboradorRepositorio tarjetasColaboradorRepositorio;
    private IHeladerasRepositorio heladerasRepositorio;
    //private VerificadorDePermisos verificadorDePermisos;

    /*
    public AccionesSolicitadasService(IAccionesSolicitadasRepositorio accionesSolicitadasRepositorio, VerificadorDePermisos verificadorDePermisos){
        this.accionesSolicitadasRepositorio = accionesSolicitadasRepositorio;
        this.verificadorDePermisos = verificadorDePermisos;
    }
*/
    @Override
    public AccionSolicitadaOutputDTO crear(
            AccionSolicitadaInputDTO accionSolicitadaInputDTO,
            Usuario usuario
    ) {
        //Verificamos si el usuario que está ejecutando el Caso de Uso tiene los permisos necesarios
        //verificadorDePermisos.verificarSiUsuarioPuede("CREAR_ACCION_SOLICITADA", usuario);
        //TODO: @Luca

        Optional<TarjetaColaborador> posibleTarjeta = this.tarjetasColaboradorRepositorio.buscarPorCodigo(accionSolicitadaInputDTO.getCodigoTarjetaColaborador());
        if(posibleTarjeta.isEmpty())
            throw new TarjetaColaboradorNoEcontradaException();

        List<Heladera> posiblesHeladeras = new ArrayList<>();

        for (Integer idHeladera : accionSolicitadaInputDTO.getIdHeladerasSolicitadas()) {
            Optional<Heladera> posibleHeladera = heladerasRepositorio.buscar(Long.valueOf(idHeladera));

            if(posibleHeladera.isEmpty())
                throw new HeladeraNoEncontradaException();

            posiblesHeladeras.add(posibleHeladera.get());

        }

        //Creamos y configuramos la nueva accion solicitada
        AccionSolicitada nuevaAccionSolicitada = AccionSolicitada.builder()
                .tipoDeAccion(accionSolicitadaInputDTO.getTipoDeAccion())
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now().plusHours(Long.parseLong(Config.getInstancia().obtenerDelConfig("horasParaAbrirHeladera"))))
                .tarjetaColaborador(posibleTarjeta.get())
                .heladerasSolicitadas(posiblesHeladeras)
                .build();

        //Guardamos la nueva accion solicitada
        this.accionesSolicitadasRepositorio.guardar(nuevaAccionSolicitada);

        PublicadorSolicitudApertura publicadorSolicitudApertura = PublicadorSolicitudApertura.builder().build();
        

        publicadorSolicitudApertura.publicarAperturaSolicitada(nuevaAccionSolicitada);



        //Generamos y configuramos el DTO de salida
        AccionSolicitadaOutputDTO output = AccionSolicitadaOutputDTO.builder()
                .tipoDeAccion(nuevaAccionSolicitada.getTipoDeAccion())
                .fechaYHoraDeSolicitud(nuevaAccionSolicitada.getFechaYHoraDeSolicitud())
                .fechaDeCaducidadDeSolicitud(nuevaAccionSolicitada.getFechaDeCaducidadDeSolicitud())
                .tarjetaColaborador(nuevaAccionSolicitada.getTarjetaColaborador())
                .heladerasSolicitadas(nuevaAccionSolicitada.getHeladerasSolicitadas())
                .id(nuevaAccionSolicitada.getId())
                .build();

        return output;
    }

}
