package vianditasONG.services.imp.colaboraciones;

import vianditasONG.dtos.inputs.colaboraciones.DistribucionDeViandaInputDTO;
import vianditasONG.dtos.outputs.DistribucionDeViandaOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.ICalculadorViandasDistribuidas;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.colaboracion.IDistribucionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.services.IDistribucionesDeViandasService;
import vianditasONG.utils.permisos.IVerificadorDePermisos;

import java.time.LocalDate;

public class DistribucionesDeViandasService implements IDistribucionesDeViandasService {
    ICalculadorViandasDistribuidas calculadorViandasDistribuidas;
    IVerificadorDePermisos verificadorDePermisos;
    IHumanosRepositorio humanosRepositorio;
    IHeladerasRepositorio heladerasRepositorio;
    IDistribucionesDeViandasRepositorio distribucionesDeViandasRepositorio;
    //falta el motivo de distribución
    @Override
    public DistribucionDeViandaOutputDTO crear(DistribucionDeViandaInputDTO distribucionDeViandaInputDTO, Usuario usuario) {
        Humano humano = this.humanosRepositorio.buscar(Long.valueOf(distribucionDeViandaInputDTO.getIdHumano())).orElse(null);
        Heladera heladeraDestino = this.heladerasRepositorio.buscar(Long.valueOf(distribucionDeViandaInputDTO.getIdHeladeraDestino())).orElse(null);
        Heladera heladeraOrigen = this.heladerasRepositorio.buscar(Long.valueOf(distribucionDeViandaInputDTO.getIdHeladeraOrigen())).orElse(null);
        /*MotivoDistribucion motivoDistribucion = this.motivoDistribucionRepositorio.buscar(distribucionViandaInputDTO.getIdMotivoDistribucion()).orElse(null);*/
        DistribucionDeVianda distribucionDeVianda = DistribucionDeVianda.builder().
                cantidadDeViandas(distribucionDeViandaInputDTO.getCantViandas()).
                fecha(LocalDate.now()).
                heladeraDestino(heladeraDestino).
                heladeraOrigen(heladeraOrigen).
                colaborador(humano).
                /*motivoDeDistribucion(motivoDistribucion).*/
                build();
        double puntosASumar = this.calculadorViandasDistribuidas.calcularPuntos(distribucionDeVianda);
        humano.sumarPuntos(puntosASumar);
        this.distribucionesDeViandasRepositorio.guardar(distribucionDeVianda);
        return null;
    }
}
