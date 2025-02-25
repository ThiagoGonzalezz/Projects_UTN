package vianditasONG.serviciosExternos.recomendadorDePuntos;

import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.ListadoDePuntosEstrategicos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecomendadorDePuntosServicios {

    @GET("puntoestrategico")
    Call<ListadoDePuntosEstrategicos> puntosRecomendados(
            @Query("latitud") String latitud,
            @Query("longitud") String longitud,
            @Query("radio") int radio
    );

}
