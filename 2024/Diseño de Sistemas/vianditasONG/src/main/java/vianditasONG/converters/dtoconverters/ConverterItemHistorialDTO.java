package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.outputs.ItemHistorialOutputDTO;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Canje;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;

@Builder
public class ConverterItemHistorialDTO {

    @Builder.Default
    private CalculadorViandasDonadas calculadorViandasDonadas= ServiceLocator.getService(CalculadorViandasDonadas.class);
    @Builder.Default
    private CalculadorViandasDistribuidas calculadorViandasDistribuidas = ServiceLocator.getService(CalculadorViandasDistribuidas.class);
    @Builder.Default
    private CalculadorPesosDonados calculadorPesosDonados = ServiceLocator.getService(CalculadorPesosDonados.class);
    @Builder.Default
    private CalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas= ServiceLocator.getService(CalculadorTarjetasDistribuidas.class);


    public ItemHistorialOutputDTO convertDonacionDeViandaToDTO(DonacionDeVianda donacionDeVianda){
        return ItemHistorialOutputDTO.builder()
                .tipo("Donacion")
                .imagen("img/iconosDonaciones/iconoDonarVianda.png")
                .puntos((int) calculadorViandasDonadas.calcularPuntos(donacionDeVianda))
                .fechaYHora(String.valueOf(donacionDeVianda.getFecha()))
                .descripcion("Donación de vianda")
                .build();
    }

    public ItemHistorialOutputDTO convertDistribucionDeViandaToDTO(DistribucionDeVianda distribucionDeVianda) {
        return ItemHistorialOutputDTO.builder()
                .tipo("Donacion")
                .imagen("img/iconosDonaciones/iconoRedistribuirVianda.png")
                .puntos((int) calculadorViandasDistribuidas.calcularPuntos(distribucionDeVianda))
                .fechaYHora(String.valueOf(distribucionDeVianda.getFecha()))
                .descripcion("Distribución de viandas")
                .build();
    }

    public ItemHistorialOutputDTO convertRegistroPersonasVulnerablesToDTO(RegistroPersonasVulnerables registroPersonasVulnerables) {
        return ItemHistorialOutputDTO.builder()
                .tipo("Donacion")
                .imagen("img/iconosDonaciones/iconoRegistroPersonaVulnerable.png")
                .puntos((int) calculadorTarjetasDistribuidas.calcularPuntos(registroPersonasVulnerables))
                .fechaYHora(String.valueOf(registroPersonasVulnerables.getFecha()))
                .descripcion("Registro de persona vulnerable")
                .build();
    }

    public ItemHistorialOutputDTO convertDonacionDeDineroToDTO(DonacionDeDinero donacionDeDinero) {
        return ItemHistorialOutputDTO.builder()
                .tipo("Donacion")
                .imagen("img/iconosDonaciones/iconoDonarDinero.png")
                .puntos((int) calculadorPesosDonados.calcularPuntos(donacionDeDinero.pesosDonados()))
                .fechaYHora(String.valueOf(donacionDeDinero.getFecha()))
                .descripcion("Donacion de dinero")
                .build();
    }

    public ItemHistorialOutputDTO convertCanjeToDTO(Canje canje) {

        return ItemHistorialOutputDTO.builder()
                .tipo("Canje")
                .imagen(canje.getOferta().getImagen())
                .puntos(canje.getPuntos().intValue())
                .fechaYHora(String.valueOf(canje.getFechaYHora().toLocalDate()))
                .descripcion(canje.getOferta().getNombre())
                .build();

    }

    public ItemHistorialOutputDTO convertOfertaToDTO(Oferta oferta) {

        String descripcion = oferta.getNombre();
        if(oferta.getFueCanjeada())
            descripcion = descripcion.concat(" (canjeado)");

        return ItemHistorialOutputDTO.builder()
                .tipo("Oferta Publicada")
                .imagen(oferta.getImagen())
                .puntos((int) oferta.getPuntosNecesarios().intValue())
                .fechaYHora(String.valueOf(oferta.getFechaYHora().toLocalDate()))
                .descripcion(descripcion)
                .build();


    }

    public ItemHistorialOutputDTO convertHacerseCargoToDTO(HacerseCargoDeHeladera hacerseCargoDeHeladera) {

        Double puntosAcumulados = hacerseCargoDeHeladera.getPuntosAcumulados();
        Integer puntosRedondeados = Math.toIntExact(Math.round(puntosAcumulados));

        return ItemHistorialOutputDTO.builder()
                .tipo("Donacion")
                .imagen("img/iconosDonaciones/iconoColocarHeladera.png")
                .puntos(puntosRedondeados)
                .fechaYHora(String.valueOf(hacerseCargoDeHeladera.getFecha()))
                .descripcion("Hacerse cargo de heladera: " + hacerseCargoDeHeladera.getHeladera().getNombre())
                .build();
    }
}
