package vianditasONG.serviciosExternos.recomendadorDeColaboradores;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vianditasONG.config.Config;
import vianditasONG.serviciosExternos.recomendadorDeColaboradores.entities.HumanoRecomendado;


import java.io.IOException;
import java.util.List;

public class RecomendadorDeColaboradoresServicio implements IRecomendadorDeColaboradoresServicio{

    private static RecomendadorDeColaboradoresServicio instancia = null;
    private static final String urlAPI = Config.getInstancia().obtenerDelConfig("urlRecomendadorDeColaboradores");
    private Retrofit retrofit;

    private RecomendadorDeColaboradoresServicio() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RecomendadorDeColaboradoresServicio getInstancia() {
        if (instancia == null) {
            instancia = new RecomendadorDeColaboradoresServicio();
        }
        return instancia;
    }

    public Call<Void> enviarColaboradores(List<HumanoRecomendado> humanosRecomendados) throws IOException {
        IRecomendadorDeColaboradoresServicio recomendadorDeColaboradoresServicio = this.retrofit.create(IRecomendadorDeColaboradoresServicio.class);
        Call<Void> requestRecomendacion = recomendadorDeColaboradoresServicio.enviarColaboradores(humanosRecomendados);
        requestRecomendacion.execute();
        return requestRecomendacion;
    }

}
