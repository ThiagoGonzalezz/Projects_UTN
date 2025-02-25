package vianditasONG.serviciosExternos.recomendadorDeColaboradores;

import retrofit2.Call;
import retrofit2.http.*;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.HumanoRecomendado;

import java.io.IOException;
import java.util.List;

public interface IRecomendadorDeColaboradoresServicio {


    @Headers({
            "Content-Type: application/json",
            "Accept: */*"
    })
    @POST("colaboradores")
    Call<Void> enviarColaboradores(@Body List<HumanoRecomendado> humanosRecomendados) throws IOException;

}
