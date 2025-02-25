package vianditasONG.converters.dtoconverters;


import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.DonacionDeDineroInputDTO;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class ConverterDonacionDineroDTO {
    @Builder.Default
    HumanosRepositorio repoHumanos = ServiceLocator.getService(HumanosRepositorio.class);

    @Builder.Default
    PersonasJuridicasRepositorio repoJuridicas = ServiceLocator.getService(PersonasJuridicasRepositorio.class);

    @Builder.Default
    CalculadorPesosDonados calculadorPesosDonados = ServiceLocator.getService(CalculadorPesosDonados.class);

    public DonacionDeDinero convertToDonacion(DonacionDeDineroInputDTO dto, Boolean humano) {

        if (humano) {

            Humano donador = repoHumanos.buscar(dto.getIdUsuario()) .orElseThrow(() -> new RuntimeException("Humano con ID " + dto.getIdUsuario() + " no encontrado"));
            donador.setPuntos(donador.getPuntos()+calculadorPesosDonados.calcularPuntos(dto.getMonto()));

            return DonacionDeDinero.builder()
                    .fecha(LocalDate.now())
                    .monto(dto.getMonto())
                    .frecuenciaDonacion(dto.getFrecuenciaDonacion())
                    .humano(donador)
                    .build();
        }else{

            PersonaJuridica donador = repoJuridicas.buscarPorId(dto.getIdUsuario()) .orElseThrow(() -> new RuntimeException("Persona Jurídica con ID " + dto.getIdUsuario() + " no encontrada"));
            return DonacionDeDinero.builder()
                    .fecha(LocalDate.now())
                    .monto(dto.getMonto())
                    .frecuenciaDonacion(dto.getFrecuenciaDonacion())
                    .personaJuridica(donador)
                    .build();

        }


    }
}