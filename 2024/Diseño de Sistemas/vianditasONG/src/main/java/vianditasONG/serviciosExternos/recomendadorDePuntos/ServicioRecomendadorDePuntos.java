package vianditasONG.serviciosExternos.recomendadorDePuntos;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.ListadoDePuntosEstrategicos;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioRecomendadorDePuntos {

        private static ServicioRecomendadorDePuntos instancia = null;
        private static final String urlAPI = Config.getInstancia().obtenerDelConfig("urlRecomendadorDePuntosAPI");
        private Retrofit retrofit;

        private ServicioRecomendadorDePuntos() {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(urlAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static ServicioRecomendadorDePuntos getInstancia() {
            if (instancia == null) {
                instancia = new ServicioRecomendadorDePuntos();
            }
            return instancia;
        }

        public ListadoDePuntosEstrategicos listadoDePuntosEstrategicos(PuntoGeografico puntoGeografico, int radio) throws IOException {
            RecomendadorDePuntosServicios recomendadorDePuntosServicios = this.retrofit.create(RecomendadorDePuntosServicios.class);
            Call<ListadoDePuntosEstrategicos> requestPuntos = recomendadorDePuntosServicios.puntosRecomendados(String.valueOf(puntoGeografico.getLatitud()),
                                                                                                               String.valueOf(puntoGeografico.getLongitud()),
                                                                                                               radio);
            Response<ListadoDePuntosEstrategicos> responsePuntos = requestPuntos.execute();
            return responsePuntos.body();
        }


}
