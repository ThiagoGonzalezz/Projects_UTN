package vianditasONG.modelos.servicios.recomendadorDePuntos;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.datosGenerales.AreaDeBusqueda;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.servicios.conversorDirecAPunto.OpenCageAdapterAPI;
import vianditasONG.serviciosExternos.recomendadorDePuntos.ServicioRecomendadorDePuntos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.ListadoDePuntosEstrategicos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.PuntoMolde;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
@Builder
public class PuntosRecomendadosAdapterAPI implements PuntosRecomendadosAdapter {
    @Builder.Default
    private ConversorDirecAPuntoAdapter conversorDirecAPunto = ServiceLocator.getService(OpenCageAdapterAPI.class);

    public List<PuntoEstrategico> recomendarPuntos(AreaDeBusqueda areaDeBusqueda) throws IOException {
        ListadoDePuntosEstrategicos resultado = ServicioRecomendadorDePuntos.getInstancia().listadoDePuntosEstrategicos(areaDeBusqueda.getPuntoGeografico(), areaDeBusqueda.getRadioEnMetros());
        if(resultado==null){
            throw new RuntimeException();
        }
        List<PuntoMolde> puntosIdealesObtenidos = new ArrayList<>();
        puntosIdealesObtenidos.addAll(resultado.getPuntosEstrategicos());

        List<PuntoGeografico> puntosIdeales = new ArrayList<>();
        puntosIdealesObtenidos.forEach(p -> puntosIdeales.add(PuntoGeografico.builder()
                .latitud(Double.parseDouble(p.latitud))
                .longitud(Double.parseDouble(p.longitud))
                .build()));

        List<PuntoEstrategico> puntosEstrategicos = new ArrayList<>();

        for(PuntoGeografico puntoGeografico : puntosIdeales){
            Direccion direccion = this.conversorDirecAPunto.obtenerDireccionDesdePuntoGeografico(puntoGeografico);
            PuntoEstrategico puntoEstrategico = PuntoEstrategico.builder().puntoGeografico(puntoGeografico).direccion(direccion).build();
            puntosEstrategicos.add(puntoEstrategico);
        }

        return puntosEstrategicos;
    }
}