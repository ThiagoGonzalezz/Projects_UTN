package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.DistribucionDeViandaInputDTO;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.MotivoDeDistribucion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.humanos.IHumanosRepositorio;
import vianditasONG.modelos.repositorios.humanos.imp.HumanosRepositorio;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Builder
public class ConverterDistribucionViandaDTO {
    @Builder.Default
    private IHumanosRepositorio humanosRepositorio= ServiceLocator.getService(HumanosRepositorio.class);
    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio= ServiceLocator.getService(HeladerasRepositorio.class);

    public DistribucionDeVianda converToDistribucionDeVianda(DistribucionDeViandaInputDTO distribucionDeViandaInputDTO){

        Optional<Heladera> heladeraDestinoOptional = heladerasRepositorio.buscar(Long.valueOf(distribucionDeViandaInputDTO.getIdHeladeraDestino()));
        Optional<Heladera> heladeraOrigenOptional = heladerasRepositorio.buscar(Long.valueOf(distribucionDeViandaInputDTO.getIdHeladeraOrigen()));
        Optional<Humano> humanoOptional = humanosRepositorio.buscar(distribucionDeViandaInputDTO.getIdHumano());

        Heladera heladeraOrigen = heladeraOrigenOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una heladera con el ID: " + distribucionDeViandaInputDTO.getIdHeladeraOrigen())
        );

        Heladera heladeraDestino = heladeraDestinoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró una heladera con el ID: " + distribucionDeViandaInputDTO.getIdHeladeraDestino())
        );
        Humano humano = humanoOptional.orElseThrow(() ->
                new NoSuchElementException("No se encontró un humano con el ID: " + distribucionDeViandaInputDTO.getIdHumano())
        );

        return DistribucionDeVianda.builder()
                .cantidadDeViandas(distribucionDeViandaInputDTO.getCantViandas())
                .colaborador(humano)
                .fecha(LocalDate.now())
                .heladeraDestino(heladeraDestino)
                .heladeraOrigen(heladeraOrigen)
                .motivoDeDistribucion(MotivoDeDistribucion.valueOf(distribucionDeViandaInputDTO.getMotivoDistribucion()))
                .build();
    }
}
