package vianditasONG.services.imp.colaboraciones;

import vianditasONG.dtos.inputs.colaboraciones.DonacionDeViandaInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.DonacionDeViandaOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.ICalculadorViandasDonadas;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeViandasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.services.IDonacionesDeViandasService;
import vianditasONG.utils.permisos.IVerificadorDePermisos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DonacionesDeViandasService implements IDonacionesDeViandasService {
    IDonacionesDeViandasRepositorio donacionesDeViandasRepositorio;
    ICalculadorViandasDonadas calculadorViandasDonadas;
    IVerificadorDePermisos verificadorDePermisos;
    IHumanosRepositorio humanosRepositorio;
    IHeladerasRepositorio heladerasRepositorio;

    @Override
    public DonacionDeViandaOutputDTO crear(DonacionDeViandaInputDTO donacionDeViandaInputDTO, Usuario usuario) {
        return null;
    }

    /*public DonacionDeViandaOutputDTO crear(DonacionDeViandaInputDTO donacionDeViandaInputDTO, Usuario usuario) {
        verificadorDePermisos.verificarSiUsuarioPuede("DONAR_VIANDA", usuario);
        Humano humano = this.humanosRepositorio.buscar(Long.valueOf(donacionDeViandaInputDTO.getIdHumano())).orElse(null);
        Heladera heladera = this.heladerasRepositorio.buscar(Long.valueOf(donacionDeViandaInputDTO.getIdHeladeraSeleccionada())).orElse(null);
        DonacionDeVianda donacionDeVianda = DonacionDeVianda.builder().
                fecha(LocalDate.from(LocalDateTime.now())).
                colaborador(humano).
                build();
        donacionDeViandaInputDTO.getViandaDto().stream().forEach(v->{
            Vianda vianda = Vianda.builder().
                    calorias(v.getCalorias()).
                    comida(v.getComida()).
                    fechaDeDonacion(LocalDateTime.now()).
                    fechaDeCaducidad(LocalDateTime.parse(v.getFechaDeCaducidad())).
                    colaborador(humano).
                    heladeraOrigen(heladera).
                    entregada(Boolean.FALSE).
                    build();
            donacionDeVianda.agregarViandas(vianda);
        });

        Double puntosASumar = calculadorViandasDonadas.calcularPuntos(donacionDeVianda);
        humano.sumarPuntos(puntosASumar);
        this.donacionesDeViandasRepositorio.guardar(donacionDeVianda);
        return null;*///todo @Agus
    }

