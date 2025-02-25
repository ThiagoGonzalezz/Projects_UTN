package vianditasONG.serviciosExternos.openCage;

import lombok.Builder;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenCageServicio {
    @GET("/geocode/v1/json")
    Call<RespuestaOpenCage> obtenerPuntoGeografico(
            @Query("q") String direccion,
            @Query("key") String apiKey,
            @Query("countrycode") String countrycode
    );

    @GET("/geocode/v1/json")
    Call<RespuestaOpenCage> obtenerDireccionDesdePunto(
            @Query("q") String coordenadas,  // "latitud,longitud"
            @Query("key") String apiKey,
            @Query("countrycode") String countrycode
    );
}
