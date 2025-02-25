package vianditasONG.services.imp.colaboraciones;

import vianditasONG.dtos.inputs.colaboraciones.DonacionDeDineroInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.DonacionDeDineroOutputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.ICalculadorPesosDonados;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.repositorios.colaboracion.IDonacionesDeDineroRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.services.IDonacionesDeDineroService;
import vianditasONG.utils.permisos.IVerificadorDePermisos;

import java.time.LocalDate;


public class DonacionesDeDineroService implements IDonacionesDeDineroService {
    IDonacionesDeDineroRepositorio donacionesDeDineroRepositorio;
    ICalculadorPesosDonados calculadorPesosDonados;
    IVerificadorDePermisos verificadorDePermisos;
    IPersonasJuridicasRepositorio personasJuridicasRepositorio;
    IHumanosRepositorio humanosRepositorio;
    @Override
    public DonacionDeDineroOutputDTO crear(DonacionDeDineroInputDTO donacionDeDineroInputDTO, Usuario usuario) {
        verificadorDePermisos.verificarSiUsuarioPuede("DONAR_DINERO", usuario);

        DonacionDeDinero donacionDeDinero = DonacionDeDinero.builder().
                monto(donacionDeDineroInputDTO.getMonto()).
                frecuenciaDonacion(donacionDeDineroInputDTO.getFrecuenciaDonacion()).
                fecha(LocalDate.now()).
                build();

        Double puntosASumar = calculadorPesosDonados.calcularPuntos(donacionDeDinero.getMonto());

       /* if(donacionDeDineroInputDTO.getIdHumano()==null){
            PersonaJuridica personaJuridica = this.personasJuridicasRepositorio
                    .buscarPorRazonSocial(donacionDeDineroInputDTO.getRazonSocialPersonaJuridica()).orElse(null);

            donacionDeDinero.setPuntuable(personaJuridica);
            personaJuridica.agregarDonacionesDeDinero(donacionDeDinero);
            personaJuridica.sumarPuntos(puntosASumar);
        }
        else {
            Humano humano = this.humanosRepositorio.buscar(donacionDeDineroInputDTO.getIdHumano()).orElse(null);
            donacionDeDinero.setPuntuable(humano);
            humano.agregarDonacionesDeDinero(donacionDeDinero);
            humano.sumarPuntos(puntosASumar);
        }
        this.donacionesDeDineroRepositorio.guardar(donacionDeDinero);*/ //TODO:Descomentar una vez arreglado

        return null;//todo
    }



}
