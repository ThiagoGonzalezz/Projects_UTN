package vianditasONG.modelos.servicios.recomendadorDeColaboradores;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.IRecomendadorDeColaboradoresServicio;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.RecomendadorDeColaboradoresServicio;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.HumanoRecomendado;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.MedioDeConctactoRecomendado;

import java.io.IOException;
import java.util.List;

@Builder
public class RecomendadorDeColaboradoresAdapterAPI implements RecomendadorDeColaboradoresAdapter {

    @Builder.Default
    private IRecomendadorDeColaboradoresServicio recomendadorDeColaboradoresServicio = RecomendadorDeColaboradoresServicio.getInstancia();
    public void recomendarColaboradores(List<Humano> humanos) throws IOException{

        List<HumanoRecomendado> humanosRecomendados = humanos.stream().map(this::convertToColaboradorRecomendadoDTO).toList();

        recomendadorDeColaboradoresServicio.enviarColaboradores(humanosRecomendados);

    }

    private HumanoRecomendado convertToColaboradorRecomendadoDTO(Humano humano){

        List<MedioDeConctactoRecomendado> mediosDeConctactoRecomendados = humano.getContactos().stream().map(this::convertToMedioDeContactoRecomendadoDTO).toList();


        return HumanoRecomendado.builder()
                .id(humano.getId())
                .nombre(humano.getNombre())
                .apellido(humano.getApellido())
                .puntos(humano.getPuntos())
                .tipoDocumento(String.valueOf(humano.getTipoDeDocumento()))
                .nroDocumento(humano.getNumeroDocumento())
                .donaciones(humano.getPuntos().intValue())
                .mediosDeContacto(mediosDeConctactoRecomendados)
                .build();

    }

    private MedioDeConctactoRecomendado convertToMedioDeContactoRecomendadoDTO(Contacto contacto){

        return MedioDeConctactoRecomendado.builder()
                .tipoMedioDeContacto(String.valueOf(contacto.getTipoDeContacto()))
                .detalle(contacto.getContacto())
                .build();

    }

}
