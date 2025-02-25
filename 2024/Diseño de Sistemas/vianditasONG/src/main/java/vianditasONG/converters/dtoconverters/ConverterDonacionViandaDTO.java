package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.DonacionDeViandaInputDTO;
import vianditasONG.dtos.inputs.colaboraciones.ViandaDTO;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Builder
public class ConverterDonacionViandaDTO {

    @Builder.Default
    private IHumanosRepositorio humanosRepositorio= ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio= ServiceLocator.getService(HeladerasRepositorio.class);

    public DonacionDeVianda convertToDonacionDeVianda(DonacionDeViandaInputDTO donacionDeViandaInputDTO){

        Optional<Heladera> heladeraOptional = heladerasRepositorio.buscar(Long.valueOf(donacionDeViandaInputDTO.getIdHeladeraSeleccionada()));
        Optional<Humano> humanoOptional = humanosRepositorio.buscar(donacionDeViandaInputDTO.getIdHumano());

        Heladera heladera = heladeraOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una heladera con el ID: " + donacionDeViandaInputDTO.getIdHeladeraSeleccionada())
        );
        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró un humano con el ID: " + donacionDeViandaInputDTO.getIdHumano())
        );

        List<Vianda> viandas = new ArrayList<>();

        for (ViandaDTO viandaDTO : donacionDeViandaInputDTO.getViandas()) {
            Vianda vianda = Vianda.builder()
                    .comida(viandaDTO.getComida())
                    .calorias(viandaDTO.getCalorias())
                    .entregada(false)
                    .heladeraOrigen(heladera)
                    .fechaDeDonacion(LocalDateTime.now())
                    .peso(viandaDTO.getPesoEnGramos())
                    .fechaDeCaducidad(LocalDate.parse(viandaDTO.getFechaDeCaducidad()))
                    .build();
            viandas.add(vianda);
        }

        return DonacionDeVianda.builder()
                .heladera(heladera)
                .colaborador(humano)
                .fecha(LocalDate.now())
                .viandas(viandas)
                .build();
    }
}
