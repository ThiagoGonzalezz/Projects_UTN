package vianditasONG.converters.dtoconverters;


import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.dtos.inputs.colaboraciones.OfertaInputDTO;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.repositorios.personas_juridicas.IPersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.personas_juridicas.imp.PersonasJuridicasRepositorio;
import vianditasONG.modelos.repositorios.rubros.IRubrosOfertasRepositorio;
import vianditasONG.modelos.repositorios.rubros.imp.RubrosOfertasRepositorio;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
public class ConverterOfertasDto {

    @Builder.Default
    private IPersonasJuridicasRepositorio personasJuridicasRepositorio = ServiceLocator.getService(PersonasJuridicasRepositorio.class);
    @Builder.Default
    private IRubrosOfertasRepositorio rubrosOfertasRepositorio = ServiceLocator.getService(RubrosOfertasRepositorio.class);

    public OfertaOutputDto convertToDTO(Oferta oferta){
        return OfertaOutputDto.builder()
                .id(oferta.getId())
                .ofertante(oferta.getOfertante().getRazonSocial())
                .imagen(oferta.getImagen())
                .puntosNecesarios(oferta.getPuntosNecesarios().intValue())
                .nombre(oferta.getNombre())
                .build();
    }

    public Oferta convertToOferta(OfertaInputDTO ofertaInputDTO){

        Optional <RubroOferta> rubroOferta = rubrosOfertasRepositorio.buscarPorId(ofertaInputDTO.getRubroId());
        Optional <PersonaJuridica> personaJuridica = personasJuridicasRepositorio.buscarPorId(ofertaInputDTO.getOfertanteId());

        if (rubroOferta.isEmpty())
            throw new RuntimeException("No tomó el rubro de la oferta");


        if (personaJuridica.isEmpty())
            throw new RuntimeException("No tomó la sesión");



        return Oferta.builder()
                .ofertante(personaJuridica.get())
                .imagen(ofertaInputDTO.getImagen())
                .puntosNecesarios(ofertaInputDTO.getPuntosNecesarios())
                .nombre(ofertaInputDTO.getNombre())
                .rubro(rubroOferta.get())
                .fechaYHora(LocalDateTime.now())
                .build();
    }

}
